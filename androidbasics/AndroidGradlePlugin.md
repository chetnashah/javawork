
Android Studio 4.2 can open projects that use AGP 3.1 and higher provided that AGP is running Gradle 4.8.1 and higher

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



