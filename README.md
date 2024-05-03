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

## 2. Jetpack Compose and Material 3 design

The application's UI is done using **Jetpack Compose** using **compose-navigation** and following the **Material 3 design guidelines** inspired by .


<p>
    <img src="https://github.com/Subikesh/CodeDatabase_android/assets/53510640/0e8cece3-7b4d-4193-b358-e2acaed4c08c" alt="CodeDatabase_android_screenshot" width=350px>
    <em>In-app screenshot of list view and search</em>
</p>

## 3. Model View Intent(MVI) architecture

 * The app uses the **Model View Intent** architecture by declaring the **UIStates** in screen's view model
 * The screen only reacts to the changes happening in the UIStates which enforces **unidirectional data flow**
 * Any operations from the screen are posted as **Events** to view model which processes the event and updates the UI state if required
 * The app's architecture follows the common guidelines recommended by [android documentation](https://developer.android.com/topic/architecture)

## 4. Token Authentication for user login/logout

 * **User login/logout** functionalities are also implemented in the app
 * A token generation API was written in the web app using [**Django rest framework authtoken**](https://www.django-rest-framework.org/api-guide/authentication/#tokenauthentication) and a token is generated on every user login
 * Every calls in app will pass that token as a Authorization header to get that user specific response
 * On logout the token will be removed. The refresh and revoke access token is yet to be implemented.

