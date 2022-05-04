

## ActivityScenarioRule

This rule provides functional testing of a single activity.
**This rule launches chosen activity before each test annotated with `@Test`**

* To access the given activity in your test logic, provide a callback runnable to `ActivityScenarioRule.getScenario().onActivity()`.

A kotlin sample of usage:
```kt
@RunWith(AndroidJUnit4::class.java)
@LargeTest
class MyClassTest {
  @get:Rule
  val activityRule = ActivityScenarioRule(MyClass::class.java) // The activity to launch

  @Test fun myClassMethod_ReturnsTrue() {
    activityRule.scenario.onActivity { … } // Optionally, access the activity.
   }
}
```

## ServiceTestRule

This rule provides a simplified mechanism to launch your service before the tests and shut it down before and after. `You can start or bind the service with one of the helper methods`.


It automatically stops or unbinds after the test completes and any methods annotated with @After have finished.


**NOte: This rule doesn’t support IntentService. This is because the service is destroyed when IntentService.onHandleIntent(Intent) finishes all outstanding commands, so there is no guarantee to establish a successful connection in a timely manner**

```java
@RunWith(AndroidJUnit4.class)
public class LocalServiceTest {
    @Rule
    public final ServiceTestRule mServiceRule = new ServiceTestRule();

    @Test
    public void testWithBoundService() throws TimeoutException {
        // Create the service Intent.
        Intent serviceIntent =
                new Intent(getApplicationContext(), LocalService.class);

        // Data can be passed to the service via the Intent.
        serviceIntent.putExtra(LocalService.SEED_KEY, 42L);

        // Bind the service and grab a reference to the binder.
        IBinder binder = mServiceRule.bindService(serviceIntent);

        // Get the reference to the service, or you can call public methods on the binder directly.
        LocalService service = ((LocalService.LocalBinder) binder).getService();

        // Verify that the service is working correctly.
        assertThat(service.getRandomInt(), is(any(Integer.class)));
    }
}
```

LocalService code:
```java
public class LocalService extends Service {
    // Used as a key for the Intent.
    public static final String SEED_KEY = "SEED_KEY";

    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();

    // Random number generator
    private Random mGenerator = new Random();

    private long mSeed;

    @Override
    public IBinder onBind(Intent intent) {
        // If the Intent comes with a seed for the number generator, apply it.
        if (intent.hasExtra(SEED_KEY)) {
            mSeed = intent.getLongExtra(SEED_KEY, 0);
            mGenerator.setSeed(mSeed);
        }
        return mBinder;
    }

    public class LocalBinder extends Binder {

        public LocalService getService() {
            // Return this instance of LocalService so clients can call public methods.
            return LocalService.this;
        }
    }

    /**
     * Returns a random integer in [0, 100).
     */
    public int getRandomInt() {
        return mGenerator.nextInt(100);
    }
}
```