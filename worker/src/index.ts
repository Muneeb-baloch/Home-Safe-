import { SignJWT, importPKCS8 } from "jose";

export interface Env {
  FIREBASE_SERVICE_ACCOUNT_KEY: string;
}

interface ServiceAccount {
  project_id: string;
  client_email: string;
  private_key: string;
}

const FIRESTORE_SCOPE = "https://www.googleapis.com/auth/datastore";
const FCM_SCOPE = "https://www.googleapis.com/auth/firebase.messaging";
const TOKEN_URL = "https://oauth2.googleapis.com/token";

let cachedToken: { accessToken: string; expiresAt: number; projectId: string } | null = null;

async function getAccessToken(env: Env): Promise<{ accessToken: string; projectId: string }> {
  const serviceAccount: ServiceAccount = JSON.parse(env.FIREBASE_SERVICE_ACCOUNT_KEY);

  if (cachedToken && cachedToken.expiresAt > Date.now() + 60_000) {
    return { accessToken: cachedToken.accessToken, projectId: cachedToken.projectId };
  }

  const privateKey = await importPKCS8(serviceAccount.private_key, "RS256");
  const now = Math.floor(Date.now() / 1000);

  const jwt = await new SignJWT({ scope: `${FIRESTORE_SCOPE} ${FCM_SCOPE}` })
    .setProtectedHeader({ alg: "RS256", typ: "JWT" })
    .setIssuer(serviceAccount.client_email)
    .setSubject(serviceAccount.client_email)
    .setAudience(TOKEN_URL)
    .setIssuedAt(now)
    .setExpirationTime(now + 3600)
    .sign(privateKey);

  const response = await fetch(TOKEN_URL, {
    method: "POST",
    headers: { "Content-Type": "application/x-www-form-urlencoded" },
    body: new URLSearchParams({
      grant_type: "urn:ietf:params:oauth:grant-type:jwt-bearer",
      assertion: jwt
    })
  });

  if (!response.ok) {
    throw new Error(`Token exchange failed: ${await response.text()}`);
  }

  const data = (await response.json()) as { access_token: string; expires_in: number };
  cachedToken = {
    accessToken: data.access_token,
    expiresAt: Date.now() + data.expires_in * 1000,
    projectId: serviceAccount.project_id
  };
  return { accessToken: data.access_token, projectId: serviceAccount.project_id };
}

function firestoreUrl(projectId: string, path: string): string {
  return `https://firestore.googleapis.com/v1/projects/${projectId}/databases/(default)/documents/${path}`;
}

function decodeValue(value: any): any {
  if (value == null) return null;
  if ("stringValue" in value) return value.stringValue;
  if ("booleanValue" in value) return value.booleanValue;
  if ("integerValue" in value) return Number(value.integerValue);
  if ("timestampValue" in value) return value.timestampValue;
  if ("arrayValue" in value) return (value.arrayValue.values ?? []).map(decodeValue);
  if ("mapValue" in value) return decodeFields(value.mapValue.fields ?? {});
  return null;
}

function decodeFields(fields: Record<string, any>): Record<string, any> {
  const result: Record<string, any> = {};
  for (const key of Object.keys(fields)) {
    result[key] = decodeValue(fields[key]);
  }
  return result;
}

function encodeValue(value: any): any {
  if (value === null || value === undefined) return { nullValue: null };
  if (value instanceof Date) return { timestampValue: value.toISOString() };
  if (typeof value === "string") return { stringValue: value };
  if (typeof value === "boolean") return { booleanValue: value };
  if (typeof value === "number") return { integerValue: String(Math.trunc(value)) };
  if (Array.isArray(value)) return { arrayValue: { values: value.map(encodeValue) } };
  return { nullValue: null };
}

function encodeFields(fields: Record<string, any>): Record<string, any> {
  const result: Record<string, any> = {};
  for (const key of Object.keys(fields)) {
    result[key] = encodeValue(fields[key]);
  }
  return result;
}

async function getDocument(projectId: string, accessToken: string, path: string): Promise<Record<string, any> | null> {
  const response = await fetch(firestoreUrl(projectId, path), {
    headers: { Authorization: `Bearer ${accessToken}` }
  });
  if (response.status === 404) return null;
  if (!response.ok) throw new Error(`Firestore GET ${path} failed: ${await response.text()}`);
  const doc = (await response.json()) as { fields?: Record<string, any> };
  return decodeFields(doc.fields ?? {});
}

async function patchDocument(
  projectId: string,
  accessToken: string,
  path: string,
  fields: Record<string, any>
): Promise<void> {
  const updateMask = Object.keys(fields)
    .map((key) => `updateMask.fieldPaths=${encodeURIComponent(key)}`)
    .join("&");
  const response = await fetch(`${firestoreUrl(projectId, path)}?${updateMask}`, {
    method: "PATCH",
    headers: { Authorization: `Bearer ${accessToken}`, "Content-Type": "application/json" },
    body: JSON.stringify({ fields: encodeFields(fields) })
  });
  if (!response.ok) throw new Error(`Firestore PATCH ${path} failed: ${await response.text()}`);
}

async function createDocument(
  projectId: string,
  accessToken: string,
  collectionPath: string,
  fields: Record<string, any>
): Promise<void> {
  const response = await fetch(firestoreUrl(projectId, collectionPath), {
    method: "POST",
    headers: { Authorization: `Bearer ${accessToken}`, "Content-Type": "application/json" },
    body: JSON.stringify({ fields: encodeFields(fields) })
  });
  if (!response.ok) throw new Error(`Firestore POST ${collectionPath} failed: ${await response.text()}`);
}

async function sendFcm(
  projectId: string,
  accessToken: string,
  fcmToken: string,
  data: Record<string, string>
): Promise<boolean> {
  const response = await fetch(`https://fcm.googleapis.com/v1/projects/${projectId}/messages:send`, {
    method: "POST",
    headers: { Authorization: `Bearer ${accessToken}`, "Content-Type": "application/json" },
    body: JSON.stringify({
      message: {
        token: fcmToken,
        data,
        android: { priority: "high" }
      }
    })
  });
  return response.ok;
}

async function handleNotifyArrival(request: Request, env: Env): Promise<Response> {
  const body = (await request.json()) as { journeyId?: string };
  const journeyId = body.journeyId;
  if (!journeyId) {
    return new Response("Missing journeyId", { status: 400 });
  }

  const { accessToken, projectId } = await getAccessToken(env);

  const journey = await getDocument(projectId, accessToken, `journeys/${journeyId}`);
  if (!journey || journey.status !== "ARRIVED" || journey.notified === true) {
    return new Response("OK", { status: 200 });
  }

  const outing = await getDocument(projectId, accessToken, `outings/${journey.outingId}`);
  if (!outing) {
    return new Response("OK", { status: 200 });
  }

  const arriver = await getDocument(projectId, accessToken, `users/${journey.userId}`);
  const arriverName: string = arriver?.name || "Someone";

  const recipientIds: string[] = (outing.members ?? []).filter((uid: string) => uid !== journey.userId);

  const arrivedAtIso: string | null = journey.arrivedAt ?? null;
  const arrivedAtDate = arrivedAtIso ? new Date(arrivedAtIso) : new Date();
  const arrivedAtLabel = arrivedAtDate.toLocaleTimeString("en-US", { hour: "numeric", minute: "2-digit" });

  for (const recipientId of recipientIds) {
    const recipient = await getDocument(projectId, accessToken, `users/${recipientId}`);
    const tokens: string[] = Array.from(new Set((recipient?.fcmTokens ?? []) as string[]));

    let delivered = false;
    for (const fcmToken of tokens) {
      const sent = await sendFcm(projectId, accessToken, fcmToken, {
        name: arriverName,
        status: "ARRIVED",
        arrivedAt: arrivedAtLabel
      });
      if (sent) delivered = true;
    }

    await createDocument(projectId, accessToken, "notifications", {
      outingId: journey.outingId,
      recipientId,
      type: "ARRIVAL",
      arriverName,
      arrivedAt: arrivedAtDate,
      createdAt: new Date(),
      delivered
    });
  }

  await patchDocument(projectId, accessToken, `journeys/${journeyId}`, { notified: true });

  return new Response("OK", { status: 200 });
}

export default {
  async fetch(request: Request, env: Env): Promise<Response> {
    const url = new URL(request.url);

    if (request.method === "POST" && url.pathname === "/notify-arrival") {
      try {
        return await handleNotifyArrival(request, env);
      } catch (error) {
        return new Response(`Error: ${(error as Error).message}`, { status: 500 });
      }
    }

    return new Response("Not found", { status: 404 });
  }
};
