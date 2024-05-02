# Code Database Android

This is an android app which consumes the REST API defined in [Code Database](https://github.com/Subikesh/Code-Database) which was done with django-rest-framework.

## Key Features 

 1. Kotlin multiplatform data layer
 2. Jetpack Compose and Material 3 design
 3. Model View Intent(MVI) architecture followed
 4. Token Authentication for user login/logout

> I know it's a lot. Let me explain one by one...

## 1. Kotlin Multiplatform Data Layer

The backend data layer is done completely as a **Kotlin Multiplatform(KMP)** library and integrated with the android app. The iOS app is not configured yet, but can be done with minimal setup.

Features: 
 * Network calls with **Ktor client**
 * Followed **clean architecture** and manual dependency injection done using **[service locator pattern](https://en.wikipedia.org/wiki/Service_locator_pattern)**
 * Response serialization done with **Kotlinx serialization**
 * Local in-memory cache maintained for swift search and filter functionalities

