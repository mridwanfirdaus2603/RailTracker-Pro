<div align="center">

# 🚆 RailTracker Pro
### Advanced Real-Time Train Telemetry & GAPEKA 2025 Transit Intelligence

An enterprise-grade, modern Android application engineered with **Kotlin 2.1** and **Jetpack Compose (Material 3)**. RailTracker Pro delivers live railway monitoring, real-time transit schedules based on the official Indonesian Railway Graphic Timetable (**GAPEKA 2025**), intelligent station departure monitors, offline travel logs, and AI-assisted transit insights powered by the **Google Gemini API**.

<br/>

[![Kotlin](https://img.shields.io/badge/Kotlin-2.1.0-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Android](https://img.shields.io/badge/Platform-Android%20(API%2024%2B)-34A853?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com)
[![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose%20M3-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)](https://developer.android.com/jetpack/compose)
[![Architecture](https://img.shields.io/badge/Architecture-MVVM%20%2B%20Repository-orange?style=for-the-badge)](https://developer.android.com/topic/architecture)
[![Room DB](https://img.shields.io/badge/Database-Room%20(SQLite)-4285F4?style=for-the-badge&logo=sqlite&logoColor=white)](https://developer.android.com/training/data-storage/room)
[![Gemini AI](https://img.shields.io/badge/AI%20Engine-Google%20Gemini-886FBF?style=for-the-badge&logo=googlegemini&logoColor=white)](https://ai.google.dev)
[![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)](LICENSE)

<br/>

[Key Features](#-key-features) • [Application Architecture](#-architecture--design) • [Tech Stack](#-technology-stack) • [Installation & Setup](#-getting-started) • [Permissions](#-system-permissions) • [Navigation Flow](#-navigation-flow) • [Contributing](#-contributing)

---

</div>

## 📖 Overview

**RailTracker Pro** addresses the complexities of modern rail transit across Java's intercity rail corridors. By synchronizing comprehensive timetable metadata from **GAPEKA 2025** (*Grafik Perjalanan Kereta Api*) with intuitive UI components, RailTracker Pro empowers commuters, railfans, and logistics planners with accurate trip planning, platform assignments, live tracking simulations, and automated schedule alerts.

---

## ✨ Key Features

<table>
  <tr>
    <td width="50%" valign="top">
      <h3>🗺️ Real-Time Rail Corridor Map</h3>
      <ul>
        <li><b>Dynamic Fleet Simulation:</b> Interactive mapping engine displaying active train services across operational sectors.</li>
        <li><b>Station Waypoints:</b> Geocoded rail junctions and hubs with high-fidelity coordinate rendering.</li>
        <li><b>One-Tap Telemetry:</b> Deep-link into specific rolling stock profiles directly from track markers.</li>
      </ul>
    </td>
    <td width="50%" valign="top">
      <h3>🕒 GAPEKA 2025 Schedule Engine</h3>
      <ul>
        <li><b>Timetable Indexing:</b> Full compatibility with GAPEKA 2025 operational schedule data.</li>
        <li><b>Multi-Class Filtering:</b> Rapid categorization across Executive, Business, and Economy classes.</li>
        <li><b>Query Engine:</b> Instant search and sort across train names, operational codes, origin, and destination nodes.</li>
      </ul>
    </td>
  </tr>
  <tr>
    <td width="50%" valign="top">
      <h3>🏢 Station & Platform Directory</h3>
      <ul>
        <li><b>Hub Profiles:</b> Detailed station indices with operational codes (e.g., GMR, BD, SGU).</li>
        <li><b>Infrastructure Intelligence:</b> Track capacities, platform layouts, and terminal operating hours (04:00 - 23:00).</li>
        <li><b>Passenger Amenities:</b> Catalog of station services including VIP lounges, accessible restrooms, parking, and culinary hubs.</li>
      </ul>
    </td>
    <td width="50%" valign="top">
      <h3>📰 Live Transit Feed & Announcements</h3>
      <ul>
        <li><b>Operational Broadcasts:</b> Real-time service status alerts, infrastructure updates, and maintenance advisories.</li>
        <li><b>Contextual Linking:</b> Seamless redirection from transit advisories into relevant route profiles.</li>
        <li><b>Community Insights:</b> Latest travel guidelines and cross-regional rail transit notices.</li>
      </ul>
    </td>
  </tr>
  <tr>
    <td width="50%" valign="top">
      <h3>📋 Travel Journal & History (Offline-First)</h3>
      <ul>
        <li><b>Persistence via Room:</b> Fully offline SQLite storage preserving completed user journeys.</li>
        <li><b>Journey Archival:</b> Timestamped records of past departures, arrival logs, and personal itineraries.</li>
        <li><b>Reactive State:</b> Kotlin Flow-backed asynchronous updates to the UI layer upon database mutations.</li>
      </ul>
    </td>
    <td width="50%" valign="top">
      <h3>🔔 Precision Departure Alarms & Notifications</h3>
      <ul>
        <li><b>Exact Alarms:</b> Powered by Android <code>AlarmManager</code> with <code>SCHEDULE_EXACT_ALARM</code> support.</li>
        <li><b>Broadcast Dispatch:</b> Background receivers triggering actionable system notifications for departure schedules.</li>
        <li><b>Android 13+ Notification Runtime:</b> Compliant with modern <code>POST_NOTIFICATIONS</code> authorization standards.</li>
      </ul>
    </td>
  </tr>
</table>

---

## 🏗️ Architecture & Design

RailTracker Pro adheres strictly to the **Official Android Architecture Guidelines**, enforcing separation of concerns through an un-directional data flow (**UDF**) pattern with **MVVM (Model-View-ViewModel)**.

```
┌────────────────────────────────────────────────────────┐
│                   UI Presentation Layer                │
│    Jetpack Compose UI • Material 3 Components • Screens│
└──────────────────────────▲─────────────────────────────┘
                           │ StateFlow / Events
┌──────────────────────────▼─────────────────────────────┐
│                     ViewModel Layer                    │
│           HistoryViewModel • App State Holders         │
└──────────────────────────▲─────────────────────────────┘
                           │ Repository Pattern
┌──────────────────────────▼─────────────────────────────┐
│                       Data Layer                       │
│  ┌───────────────────────┐   ┌───────────────────────┐ │
│  │   Local Persistence   │   │     Remote Services   │ │
│  │   Room DB (SQLite)    │   │  Retrofit / Moshi     │ │
│  │   TravelHistoryDao    │   │  Google Gemini Engine │ │
│  └───────────────────────┘   └───────────────────────┘ │
└────────────────────────────────────────────────────────┘
```

### 📂 Directory Structure

```
RailTracker-Pro/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── AndroidManifest.xml           # System permissions & component declarations
│   │   │   ├── java/com/example/
│   │   │   │   ├── MainActivity.kt           # NavHost container & root composable scaffold
│   │   │   │   ├── data/                     # Data Persistence & Repository Contracts
│   │   │   │   │   ├── AppDatabase.kt        # Room database singleton
│   │   │   │   │   ├── TravelHistory.kt      # Travel log entity model
│   │   │   │   │   ├── TravelHistoryDao.kt   # Database access object interfaces
│   │   │   │   │   └── TravelRepository.kt   # Clean abstraction over local data source
│   │   │   │   ├── navigation/               # Navigation Contracts
│   │   │   │   │   └── Routes.kt             # Type-safe Kotlinx serialization routes
│   │   │   │   ├── notifications/            # System Broadcasts & Scheduled Jobs
│   │   │   │   │   ├── NotificationHelper.kt # Notification channel & payload builder
│   │   │   │   │   └── NotificationReceiver.kt# AlarmManager broadcast listener
│   │   │   │   └── ui/                       # Presentation & Design System
│   │   │   │       ├── screens/
│   │   │   │       │   ├── SplashScreen.kt        # Animated entry screen
│   │   │   │       │   ├── FeedScreen.kt          # Transit news & bulletin board
│   │   │   │       │   ├── MapScreen.kt           # Real-time track visualization & markers
│   │   │   │       │   ├── ScheduleScreen.kt      # Train timetable & filter engine
│   │   │   │       │   ├── StationsScreen.kt      # Station hub listings
│   │   │   │       │   ├── StationDetailScreen.kt # Terminal platforms & facility guides
│   │   │   │       │   ├── TrainDetailScreen.kt   # Train composition & route timeline
│   │   │   │       │   ├── HistoryScreen.kt       # Persistent journey history UI
│   │   │   │       │   └── HistoryViewModel.kt    # State & factory for travel histories
│   │   │   │       └── theme/                     # Material 3 Color Schemes & Typography
│   │   │   └── res/                          # Android vector drawables, mipmaps, strings
│   │   ├── test/                             # Unit tests (Robolectric, JUnit, Coroutines)
│   │   └── androidTest/                      # Compose UI & Espresso instrumentation tests
│   ├── build.gradle.kts                      # Module-level Gradle configuration & dependencies
│   └── proguard-rules.pro                    # R8 / ProGuard optimization rules
├── gradle/                                   # Gradle Wrapper configuration
├── .env.example                              # Template for external environment variables
├── build.gradle.kts                          # Root project build configuration
├── settings.gradle.kts                       # Repository & project includes
└── metadata.json                             # AI Studio metadata & system specifications
```

---

## 🛠️ Technology Stack

| Layer | Technology | Description |
|---|---|---|
| **Language** | [Kotlin 2.1.0](https://kotlinlang.org/) | Modern, statically typed language targeting JVM 11 with coroutines support |
| **UI Framework** | [Jetpack Compose](https://developer.android.com/jetpack/compose) | Declarative Android UI toolkit with dynamic state recomposition |
| **Design System** | [Material Design 3](https://m3.material.io/) | Next-generation adaptive themes, container styles, and typography |
| **Navigation** | [Navigation Compose](https://developer.android.com/guide/navigation/design) | Type-safe navigation routes using `@Serializable` contracts |
| **Local Persistence** | [Room 2.6+](https://developer.android.com/training/data-storage/room) | SQLite object-mapping library with asynchronous Flow streaming |
| **Networking** | [Retrofit 2](https://square.github.io/retrofit/) & [OkHttp](https://square.github.io/okhttp/) | Type-safe HTTP client with logging interceptors |
| **Serialization** | [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization) | Compiler-driven JSON serialization and parsing |
| **Image Loading** | [Coil 3](https://coil-kt.github.io/coil/) | Lightweight, Kotlin Coroutine-first asynchronous image loader |
| **AI Integration** | [Google Gemini API](https://ai.google.dev/) | Multimodal generative AI backing predictive route analysis |
| **Async & Concurrency** | [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) | Structured concurrency model for non-blocking I/O operations |
| **Testing Engine** | [Roborazzi](https://github.com/takahirom/roborazzi) & [Robolectric](https://robolectric.org/) | JVM-based UI snapshot testing and Android runtime emulation |

---

## 📱 Navigation Flow

RailTracker Pro utilizes **Type-Safe Navigation Compose** with compile-time safety across destination arguments:

```
                      ┌──────────────────────┐
                      │     SplashScreen     │
                      └──────────┬───────────┘
                                 │
                                 ▼
┌────────────────────────────────────────────────────────────────────────┐
│                      Scaffold (Bottom Navigation Bar)                   │
├──────────────┬──────────────┬──────────────┬─────────────┬─────────────┤
│   Live Map   │   Schedule   │   Stations   │    Feed     │    Logs     │
│   (Map)      │  (Schedule)  │  (Stations)  │   (Feed)    │  (History)  │
└──────┬───────┴──────────────┴──────┬───────┴──────┬──────┴─────────────┘
       │                             │              │
       │                             │              │
       ▼                             ▼              ▼
┌──────────────┐             ┌──────────────┐ ┌──────────────┐
│ TrainDetail  │             │StationDetail │ │ TrainDetail  │
│  (TrainId)   │             │ (StationId)  │ │  (TrainId)   │
└──────────────┘             └──────────────┘ └──────────────┘
```

---

## 🔒 System Permissions

The application requests specific runtime and manifest permissions to deliver location-aware and time-sensitive rail updates:

```xml
<!-- Network communications for schedules and live data feeds -->
<uses-permission android:name="android.permission.INTERNET" />

<!-- Geolocation for pinpointing proximate stations and distance calculations -->
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />

<!-- Timely train boarding alerts (Android 13+) -->
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />

<!-- Precision departure count-down triggers -->
<uses-permission android:name="android.permission.SCHEDULE_EXACT_ALARM" />
```

---

## 🚀 Getting Started

### Prerequisites

Ensure your development environment meets the following specifications:
- **Android Studio:** Ladybug (2024.2.1+) or newer
- **JDK:** Version 11 or higher (OpenJDK 17 recommended)
- **Android SDK:** Compile SDK `36`, Minimum SDK `24` (Android 7.0+)
- **Gemini API Key:** Required for generative transit assistance. Obtain one at [Google AI Studio](https://aistudio.google.com/).

### Installation Steps

1. **Clone the repository:**
   ```bash
   git clone https://github.com/mridwanfirdaus2603/RailTracker-Pro.git
   cd RailTracker-Pro
   ```

2. **Configure environment credentials:**
   Duplicate the example environment file and register your API key:
   ```bash
   cp .env.example .env
   ```
   Open `.env` in an editor and specify your Gemini API credentials:
   ```properties
   GEMINI_API_KEY=AIzaSy...YourValidGeminiKeyHere
   ```

3. **Verify debug signing configuration:**
   The project is preconfigured to link with the Google Secrets Gradle Plugin. If building locally without custom keystores, inspect `app/build.gradle.kts` to verify your target build type.

4. **Compile and assemble debug binary:**
   Using Gradle wrapper:
   ```bash
   ./gradlew clean assembleDebug
   ```

5. **Deploy to emulator or connected hardware:**
   ```bash
   ./gradlew installDebug
   ```
   Alternatively, press **Run ('Shift + F10')** directly inside Android Studio.

---

## 🧪 Testing & Verification

Execute comprehensive unit and UI snapshot tests via Gradle:

```bash
# Execute local unit tests with Robolectric
./gradlew testDebugUnitTest

# Run Roborazzi screenshot verification
./gradlew verifyRoborazziDebug
```

---

## 🤝 Contributing

Contributions are welcomed! Follow these standard procedures to submit changes:

1. **Fork the Repository** on GitHub.
2. **Create a Feature Branch:**
   ```bash
   git checkout -b feature/rail-telemetry-enhancement
   ```
3. **Commit your modifications** following conventional commits:
   ```bash
   git commit -m "feat(telemetry): add live speed and velocity estimation"
   ```
4. **Push to your origin branch:**
   ```bash
   git push origin feature/rail-telemetry-enhancement
   ```
5. **Open a Pull Request** targeting the `main` branch with a comprehensive description of changes.

---

## 📄 License

Distributed under the **MIT License**. Refer to the [LICENSE](LICENSE) file for complete details.

---

<div align="center">

### Developed by [Muhammad Ridwan Firdaus](https://github.com/mridwanfirdaus2603)

If you find this project valuable, please consider giving it a ⭐ on [GitHub](https://github.com/mridwanfirdaus2603/RailTracker-Pro)!

</div>
