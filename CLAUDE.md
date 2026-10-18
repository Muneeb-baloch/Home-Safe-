# HomeSafe — Project rules for Claude

## What we're building

HomeSafe is an Android app that tells friends and family automatically when you get home after an outing, so nobody has to remember to send the "I'm home" message.

Core loop: user starts an Outing → friends join (QR / 4-char code / invite link) → user goes out → user's phone enters the home geofence → journey is marked ARRIVED → Firebase pushes "Muneeb reached home safely" to the outing's companions (and trusted contacts).

Three product principles. Every decision must respect them:

1. **Automatic.** The user should not have to open the app to tell people they're home.
2. **Temporary.** Outing companions exist only for that outing. Trusted contacts are the only permanent relationship, and they require the other person to accept.
3. **Privacy-first.** Share arrival status and time only. Never share live location, routes, or location history with anyone.

## Tech stack

- Kotlin, Jetpack Compose, Material 3, single Activity, Navigation Compose
- MVVM: Composable screen → ViewModel (StateFlow UI state) → Repository → Firebase / Android APIs
- Follow the DI and package structure already in the project. If none exists, use Hilt.
- Firebase: Auth, Firestore, Cloud Messaging (FCM), Cloud Functions, Analytics, Crashlytics, Remote Config
- Location: GeofencingClient (Google Play Services) for home enter/exit. Never use continuous GPS tracking.
- Background work: WorkManager. Re-register geofences on BOOT_COMPLETED and app update.
- Maps: Google Maps Compose (Set home location only)
- QR: CameraX + ML Kit barcode scanning; generate QR for the outing code

Do not add a new library without asking me first and saying why.

## Navigation

- Bottom tabs: Home · Journeys · Companions · Profile (exactly these four)
- Alerts opens from the bell in the Home header. It is NOT a tab.
- First run: Splash → Onboarding (3) → Sign up / Log in → Setup (location → background location → notifications → set home) → Home
- Skip Setup if home is saved and permissions are granted.

## Design system (match exactly)

Reference screens are HTML files in `/design/screens/`. Treat them as the visual source of truth: same layout, text, spacing, colors and icons. Use the HTML for layout only; build real Compose UI (a real Google Map, real camera, etc.).

### Colors

| Token | Hex | Use |
|---|---|---|
| brand | `#176B5B` | primary buttons, active tab, links, icons |
| brandTint | `#E8F1EE` | icon tiles, pills, avatars |
| ink | `#16211D` | primary text |
| muted | `#5E6B66` | secondary text |
| subtle | `#8A938F` | captions, metadata |
| faint | `#9AA39F` | inactive tab, hints |
| border | `#D5DED9` | outlined buttons, inputs |
| divider | `#EEF2F0` | list dividers, nav top border |
| page | `#F4F6F5` | screen background |
| surface | `#FFFFFF` | cards, header, nav |
| map | `#EAF1EE` | map placeholder / illustration bg |
| red | `#C4453C` / tint `#FBECEB` | destructive actions, location-off |
| amber | `#A8670C` / tint `#FDF3E3` | "Out", offline, queued |

### Typography

Inter only, weights Regular (400) and Medium (500). Set `letterSpacing = 0.sp` on every text style (override the Material 3 defaults).

- Screen title 24sp Medium · Heading 26sp Medium (line height 32) · Hero title 26sp Medium
- Body 14sp (line height 21) · Button 15sp Medium · Caption 12sp · Small 11sp

### Shape & spacing

- Screen horizontal padding 20dp; vertical gap between sections 16dp (Home uses 14dp)
- Cards: white, radius 20dp, padding 16dp. List rows: 12dp vertical padding with a 1dp divider.
- Primary button: height 52dp, radius 16dp, full width. Outline button: white with a 1dp `#D5DED9` border.
- Hero card: brand background, radius 20dp, padding 20dp
- Icon tiles: 36–38dp, radius 11dp, brandTint background, 18dp icon
- Back/close button: 40dp white circle
- Bottom nav: white, 1dp top border `#EEF2F0`, 21dp icons, 11sp labels. Active: brand + Medium. Inactive: faint + Regular.

### Icons

Outlined, rounded caps and joins, stroke width about 2 (Lucide style). Use the custom Home icon from the design (rounded house with a short line at the bottom) everywhere a home icon appears. Store icons as vector drawables in `res/drawable/ic_*.xml`.

### Insets

Edge-to-edge. Apply status-bar insets once only, on the header. Don't use the Material3 TopAppBar for the Home header; use a custom Row (padding top 16, bottom 10, horizontal 20). The status bar area and the header are both white with dark icons.

## Data model (Firestore)

- `users/{uid}`: name, email, homeLocation {lat, lng, radius, address}, fcmTokens[], settings{}
- `users/{uid}/trustedContacts/{contactUid}`: status (pending|accepted|rejected), notifyOnArrival, createdAt
- `outings/{outingId}`: code (4 chars), createdBy, members[], createdAt, expiresAt, status (active|ended|expired)
- `journeys/{journeyId}`: outingId, userId, status, startedAt, arrivedAt, notified
- `notifications/{id}`: outingId, recipientId, type, createdAt, delivered

Journey states: CREATED → ACTIVE → OUT → TRAVELLING → ARRIVED → COMPLETED (also CANCELLED, EXPIRED).

Arrival flow: geofence ENTER → validate there's an active journey → mark ARRIVED locally and in Firestore (queue it if offline) → Cloud Function sends FCM to outing members + accepted trusted contacts (deduplicated) → outing completes for that user.

## Privacy & safety rules (never break)

- Never write the user's live coordinates to Firestore. The only stored location is the saved home.
- Notifications contain only name, status and time (e.g. "Muneeb reached home at 11:42 PM").
- A trusted contact is only active after the other person accepts.
- Ask for location in two steps (foreground first, then "Allow all the time"), with the explanation screens before each.
- Firestore security rules: users read and write only their own user doc. Outing and journey docs are readable only by members.

## How to work in this repo

- Read before writing. Look at the existing code, theme, components and navigation first, and reuse them. Don't create a second version of something that already exists.
- One feature at a time. Finish it, build it, then tell me what changed.
- After every change, run `./gradlew assembleDebug` (and lint for bigger changes). Fix errors before saying you're done.
- Put shared UI in `ui/components` (HomeSafeButton, IconTile, Pill, Avatar, SectionCard, ListRow, Toggle, BottomNav, HeaderBar). Screens must use these, not one-off copies.
- All colors and type come from the theme (HomeSafeTheme). No hardcoded hex or sp values inside screens.
- User-facing text goes in `strings.xml`.
- Never delete files without first showing me the list and getting my OK. Commit before big refactors.
- Don't change Firebase config, `google-services.json`, package name, or signing setup unless I ask.
- If the design and the code conflict, or something is unclear, ask me instead of guessing.
- Keep code simple and readable. Do NOT add code comments.
- Never add "Co-Authored-By" or "Generated with Claude" lines to commit messages or PRs.

## Current phase: UI only

Build screens and navigation only. No permissions, Firebase, DataStore, geofencing or networking until I say so. Use sample data from the design HTML and empty lambdas for actions, so logic can be plugged in later without changing the UI.

## Current priority (MVP)

1. Setup flow + Set home location + geofence registration ← now
2. Start an outing (code/QR) + Active outing
3. Arrival detection → Firestore → Cloud Function → FCM push
4. Join flow (scan QR, enter code, confirm, friend view)
5. Journeys, Companions, Profile/Settings, empty states
