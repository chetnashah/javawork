A unit test verifies the behavior of a small section of code, the unit under test. It does so by executing that code and checking the result.

## Dependencies needed

the dependency configuration used is `testImplementation` for unit tests

Main dependencies needed:
```groovy
dependencies {
  // Required -- JUnit 4 framework
  testImplementation "junit:junit:$jUnitVersion"
  // Optional -- Robolectric environment
  testImplementation "androidx.test:core:$androidXTestVersion"
  // Optional -- Mockito framework
  testImplementation "org.mockito:mockito-core:$mockitoVersion"
  // Optional -- mockito-kotlin
  testImplementation "org.mockito.kotlin:mockito-kotlin:$mockitoKotlinVersion"
  // Optional -- Mockk framework
  testImplementation "io.mockk:mockk:$mockkVersion"
}
```

## tests location

`src/test/java/`

## Unit "Android" Test

An **Unit Android Test** is a test that needs an Android device or emulator but it's different from a UI test because it doesn't start any activities.

In this sample the test can't run without the Android Framework because the `Parcel` class is used in one of the methods of the Parcelable interface and the way data is written into a Parcel and read from it is not trivial enough to be stubbed. i.e Instrumentation tests can be used to test none UI logic as well. They are especially useful when you need to test code that has a dependency on a context.



Note that the unit test is placed in `/androidTest/` instead of `/test/`.

Add a Specific instrumentation runner: androidx.test.runner.AndroidJUnitRunner

https://github.com/android/testing-samples/tree/main/unit/BasicUnitAndroidTest

`./gradlew connectedCheck` to run a unit androoid test.

Another way is to interact with am in adb shell
```
adb shell am instrument -w <test_package_name>/<runner_class>
```

**an instrumentation test** provides a special test execution environment as launched via the am instrument command, where the targeted application process is restarted and initialized with basic application context, and an instrumentation thread is started inside the application process VM. 

Your test code starts execution on this instrumentation thread and is provided with an Instrumentation instance that provides access to the application context and APIs to manipulate the application process under test.

## Quck way to generate tests in intellij/android studio

![gen tests](images/quickgentests.png)

## Using more readable test names with kotlin

In kotlin you can do this:
```kotlin
  @Test
  fun `empty username returns false`(){
      
  }
```

## @Before and @After

`@Before` for setup and `@After` for teardown.

## Test runner and its configuration

No test runner configuration is needed by default, 

if you want to change a test runner you can specify it in `@RunWith(SOmeRunner.class)`.
Test runner in case of Mockito is `MockitoJUnitRunner`,
For e.g. you want to use RoboElectric framework, you would use `@RunWith(RobolectricTestRunner.class)`.

An example :
```java
@RunWith(RobolectricTestRunner.class)
public class MyActivityTest {

  @Test
  public void clickingButton_shouldChangeMessage() {
    MyActivity activity = Robolectric.setupActivity(MyActivity.class);
    activity.button.performClick();
    assertThat(activity.message.getText()).isEqualTo("Robolectric Rocks!");
  }
}
```

## Where does assert come from?

```kotlin
import org.junit.Assert.*
```

## Where does @Test come from?

```kotlin
import org.junit.Test
```

## Creating and running tests

## Mocking android framework

https://developer.android.com/training/testing/local-tests#mocking-dependencies

## InstantTaskExecutor rule

A JUnit Test Rule that swaps the background executor used by the Architecture Components with a different one which executes each task synchronously.

You can use this rule for your host side tests that use Architecture Components.

