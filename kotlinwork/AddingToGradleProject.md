

## Add kotlin plugin for jvm

```groovy
plugins {
    id 'org.jetbrains.kotlin.jvm' version '1.5.31'  // add this

    id 'application' 
}
```

## Add kotlin stdlib implementation dependency

```groovy
dependencies {
    //...
    implementation 'org.jetbrains.kotlin:kotlin-stdlib-jdk8' 
    //...
}
```

## Write code in source sets : `src/main/kotlin` and `src/tests/kotlin`

Kotlin plugin adds these source sets.

## optionally configure KotlinCompile task

```groovy
tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile) {
    kotlinOptions {
        jvmTarget = "11"
        javaParameters = true
        freeCompilerArgs = ["-Xjvm-default=all"]
    }
}
```