# My Pantry

A Java Android application for the Mobile App Development 700 practical assignment that helps users manage pantry ingredients. It suggests recipes only when every required ingredient is present.

## The features
- Pantry create, read, update and delete (CRUD) using SQLite.
- Ingredient quantity, unit and optional expiry date with validation.
- 20 seeded recipes stored in SQLite on first install.
- Strict recipe matching: every listed ingredient must be in the pantry; incomplete matches are excluded.
- Recipe detail screen with ingredient list and method.
- Settings screen with a saved expiring-soon reminder preference.
- XML layouts, scrollable screens, RecyclerView adapters, Intents and simple bottom navigation.
- Minimal beige / olive / white / yellow visual system. Ingredient panel uses olive background with white text.

## The tech
- Java, Android SDK, AndroidX AppCompat, Material Components and RecyclerView.
- SQLite via `SQLiteOpenHelper` for local persistent storage.

## To run
1. Install Android Studio (recent version) and Android SDK 35.
2. Open this folder in Android Studio.
3. Allow Gradle sync to complete.
4. Run the `app` configuration on an emulator or Android device (Android 9.0+).
5. Add ingredients from Pantry. Use exact recipe ingredient names for best results (for example `tomato`, `egg`, `salt`, `pasta`, `garlic`, `potato`, `cheese`, `milk`, `banana`, `oats`).

