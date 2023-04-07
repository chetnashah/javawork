https://developer.android.com/build/migrate-to-catalogs

Using Gradle version catalogs makes managing dependencies and plugins easier when you have multiple modules. Instead of hardcoding dependency names and versions in individual build files and updating each entry whenever you need to upgrade a dependency, you can create a central version catalog of dependencies that various modules can reference in a type-safe way with Android Studio assistance

Example: https://github.com/android/nowinandroid/blob/main/gradle/libs.versions.toml

## libs.versions.toml

Create `libs.versions.toml` in gradle folder.

```
// libs.version.toml
[versions]

[libraries]

[plugins]
```
The sections are used as follows:

* In the `versions` block, define variables that hold the versions of your dependencies and plugins. You use these variables in the subsequent blocks (the versions and plugins blocks).
* In the libraries block, define your dependencies.
* In the plugins block, define your plugins.

E.g. following:
```kts
plugins {
   id("com.android.application") version "7.4.1" apply false
}
dependencies {
    implementation("androidx.core:core-ktx:1.9.0")
}
```
is migrated to new way:::
```
[versions]
ktx = "1.9.0"
androidGradlePlugin = "7.4.1"

[libraries]
androidx-ktx = { group = "androidx.core", name = "core-ktx", version.ref = "ktx" }

[plugins]
android-application = { id = "com.android.application", version.ref = "androidGradlePlugin" }
```
along with
```
dependencies {
   implementation(libs.androidx.ktx)
}
```