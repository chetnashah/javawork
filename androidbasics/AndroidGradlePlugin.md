
Android Studio 4.2 can open projects that use AGP 3.1 and higher provided that AGP is running Gradle 4.8.1 and higher


### Where is the source code for Android Gradle plugin?

It actually sits with "Android studio" source code.

https://android.googlesource.com/platform/tools/base/+/studio-master-dev/source.md

The plugin code can be found under `android-studio-master-dev/tools/base`.

Majority of the android gradle plugin source code sits in `tools/base/build-system` (https://cs.android.com/android-studio/platform/tools/base/+/mirror-goog-studio-main:build-system/). This is where you should find tasks defined from android gradle plugin.

### where to find googles published lib on maven

https://maven.google.com/web/index.html?q=room#androidx.room:room-compiler

### Where are all the common plugins implemented in gradle?

Common android gradle exposed plugins like `com.android.application` , `com.android.library` etc are implemented as below:

The source of truth is at: https://cs.android.com/android-studio/platform/tools/base/+/mirror-goog-studio-main:build-system/gradle-core/build.gradle;l=81

Full list available at: https://cs.android.com/android-studio/platform/tools/base/+/mirror-goog-studio-main:build-system/gradle-core/src/main/java/com/android/build/gradle/internal/plugins/

```
gradlePlugin {
    testSourceSets sourceSets.apiTest
    plugins {
        comAndroidApplication {
            id = "com.android.application"
            implementationClass = "com.android.build.gradle.AppPlugin"
        }
        comAndroidLibrary {
            id = "com.android.library"
            implementationClass = "com.android.build.gradle.LibraryPlugin"
        }
        comAndroidDynamicFeature {
            id = "com.android.dynamic-feature"
            implementationClass = "com.android.build.gradle.DynamicFeaturePlugin"
        }
        comAndroidAssetPack {
            id = "com.android.asset-pack"
            implementationClass = "com.android.build.gradle.AssetPackPlugin"
        }
        comAndroidAssetOnlyBundle {
            id = "com.android.asset-pack-bundle"
            implementationClass = "com.android.build.gradle.AssetPackBundlePlugin"
        }
        comAndroidLint {
            id = "com.android.lint"
            implementationClass = "com.android.build.gradle.LintPlugin"
        }
        comAndroidTest {
            id = "com.android.test"
            implementationClass = "com.android.build.gradle.TestPlugin"
        }
        comAndroidFusedLibrary {
            id = "com.android.fused-library"
            implementationClass = "com.android.build.gradle.api.FusedLibraryPlugin"
        }
        comAndroidSdkLibrary {
            id = "com.android.privacy-sandbox-sdk"
            implementationClass = "com.android.build.gradle.api.PrivacySandboxSdkPlugin"
        }
        comAndroidKotlinMultiplatform {
            id = "com.android.kotlin.multiplatform.library"
            implementationClass = "com.android.build.gradle.api.KotlinMultiplatformAndroidPlugin"
        }
    }
}

```

### setting AGP version

specify along with the plugins 
```kt
plugins {
    id("com.android.application") version "7.1.2" apply false
    id("com.android.library") version "7.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.5.31" apply false
}
```

Or in root `build.gradle`
Update this line:
```
        classpath 'com.android.tools.build:gradle:7.1.2'
```

### Setting gradle version

`gradle-wrapper.properties` file, name of field is `distributionUrl`

### compatibility

Between Gradle and AGP version:
https://developer.android.com/studio/releases/gradle-plugin?buildsystem=ndk-build#updating-gradle

Overall compatibility
https://developer.android.com/studio/releases/gradle-plugin?buildsystem=ndk-build#compatibility_2


### AGP 7.0 and above needs JDK 11

https://www.youtube.com/watch?v=AZBW5StgF8o

When using Android Gradle plugin 7.0 to build your app, JDK 11 is now required to run Gradle. Android Studio Arctic Fox bundles JDK 11 and configures Gradle to use it by default, which means that most Android Studio users do not need to make any configuration changes to their projects

## Learning about gradle tricks

https://github.com/android/gradle-recipes

## task list

https://cs.android.com/android-studio/platform/tools/base/+/mirror-goog-studio-main:build-system/gradle-core/src/main/java/com/android/build/gradle/internal/tasks/

https://cs.android.com/android-studio/platform/tools/base/+/mirror-goog-studio-main:build-system/gradle-core/src/main/java/com/android/build/gradle/tasks/

https://cs.android.com/android-studio/platform/tools/base/+/mirror-goog-studio-main:build-system/gradle-core/src/main/java/com/android/build/gradle/internal/TaskManager.kt;l=1689?q=connectedAndroidTest&ss=android-studio%2Fplatform%2Ftools%2Fbase:build-system%2Fgradle-core%2Fsrc%2Fmain%2Fjava%2Fcom%2Fandroid%2Fbuild%2F

## How it works

https://developer.android.com/studio/build/extend-agp

## Starting from bumblebee

old way was classpath+apply

This is deprecateD:
```
buildscript {
  dependencies {
    classpath <plugin>
  }
}
```

you'd instead have
```
plugins {
  id <plugin> apply false
}
```
Buildscript block is no longer needed:
https://stefma.medium.com/its-time-to-ditch-the-buildscript-block-a1ab12e0d9ce


`Plugins block`:
```
plugins {
  id("org.gradle.java")
  kotlin("jvm") version "1.2.40"
}
```
By default the plugin block can only resolve plugins which are either:

Core Plugins (provides by Gradle themself). These are e.g.: `org.gradle.java`, `org.gradle.groovy`, `org.gradle.java-gradle-plugin` and so on…
Published to the Gradle Plugin Portal.
Unfortunately not all Gradle Plugins are available at the Plugin Portal (looking at you, Android Gradle Plugin! 😡).

