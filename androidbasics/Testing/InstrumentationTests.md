
## What are Instrumentation tests?

These tests run on actual devices, not in jdk or laptop. although they will run slowly.

## How they work?

The Android Plug-in for Gradle builds a test app based on your test code, then loads the test app on the same device as the target app.

## Testing options for UI testing

1. Espresso - https://developer.android.com/training/testing/espresso
2. Jetpack compose - https://developer.android.com/jetpack/compose/testing
3. UI Automater - https://developer.android.com/training/testing/ui-automator
4. Roboelectric - http://robolectric.org/

## Dependencies to run instrumentation tests

dependency configuration name starts with `androidTestImplementation`
We use androidx test runner

In `build.gradle`
```groovy
dependencies {
    androidTestImplementation "androidx.test:core:$androidXTestVersion0"
    androidTestImplementation "androidx.test:runner:$androidXTestVersion"
    androidTestImplementation "androidx.test:rules:$androidXTestVersion"
    
    // Brings in AndroidJUnit4
    androidTestImplementation "androidx.test.ext:junit:$testJunitVersion"

    // Optional -- UI testing with Espresso
    androidTestImplementation "androidx.test.espresso:espresso-core:$espressoVersion"
    // Optional -- UI testing with UI Automator
    androidTestImplementation "androidx.test.uiautomator:uiautomator:$uiAutomatorVersion"
    // Optional -- UI testing with Compose
    androidTestImplementation "androidx.compose.ui:ui-test-junit4:$compose_version"
}
```

### Specifying the test instrumentation runner in build config

In `module/build.gradle`
```groovy
android {
    defaultConfig {
        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }
}
```


## Where are instrumentation tests written?

IN `src/androidTest/java/pkgname/ExampleInstrumentationTest.java/kt`,
in general use `modulename/src/androidTest/java/`

![instrumentationcode](images/instrumentedtests.PNG)


## AndroidJUnit4 vs AndroidJUnitRunner

`AndroidJUnit4` comes from `import androidx.test.ext.junit.runners.AndroidJUnit4`
where as `AndroidJUnitRunner` comes from `import androidx.test.runner.AndroidJUnitRunner`

`AndroidJUnitRunner` - This is essentially the entry point into running your entire suite of tests. It controls the test environment, the test apk, and launches all of the tests defined in your test package. This is specified in `build.gradle` as the `testInstrumentationRunner`

`AndroidJUnit4` - `AndroidJUnit4` is the class test runner. This is the thing that will drive the tests for a single class. This is specified in `@RunWith` in the instrumented test cases.

## Where is the instrumentation test runner coming from?

```kt
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith
```

Know more about AndroidJUnitRunner: The `AndroidJUnitRunner` class is a JUnit test runner that lets you run instrumented JUnit 4 tests on Android devices, including those using the Espresso, UI Automator, and Compose testing frameworks. **This test runner is not needed for local tests, only instrumentation tests**

https://developer.android.com/training/testing/instrumented-tests/androidx-test-libraries/runner#enable-gradle

## Creating and running tests

To create an instrumented JUnit 4 test class, specify `AndroidJUnit4` as your default test runner via `@RunWith(AndroidJUnit4.class)`

Mark tests as usual with `@Test`.

specify `ActivityScenarioRule` to get an activity launched for test:
```kt
    @get:Rule()
    val activity = ActivityScenarioRule(MainActivity::class.java)
```

## Reducing flakiness

https://developer.android.com/training/testing/espresso/idling-resource
