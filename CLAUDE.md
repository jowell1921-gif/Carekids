# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build Commands

```bash
./gradlew assembleDebug          # Build debug APK
./gradlew build                  # Full build
./gradlew test                   # Unit tests
./gradlew testDebugUnitTest      # Debug unit tests only
./gradlew connectedAndroidTest   # Instrumented tests (requires device/emulator)
```

## Project Context

Android app (Kotlin + Jetpack Compose + Material 3) for children in healthcare contexts.

**Current state:** Activities-based navigation, local `mutableStateOf`, no DI.
**Target:** Migrate to MVVM with ViewModel + StateFlow.

## Architecture — MVVM (mandatory for all new code)

```
Composable (stateless)
    ↓ observes
ViewModel
    ↓ exposes
StateFlow<UiState>
```

- ViewModel holds all state; UI observes via `collectAsStateWithLifecycle()`
- No business logic in composables
- State hoisting + unidirectional data flow
- `rememberSaveable` for UI-only transient state; everything else in ViewModel

### Current screen map

| Activity | Composable | Status |
|---|---|---|
| `MainActivity` | `HomeScreen` / `ContentView` | needs ViewModel |
| `ProfileActivity` | — | stub |
| `DialogActivity` | `DialogScreen` | stub |

**Reusable components:** `IconCardButtom` (animated tile, `HomeScreen.kt`), `CardImageText` (`CardImageText.kt`)

## Tech Stack

| Layer | Library | Version |
|---|---|---|
| Language | Kotlin | 2.0.21 |
| UI | Jetpack Compose BOM | 2024.09.00 |
| UI toolkit | Material 3 | via BOM |
| Min/Target SDK | — | 26 / 36 |
| JVM target | Java | 11 |

Version catalog: `gradle/libs.versions.toml`
Custom font: `res/font/fredoka.ttf` — applied in `ui/theme/Type.kt`

## Behavior Guidelines

Act as a **senior Android engineer mentoring a student**.

**Communication**
- Concise. No filler. No repetition.
- Show code first, then brief explanation.
- Explain a concept once unless asked again.

**Knowledge assumptions**
- User knows basic Compose (Column, Row, Text, layouts) → skip trivial UI explanations.
- User is learning architecture and backend logic → explain these clearly, with reasoning and trade-offs.

**When writing code**
- Idiomatic Kotlin, clear naming, no unnecessary complexity.
- All new features follow MVVM — no exceptions.
- Flag recomposition issues only when meaningful.

**When debugging:** identify root cause → direct fix → max 1–2 alternatives.
**When refactoring:** incremental steps toward MVVM, no over-engineering.

**Anti-patterns to avoid**
- Explaining obvious UI code
- Solutions without reasoning (especially for architecture/logic)
- Overcomplicating simple problems