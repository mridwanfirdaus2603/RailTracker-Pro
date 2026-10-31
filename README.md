<div align="center">

# 🚆 RailTracker Pro

### Real-Time Train Monitoring & GAPEKA 2025 Schedule Tracker

[![Kotlin](https://img.shields.io/badge/Kotlin-2.1.0-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-Material%203-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)](https://developer.android.com/jetpack/compose)
[![Android](https://img.shields.io/badge/Android-API%2024+-34A853?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com)
[![Gemini AI](https://img.shields.io/badge/Gemini%20AI-Powered-886FBF?style=for-the-badge&logo=googlegemini&logoColor=white)](https://ai.google.dev)
[![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)](LICENSE)

**RailTracker Pro** adalah aplikasi Android untuk memantau jadwal kereta api Indonesia secara real-time berdasarkan **GAPEKA 2025**, dilengkapi dengan live map, station monitor, news feed, dan travel history.

<br/>

[📱 Download APK](#-installation) · [✨ Features](#-features) · [🏗️ Architecture](#%EF%B8%8F-architecture) · [🚀 Getting Started](#-getting-started)

</div>

---

## ✨ Features

<table>
<tr>
<td width="50%">

### 🗺️ Live Map
- Visualisasi posisi kereta secara real-time
- Rute jalur kereta di Pulau Jawa
- Klik marker kereta untuk melihat detail perjalanan

### 🕐 Schedule (GAPEKA 2025)
- Jadwal kereta berdasarkan GAPEKA terbaru
- Filter berdasarkan kelas (Eksekutif, Bisnis, Ekonomi)
- Pencarian jadwal berdasarkan stasiun & tujuan

### 🏢 Station Monitor
- Daftar stasiun lengkap dengan kode stasiun
- Detail stasiun: alamat, fasilitas, & jadwal keberangkatan
- Pencarian stasiun berdasarkan nama atau kode

</td>
<td width="50%">

### 📰 Feed
- Berita & informasi terkini seputar kereta api
- Update gangguan perjalanan & pengumuman
- Navigasi langsung ke detail kereta dari feed

### 📋 Travel History (Logs)
- Riwayat perjalanan tersimpan secara lokal
- Database offline menggunakan Room
- Catat perjalanan kereta yang pernah dinaiki

### 🔔 Smart Notifications
- Pengingat jadwal keberangkatan kereta
- Notifikasi berbasis alarm yang tepat waktu
- Konfigurasi pengingat sesuai preferensi

</td>
</tr>
</table>

---

## 🏗️ Architecture

```
app/
├── src/main/java/com/example/
│   ├── MainActivity.kt                  # Entry point & Navigation Host
│   ├── data/                            # Data Layer
│   │   ├── AppDatabase.kt               # Room Database
│   │   ├── TravelHistory.kt             # Entity
│   │   ├── TravelHistoryDao.kt          # DAO
│   │   └── TravelRepository.kt          # Repository
│   ├── navigation/                      # Navigation
│   │   └── Routes.kt                    # Type-safe routes
│   ├── notifications/                   # Notifications
│   │   ├── NotificationHelper.kt        # Notification builder
│   │   └── NotificationReceiver.kt      # Broadcast receiver
│   └── ui/                              # Presentation Layer
│       ├── screens/
│       │   ├── SplashScreen.kt           # Splash / Loading
│       │   ├── FeedScreen.kt             # News Feed
│       │   ├── MapScreen.kt              # Live Map
│       │   ├── ScheduleScreen.kt         # Train Schedule
│       │   ├── StationsScreen.kt         # Station List
│       │   ├── StationDetailScreen.kt    # Station Detail
│       │   ├── TrainDetailScreen.kt      # Train Detail
│       │   ├── HistoryScreen.kt          # Travel Logs
│       │   └── HistoryViewModel.kt       # History State
│       └── theme/                        # Material 3 Theme
└── src/main/res/                         # Resources
```

---

## 🛠️ Tech Stack

| Category | Technology |
|---|---|
| **Language** | Kotlin 2.1.0 |
| **UI Framework** | Jetpack Compose + Material 3 |
| **Navigation** | Jetpack Navigation Compose (Type-Safe) |
| **Database** | Room (SQLite) |
| **Networking** | Retrofit + OkHttp + Moshi |
| **Image Loading** | Coil Compose |
| **AI Integration** | Gemini API |
| **Serialization** | Kotlinx Serialization |
| **Architecture** | MVVM + Repository Pattern |
| **Async** | Kotlin Coroutines + Flow |
| **Min SDK** | API 24 (Android 7.0) |
| **Target SDK** | API 36 |

---

## 🚀 Getting Started

### Prerequisites

- [Android Studio](https://developer.android.com/studio) (Ladybug atau lebih baru)
- JDK 11+
- Android SDK API 36
- [Gemini API Key](https://ai.google.dev/) (untuk fitur AI)

### Installation

1. **Clone repository**
   ```bash
   git clone https://github.com/mridwanfirdaus2603/RailTracker-Pro.git
   cd RailTracker-Pro
   ```

2. **Setup environment variables**
   ```bash
   cp .env.example .env
   ```
   Edit `.env` dan masukkan Gemini API key kamu:
   ```
   GEMINI_API_KEY=your_api_key_here
   ```

3. **Build configuration**
   
   Untuk menjalankan dalam mode debug, hapus baris berikut di `app/build.gradle.kts`:
   ```kotlin
   signingConfig = signingConfigs.getByName("debugConfig")
   ```

4. **Run the app**
   - Buka project di Android Studio
   - Sync Gradle
   - Run pada emulator atau perangkat fisik

---

## 📱 App Navigation

```
┌─────────────┐
│   Splash     │
└──────┬──────┘
       │
       ▼
┌──────────────────────────────────────────────────┐
│              Bottom Navigation               │
├─────────┬──────────┬─────────┬──────┬────────┤
│ Live Map│ Schedule │Stations │ Feed │  Logs  │
│   🗺️   │   🚆    │   🏢   │  📰  │   📋   │
└────┬────┴──────────┴────┬────┴───┬──┴────────┘
     │                    │        │
     ▼                    ▼        ▼
┌──────────┐      ┌───────────┐ ┌──────────┐
│  Train   │      │  Station  │ │  Train   │
│  Detail  │      │  Detail   │ │  Detail  │
│   🚂    │      │    🏢    │ │    🚂   │
└──────────┘      └───────────┘ └──────────┘
```

---

## 🤝 Contributing

Kontribusi sangat diterima! Silakan:

1. Fork repo ini
2. Buat branch baru (`git checkout -b feature/fitur-baru`)
3. Commit perubahan (`git commit -m 'Menambah fitur baru'`)
4. Push ke branch (`git push origin feature/fitur-baru`)
5. Buat Pull Request

---

## 📄 License

Project ini dilisensikan di bawah MIT License - lihat file [LICENSE](LICENSE) untuk detail.

---

<div align="center">

### Made with ❤️ by [Muhammad Ridwan Firdaus](https://github.com/mridwanfirdaus2603)

⭐ **Star this repo** jika kamu merasa project ini bermanfaat!

</div>
