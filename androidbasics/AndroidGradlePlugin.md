
Android Studio 4.2 can open projects that use AGP 3.1 and higher provided that AGP is running Gradle 4.8.1 and higher

### where to find googles published lib on maven

https://maven.google.com/web/index.html?q=room#androidx.room:room-compiler

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

