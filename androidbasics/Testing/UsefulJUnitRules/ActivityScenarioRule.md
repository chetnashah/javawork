

##

This is a replacement of now **deprecated ActivityTestRule**.

`ActivityScenarioRule` launches a given activity before the test starts and closes after the test.


You can access the `androidx.test.core.app.ActivityScenari`o instance via `getScenario`. You may finish your activity manually in your test, it will not cause any problems and this rule does nothing after the test in such cases.

```java
  @Rule
  public ActivityScenarioRule<MyActivity> rule =
      new ActivityScenarioRule<>(MyActivity.class);

  @Test
  public void myTest() {
    ActivityScenario<MyActivity> scenario = rule.getScenario();
    // Your test code goes here.
  }
```