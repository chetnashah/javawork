An interesting property of Binder objects is that each instance maintains a unique identity across all processes in the system, no matter how many process boundaries it crosses or where it goes. This facility is provided by the Binder kernel driver, which analyzes the contents of each Binder transaction and assigns a unique 32-bit integer value to each Binder object it sees.

The key IBinder API is `transact()` matched by `Binder.onTransact()`. 
These methods allow you to send a call to an `IBinder` object and receive a call coming in to a `Binder` object, respectively. 

This transaction API is synchronous, such that a call to `transact()` does not return until the target has returned from `Binder.onTransact()`; this is the expected behavior when calling an object that exists in the local process, and the underlying inter-process communication (IPC) mechanism ensures that these same semantics apply when going across processes.

When working with remote objects, you often want to find out when they are no longer valid. There are three ways this can be determined:

1. The `transact()` method will throw a `RemoteException` exception if you try to call it on an `IBinder` whose process no longer exists.
2. The `pingBinder()` method can be called, and will return false if the remote process no longer exists.
3. The `linkToDeath()` method can be used to register a `DeathRecipient` with the `IBinder`, which will be called when its containing process goes away.

each Binder’s object reference is assigned either,

1. A virtual memory address pointing to a Binder object in the same process, or
2. A unique 32-bit handle (as assigned by the Binder kernel driver) pointing to the Binder’s virtual memory address in a different process.

The Binder’s unique object identity rules allow them to be used for a special purpose: as shared, security access tokens.

Binders are globally unique, which means if you create one, nobody else can create one that appears equal to it. 

For this reason, the application framework uses Binder tokens extensively in order to ensure secure interaction between cooperating processes: a client can create a Binder object to use as a token that can be shared with a server process, and the server can use it to validate the client’s requests without there being anyway for others to spoof it.

App example:
```java
/**
 * An example activity that acquires a wake lock in onCreate()
 * and releases it in onDestroy().
 */
public class MyActivity extends Activity {

  private PowerManager.WakeLock wakeLock;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
    wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "My Tag");
    wakeLock.acquire();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    wakeLock.release();
  }
}
```


PowerManager.java
```java
public final class PowerManager {

  // Our handle on the global power manager service.
  private final IPowerManager mService;

  public WakeLock newWakeLock(int levelAndFlags, String tag) {
    return new WakeLock(levelAndFlags, tag);
  }

  public final class WakeLock {
    private final IBinder mToken;
    private final int mFlags;
    private final String mTag;

    WakeLock(int flags, String tag) {
      // Create a token that uniquely identifies this wake lock.
      mToken = new Binder();
      mFlags = flags;
      mTag = tag;
    }

    public void acquire() {
      // Send the power manager service a request to acquire a wake
      // lock for the application. Include the token as part of the
      // request so that the power manager service can validate the
      // application's identity when it requests to release the wake
      // lock later on.
      mService.acquireWakeLock(mToken, mFlags, mTag);
    }

    public void release() {
      // Send the power manager service a request to release the
      // wake lock associated with 'mToken'.
      mService.releaseWakeLock(mToken);
    }
  }
}
```

 The PowerManager sends the WakeLock’s unique Binder `token` as part of the `acquire()` request. When the PowerManagerService receives the request, it holds onto the token for safe-keeping and forces the device to remain awake


### WindowToken

a window token is a special type of Binder token that the window manager uses to uniquely identify a window in the system. Window tokens are important for security because they make it impossible for malicious applications to draw on top of the windows of other applications.

`BadTokenException`: The window manager protects against this by requiring applications to pass their application’s window token as part of each request to add or remove a window.3 If the tokens don’t match, the window manager rejects the request and throws a BadTokenException.

### AppWindowToken

When an application starts up for the first time, the ActivityManagerService4 creates a special kind of window token called an application window token, which uniquely identifies the application’s top-level container window.

The activity manager gives this token to both the application and the window manager, and the application sends the token to the window manager each time it wants to add a new window to the screen.

makes it easy for the activity manager to make direct requests to the window manager. For example, the activity manager can say, “hide all of this token’s windows”, and the window manager will be able to correctly identify the set of windows which should be closed.

Applications which manually add new windows to the screen (i.e. using the addView(View, WindowManager.LayoutParams) method) may need to specify their application’s window token by setting the `WindowManager.LayoutParams.token` field.

It is very unlikely that any normal application would ever have to do this, since the `getWindowManager()` method returns a `WindowManager` which will automatically set the token’s value for you. 

That said, if at some point in the future you encounter a situation in which you need to add a panel window to the screen from a background service, know that you would need to manually sign the request with your application window token in order to achieve it. :P

