
## What is needed to run kotlin with android


### root build.gradle should add kotlin gradle plugin

```
buildscript {
    ext.kotlin_version = '1.4.10'
    ...
    dependencies {
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
    }
}
```

### Module level need of kotlin-android plugin & kotlin stdlib & extensions

Module level `build.gradle`:

```
plugins {
    ...
    id 'kotlin-android' // note if using non-android apps, org.jetbrains.kotlin.jvm is preferred
}

...

dependencies {
    implementation 'androidx.core:core-ktx:1.3.2'
    implementation "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
}
```

### Update sourceSEts to include `src/main/kotlin`

```
android {
    sourceSets {
        main.java.srcDirs += 'src/main/kotlin'
    }
}
```