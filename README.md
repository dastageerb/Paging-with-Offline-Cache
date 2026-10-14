# Paging with Offline Cache

An Android sample showing how to build paginated lists that work offline — Paging 3 with a `RemoteMediator` backed by a Room database, so the local cache is the single source of truth and the network just refreshes it.

## What this demonstrates

- Paging 3 with `RemoteMediator` — paged network loads stitched into a local cache
- Room database as the single source of truth (UI never talks to the network directly)
- Offline-first: cached pages render instantly with no connectivity; refresh when back online
- Clean Architecture + MVVM: data, domain, and presentation layers separated
- Dagger Hilt for dependency injection, Retrofit for the API layer

## Demo

![Demo](demo.gif)

## Run it

1. Clone the repo
2. Open in Android Studio
3. Run on an emulator or device (API 26+)
4. Toggle airplane mode to see the offline cache in action

## Stack

- Kotlin
- Paging 3, Room, Retrofit
- Dagger Hilt
- MVVM + Clean Architecture
- Min SDK 26

## License

MIT
