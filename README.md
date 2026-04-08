# Rick & Morty — Locations (KMP)

Kotlin Multiplatform application that browses locations from the [Rick and Morty API](https://rickandmortyapi.com/).
Targets **Android** and **Desktop (JVM)** from a single shared codebase.

---

## Architecture

The project follows Clean Architecture with three layers and a cross-platform native layer:

```
┌─────────────────────────────────────────────────────┐
│  Presentation                                        │
│  LocationListScreen ──► LocationListViewModel        │
│  LocationDetailScreen ──► LocationDetailViewModel    │
│  DesktopScreen (master-detail)  /  MobileNavGraph    │
├─────────────────────────────────────────────────────┤
│  Domain                                              │
│  LocationRepository (interface)                      │
│  Location, LocationPage (models)                     │
├─────────────────────────────────────────────────────┤
│  Data                                                │
│  LocationRepositoryImpl  ──► LocationCache (memory)  │
│                          └──► LocationService (Ktor) │
│  DTOs + Mappers                                      │
├─────────────────────────────────────────────────────┤
│  Cross-Native                                        │
│  SoundManager (expect/actual: Android / JVM)         │
│  PlatformModule (expect/actual: HttpClient engine)   │
└─────────────────────────────────────────────────────┘
```

State is managed with the **UDF/MVI** pattern: each screen has a single immutable `UiState`
exposed as a `StateFlow`, mutated only through typed `Action` events dispatched to the ViewModel.

---

## Project structure

```
composeApp/src/
├── commonMain/       # Shared code (all platforms)
│   └── …/
│       ├── App.kt                        # Root composable, layout selector
│       ├── Platform.kt                   # expect interface (isDesktop flag)
│       ├── audio/SoundManager.kt         # expect class
│       ├── di/AppModule.kt               # Koin shared module
│       ├── domain/
│       │   ├── model/                    # Location, LocationPage
│       │   └── repository/               # LocationRepository interface
│       ├── data/
│       │   ├── remote/                   # Ktor service + DTOs
│       │   ├── local/                    # In-memory cache
│       │   ├── mapper/                   # DTO → domain model
│       │   └── repository/               # LocationRepositoryImpl
│       ├── navigation/                   # AppRoute, MobileNavGraph
│       └── presentation/
│           ├── components/               # LocationCard
│           ├── desktop/                  # DesktopScreen
│           ├── locationlist/             # Screen, ViewModel, UiState, Action
│           └── locationdetail/           # Screen, ViewModel, UiState, Action
├── androidMain/      # Android-specific implementations
│   └── …/
│       ├── audio/SoundManager.android.kt # ToneGenerator
│       ├── di/PlatformModule.kt          # HttpClient(Android)
│       └── MainActivity.kt
└── jvmMain/          # Desktop-specific implementations
    └── …/
        ├── audio/SoundManager.jvm.kt     # Toolkit.beep()
        ├── di/PlatformModule.kt          # HttpClient(Java)
        └── main.kt
```

---

## Key dependencies

| Library | Version | Role |
|---|---|---|
| Ktor Client | 3.1.3 | HTTP requests to the Rick and Morty API |
| kotlinx.serialization | 1.8.1 | JSON deserialization of API responses |
| Koin | 4.0.4 | Dependency injection (singletons + ViewModels) |
| Navigation Compose | 2.8.0-alpha10 | Type-safe navigation stack (mobile only) |
| Compose Multiplatform | (BOM) | UI framework shared across Android and Desktop |

---

## Run the application

### Android

In Android Studio, select the `composeApp` run configuration and target a device/emulator.

Or from the terminal:
```shell
.\gradlew.bat :composeApp:assembleDebug
```

### Desktop (JVM)

```shell
.\gradlew.bat :composeApp:run
```

---

## Technical decisions

### Cache-first fetch strategy
`LocationRepositoryImpl` checks `LocationCache` before every network call. On a hit, the
cached data is returned immediately. On a miss, the remote result is written to the cache
before being returned. This means that locations browsed in the list are available instantly
when the user opens the detail screen, with no duplicate network requests.

### Alphabetical sorting (client-side)
The Rick and Morty API does not support a sort parameter for locations. Sorting is applied
client-side in `LocationListViewModel.loadPage()` after each page is appended to the
accumulated list. The full list is re-sorted on each page load.

### expect/actual SoundManager
`SoundManager` is declared as an `expect class` in `commonMain` with a no-arg constructor.
This lets Koin inject it without any platform-specific factory. Each platform provides its
own `actual` implementation:
- **Android**: `ToneGenerator(STREAM_MUSIC, 40)` with `TONE_PROP_ACK` (100 ms) — soft
  acknowledgment tone, no Context required.
- **Desktop**: `Toolkit.getDefaultToolkit().beep()` — delegates to the OS system alert sound.

### Desktop ViewModel isolation
On Desktop, Jetpack Navigation is not used, so all composables share the same
Window-level `ViewModelStore`. Without additional precautions, `koinViewModel()` would
reuse the same `LocationDetailViewModel` instance when the user selects a different location.
This is solved by passing `key = locationId.toString()` to `koinViewModel()`, which forces a
new ViewModel to be created for each distinct location ID.

### Single vs array character endpoint
`GET /character/{id}` returns a JSON object when only one ID is requested, but a JSON array
when multiple IDs are passed. `LocationService.fetchCharacters()` handles this by
deserializing as `CharacterDto` (single) or `List<CharacterDto>` (multiple) based on the
number of requested IDs.
