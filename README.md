<p align="center"><img src="docs/banner.png" alt="HomeSafe — Go out. Come home. We'll tell them." width="100%"></p>

HomeSafe automatically lets your friends and family know when you've safely reached home after an outing — so nobody has to remember to send the "I'm home" message.

<p align="center">
<img src="https://img.shields.io/badge/Kotlin-2.2.10-7F52FF?logo=kotlin&logoColor=white" alt="Kotlin">
<img src="https://img.shields.io/badge/Jetpack%20Compose-BOM%202026.02.01-4285F4?logo=jetpackcompose&logoColor=white" alt="Jetpack Compose">
<img src="https://img.shields.io/badge/Firebase-Analytics%20%7C%20Crashlytics-FFCA28?logo=firebase&logoColor=black" alt="Firebase">
<img src="https://img.shields.io/badge/minSdk-24-3DDC84?logo=android&logoColor=white" alt="Min SDK 24">
<img src="https://img.shields.io/badge/status-in%20development-yellow" alt="Status: in development">
</p>

## Screens

Real screenshots from the running app (emulator, debug build).

### First run & setup

<table>
<tr>
<td align="center"><img src="docs/screenshots/01_splash.png" width="200"><br><sub>Splash</sub></td>
<td align="center"><img src="docs/screenshots/03_setup_background_location.png" width="200"><br><sub>Setup — background location</sub></td>
<td align="center"><img src="docs/screenshots/04_setup_notifications.png" width="200"><br><sub>Setup — notifications</sub></td>
<td align="center"><img src="docs/screenshots/05_setup_home_location.png" width="200"><br><sub>Setup — set home</sub></td>
</tr>
</table>

### Home & alerts

<table>
<tr>
<td align="center"><img src="docs/screenshots/06_home.png" width="200"><br><sub>Home</sub></td>
<td align="center"><img src="docs/screenshots/07_alerts.png" width="200"><br><sub>Alerts</sub></td>
</tr>
</table>

### Starting an outing

<table>
<tr>
<td align="center"><img src="docs/screenshots/08_start_outing.png" width="200"><br><sub>Start an outing</sub></td>
<td align="center"><img src="docs/screenshots/09_add_trusted_contact.png" width="200"><br><sub>Add trusted contact</sub></td>
<td align="center"><img src="docs/screenshots/10_active_outing.png" width="200"><br><sub>Active outing</sub></td>
</tr>
</table>

### Joining an outing

<table>
<tr>
<td align="center"><img src="docs/screenshots/11_scan_qr.png" width="200"><br><sub>Scan QR</sub></td>
<td align="center"><img src="docs/screenshots/12_enter_outing_code.png" width="200"><br><sub>Enter outing code</sub></td>
<td align="center"><img src="docs/screenshots/13_join_confirmation.png" width="200"><br><sub>Join confirmation</sub></td>
<td align="center"><img src="docs/screenshots/14_joined_outing.png" width="200"><br><sub>Joined outing</sub></td>
</tr>
</table>

### Arrival

<table>
<tr>
<td align="center"><img src="docs/screenshots/15_arrival_confirmation.png" width="200"><br><sub>Arrival confirmation</sub></td>
</tr>
</table>

### Journeys

<table>
<tr>
<td align="center"><img src="docs/screenshots/16_journeys.png" width="200"><br><sub>Journeys</sub></td>
<td align="center"><img src="docs/screenshots/17_journey_details.png" width="200"><br><sub>Journey details</sub></td>
</tr>
</table>

### Companions

<table>
<tr>
<td align="center"><img src="docs/screenshots/18_companions.png" width="200"><br><sub>Companions</sub></td>
<td align="center"><img src="docs/screenshots/19_contact_details.png" width="200"><br><sub>Contact details</sub></td>
</tr>
</table>

### Profile

<table>
<tr>
<td align="center"><img src="docs/screenshots/20_profile.png" width="200"><br><sub>Profile</sub></td>
<td align="center"><img src="docs/screenshots/21_edit_profile.png" width="200"><br><sub>Edit profile</sub></td>
</tr>
</table>

## How it works

1. Start an outing
2. Friends join with a QR code or a 4-letter code
3. Go out and forget the app
4. Get home and your friends are notified automatically

## Features

- Automatic arrival detection using geofencing — no constant GPS tracking
- Temporary outing companions, scoped to a single outing
- Trusted contacts, only active after the other person accepts
- Privacy-first: shares arrival status only, never live location
- Works in the background, without opening the app

## Privacy

HomeSafe shares arrival status and time only — for example, "Muneeb reached home at 11:42 PM." No live location, no routes, and no location history are ever shared.

## Tech stack

**In use**
- Kotlin
- Jetpack Compose + Material 3
- Navigation Compose
- Firebase Analytics
- Firebase Crashlytics

**Planned**
- Firebase Auth
- Firestore
- Firebase Cloud Messaging (FCM)
- Cloud Functions
- Geofencing (Google Play Services)
- WorkManager

## Getting started


1. Clone the repo:
   ```
   git clone https://github.com/Muneeb-baloch/Home-Safe-.git
   ```
2. Open the project in Android Studio.
3. Run on a device or emulator (API 24+).

`google-services.json` is already included for Firebase Analytics and Crashlytics. If you connect your own Firebase project later, replace it with your own file from the Firebase console.

## Author

Muneeb ur Rehman
