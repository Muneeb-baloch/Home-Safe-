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

## Layout rules (every screen)

- Spacing is fixed: use `Arrangement.spacedBy(16.dp)` (14.dp on Home) between sections. Never use `SpaceBetween`, `SpaceEvenly`, `SpaceAround` or `weight()` to spread content vertically.
- Screen structure: [top bar] + [scrollable content: `LazyColumn` or `Column.verticalScroll`] + [bottom action area pinned at the bottom, outside the scroll, with 16dp top padding and navigationBars inset].
- Content must never shrink or clip: no fixed heights on lists or cards; cards wrap their content.
- Rows of chips/pills: single line, horizontally scrollable (`LazyRow`) with 20dp content padding — never cut off at the screen edge.
- Only the status bar inset is applied once at the top; only the navigation bar inset once at the bottom.
- Every screen must use `HomeSafeScreen`. Never set status bar color or top insets inside a screen.

## Keyboard rules (every screen with text input)

- AndroidManifest: activity uses `android:windowSoftInputMode="adjustResize"`. With edge-to-edge, handle the keyboard with `WindowInsets.ime`.
- The bottom action area (pinned button) gets `Modifier.imePadding()` so it sits right above the keyboard, never behind it.
- Scrollable content also respects the keyboard (`imePadding` or `contentPadding` from `WindowInsets.ime`) so the focused field is never hidden; scroll the focused field into view (`BringIntoViewRequester`).
- Never apply `imePadding` twice (not on both the Scaffold and the content).
- Correct keyboard type per field: email → `KeyboardType.Email`, phone → `Phone`, password → `Password`, outing code → `Ascii` + `KeyboardCapitalization.Characters`, names → `Words` capitalization.
- IME actions: `Next` moves to the next field, `Done`/`Search` closes the keyboard and triggers the screen action.
- Tap outside a field or start scrolling → clear focus and hide the keyboard.
- When the keyboard is open, nothing overlaps, clips or jumps; the top bar stays in place.

## Verify before saying "done" (every UI change)

1. Compare each changed screen side by side with its HTML in `design/screens`: top bar position, paddings, gaps, font sizes, colors.
2. Test edge cases on the emulator: 0 items, many items (e.g. select all), long names, and font size set to Large in Android settings. Nothing may overlap, clip, stretch or run off-screen.
3. Check the top bar matches every other screen (same `HomeSafeTopBar`, same position).
4. On every screen with a text field: open the keyboard and check the focused field and the bottom button stay visible above it, then close it and check the layout returns to normal.
5. Tell me what you checked and anything that still differs.

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

## Current phase: business logic (fast MVP pass)

The UI-only phase is done. We are now wiring up the real outing/arrival loop, staged into milestones below. Each milestone must build (`./gradlew assembleDebug`) and be verified before moving to the next.

**Intentional, temporary divergences from the sections above** — restore these in a later pass once this loop is proven:

| Section above says | This pass does instead | Why |
|---|---|---|
| Firebase Auth | Locally-generated stub UUID (DataStore), behind one `UserIdentityProvider` interface | Move fast now; swapping in real Auth later is a small change, not a rewrite |
| GeofencingClient for arrival detection | Manual "Heading home" button writes `ARRIVED` to Firestore directly | Prove the notification pipeline before tackling geofencing (flaky to test, needs a real home location first) |
| Cloud Functions | Cloudflare Worker (free plan) — re-validates the journey against Firestore with its own service-account credentials before sending, so it's safe without real Auth | Blaze billing activation isn't going through; Workers avoid the card entirely |
| Google Maps for home location, CameraX/ML Kit QR | Both stay decorative/sample for now | Out of scope for this pass |

Full milestone plan (see `/Users/muneebbaloch/.claude/plans/tell-me-ffrom-where-immutable-moth.md` for complete file-level detail):

- [x] **M1 — Foundational plumbing.** Hilt DI, domain models (`JourneyStatus`, `OutingStatus`, `User`, `Outing`, `Journey`, `TrustedContact`), stub identity (`UserIdentityProvider` + DataStore), Firestore repositories (`UserRepository`, `OutingRepository`, `JourneyRepository`), `ProfileViewModel` wired to real `users/{uid}.name`.
- [ ] **M2 — Start Outing.** Real Firestore writes (`outings/{id}`, `journeys/{id}`) from `StartOutingScreen`; `ActiveOutingScreen` goes live via snapshot listeners.
- [ ] **M3 — Join by code.** Real lookup-by-code + join flow (`EnterOutingCodeScreen`, `JoinConfirmationScreen`); `JoinedOutingScreen` goes live.
- [ ] **M4 — Manual arrival → Cloudflare Worker → FCM → client receives it.** `ArrivalConfirmationScreen` writes `ARRIVED` + calls the Worker; `HomeSafeMessagingService` handles FCM token/receive; `AlertsScreen` goes live; Worker project at `worker/` resolves companions, sends FCM, writes `notifications/{id}`.
- [ ] **M5 — Minimal Firestore security rules.** Field-shape allowlist (reject any `lat`/`lng`/`location` field on `journeys`/`outings`), `notifications` writes rejected from clients (Worker's service account bypasses rules).

## Original priority order (for after this pass)

1. Setup flow + Set home location + geofence registration
2. Start an outing (code/QR) + Active outing
3. Arrival detection → Firestore → Cloud Function → FCM push
4. Join flow (scan QR, enter code, confirm, friend view)
5. Journeys, Companions, Profile/Settings, empty states
