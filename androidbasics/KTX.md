
Android KTX is a set of Kotlin extensions that are included with Android Jetpack and other Android libraries. KTX extensions provide concise, idiomatic Kotlin to Jetpack, Android platform, and other APIs.

Full list of extensions: https://developer.android.com/kotlin/ktx/extensions-list

## Core ktx

provides extensions for common libraries that are part of the Android framework.
find code at: https://github.com/androidx/androidx/tree/androidx-main/core/core-ktx/src/main/java/androidx/core
Here are the part of it:
* androidx.core.animation
* androidx.core.content
* androidx.core.content.res
* androidx.core.database
* androidx.core.database.sqlite
* androidx.core.graphics
* androidx.core.graphics.drawable
* androidx.core.location
* androidx.core.net
* androidx.core.os
* androidx.core.text
* androidx.core.transition
* androidx.core.util
* androidx.core.view
* androidx.core.widget

```groovy
dependencies {
    implementation("androidx.core:core-ktx:1.7.0")
}
```

## Collection ktx

contain utility functions for working with Android's memory-efficient collection libraries, including `ArrayMap`, `LongSparseArray`, `LruCache`.
```groovy
dependencies {
    implementation("androidx.collection:collection-ktx:1.2.0")
}
```

## Fragment KTX

The Fragment KTX module provides a number of extensions to simplify the fragment API.


```groovy
dependencies {
    implementation("androidx.fragment:fragment-ktx:1.4.1")
}
```

Example:
```kt
fragmentManager().commit {
   addToBackStack("...")
   setCustomAnimations(
           R.anim.enter_anim,
           R.anim.exit_anim)
   add(fragment, "...")
}
```

## Lifecycle KTX

```groovy
dependencies {
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.0-rc01")
}
```

## ViewModel KTX

The ViewModel KTX library provides a `viewModelScope()` function that makes it easier to launch coroutines from your ViewModel. The `CoroutineScope` is bound to `Dispatchers.Main` and is automatically cancelled when the `ViewModel` is cleared. You can use `viewModelScope()` instead of creating a new scope for each ViewModel.

```groovy
dependencies {
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.0-rc01")
}
```

```kt
class MainViewModel : ViewModel() {
    // Make a network request without blocking the UI thread
    private fun makeNetworkRequest() {
        // launch a coroutine in viewModelScope
        viewModelScope.launch  {
            remoteApi.slowFetch()
            ...
        }
    }

    // No need to override onCleared()
}
```

## LiveData KTX

```groovy
dependencies {
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.0-rc01")
}
```

## Navigation KTX

```groovy
dependencies {
    implementation("androidx.navigation:navigation-runtime-ktx:2.4.2")
    implementation("androidx.navigation:navigation-fragment-ktx:2.4.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.4.2")
}
```

## ROom ktx
Room extensions add coroutines support for database transactions.

```groovy
dependencies {
    implementation("androidx.room:room-ktx:2.4.2")
}
```

Here are a couple of examples where Room now uses coroutines. The first example uses a suspend function to return a list of User objects, while the second utilizes Kotlin's Flow to asynchronously return the User list. Note that when using Flow, you're also notified of any changes in the tables you're querying.

```kt
@Query("SELECT * FROM Users")
suspend fun getUsers(): List<User>

@Query("SELECT * FROM Users")
fun getUsers(): Flow<List<User>>
```

## Example SharedPreferences.edit

Without ktx:
```java
sharedPreferences
        .edit()  // create an Editor
        .putBoolean("key", value)
        .apply() // write to disk asynchronously

```

With ktx:
```kotlin
// SharedPreferences.edit extension function signature from Android KTX - Core
// inline fun SharedPreferences.edit(
//         commit: Boolean = false,
//         action: SharedPreferences.Editor.() -> Unit)

// Commit a new value asynchronously
sharedPreferences.edit { putBoolean("key", value) }

// Commit a new value synchronously
sharedPreferences.edit(commit = true) { putBoolean("key", value) }
```

