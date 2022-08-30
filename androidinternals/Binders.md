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


### Token semantics

The data sent through `transact()` is a `Parcel`, a generic buffer of data that also maintains some meta-data about its contents. The meta data is used to manage `IBinder` object references in the buffer, so that those references can be maintained as the buffer moves across processes. T

his mechanism ensures that when an IBinder is written into a Parcel and sent to another process, if that other process sends a reference to that same `IBinder` back to the original process, then the original process will receive the same `IBinder` object back. **These semantics allow IBinder/Binder objects to be used as a unique identity (to serve as a token or for other purposes) that can be managed across processes.**

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


### Transaction threads semantics

The system maintains a pool of transaction threads in each process that it runs in. These threads are used to dispatch all IPCs coming in from other processes. For example, when an IPC is made from process A to process B, the calling thread in A blocks in `transact() `as it sends the transaction to process B. The next available pool thread in B receives the incoming transaction, calls `Binder.onTransact()` on the target object, and replies with the result Parcel. Upon receiving its result, the thread in process A returns to allow its execution to continue. In effect, other processes appear to use as additional threads that you did not create executing in your own process.

The Binder system also supports recursion across processes. For example if process A performs a transaction to process B, and process B while handling that transaction calls `transact()` on an IBinder that is implemented in A, then the thread in A that is currently waiting for the original transaction to finish will take care of calling Binder.onTransact() on the object being called by B. This ensures that the recursion semantics when calling remote binder object are the same as when calling local objects.

### IBinder definition

```java
public interface IBinder {
  /**
  * Get the canonical name of the interface supported by this binder.
  */
  public @Nullable String getInterfaceDescriptor() throws RemoteException;
    /**
     * Check to see if the object still exists.
     *
     * @return Returns false if the
     * hosting process is gone, otherwise the result (always by default
     * true) returned by the pingBinder() implementation on the other
     * side.
     */
    public boolean pingBinder();

    /**
     * Check to see if the process that the binder is in is still alive.
     *
     * @return false if the process is not alive.  Note that if it returns
     * true, the process may have died while the call is returning.
     */
    public boolean isBinderAlive();

    /**
     * Attempt to retrieve a local implementation of an interface
     * for this Binder object.  If null is returned, you will need
     * to instantiate a proxy class to marshall calls through
     * the transact() method.
     */
    public @Nullable IInterface queryLocalInterface(@NonNull String descriptor);
    /**
     * Perform a generic operation with the object.
     *
     * @param code The action to perform.  This should
     * be a number between {@link #FIRST_CALL_TRANSACTION} and
     * {@link #LAST_CALL_TRANSACTION}.
     * @param data Marshalled data to send to the target.  Must not be null.
     * If you are not sending any data, you must create an empty Parcel
     * that is given here.
     * @param reply Marshalled data to be received from the target.  May be
     * null if you are not interested in the return value.
     * @param flags Additional operation flags.  Either 0 for a normal
     * RPC, or {@link #FLAG_ONEWAY} for a one-way RPC.
     *
     * @return Returns the result from {@link Binder#onTransact}.  A successful call
     * generally returns true; false generally means the transaction code was not
     * understood.
     */
    public boolean transact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags)
        throws RemoteException;


    /**
     * Register the recipient for a notification if this binder
     * goes away.  If this binder object unexpectedly goes away
     * (typically because its hosting process has been killed),
     * then the given {@link DeathRecipient}'s
     * {@link DeathRecipient#binderDied DeathRecipient.binderDied()} method
     * will be called.
     *
     * <p>You will only receive death notifications for remote binders,
     * as local binders by definition can't die without you dying as well.
     *
     * @throws RemoteException if the target IBinder's
     * process has already died.
     *
     * @see #unlinkToDeath
     */
    public void linkToDeath(@NonNull DeathRecipient recipient, int flags)
            throws RemoteException;
    /**
     * Remove a previously registered death notification.
     * The recipient will no longer be called if this object
     * dies.
     *
     * @return {@code true} if the <var>recipient</var> is successfully
     * unlinked, assuring you that its
     * {@link DeathRecipient#binderDied DeathRecipient.binderDied()} method
     * will not be called;  {@code false} if the target IBinder has already
     * died, meaning the method has been (or soon will be) called.
     *
     * @throws java.util.NoSuchElementException if the given
     * <var>recipient</var> has not been registered with the IBinder, and
     * the IBinder is still alive.  Note that if the <var>recipient</var>
     * was never registered, but the IBinder has already died, then this
     * exception will <em>not</em> be thrown, and you will receive a false
     * return value instead.
     */
    public boolean unlinkToDeath(@NonNull DeathRecipient recipient, int flags

```

### Binder

Implementation of IBinder that provides standard local implementation of such an object.

Most developers will not implement this class directly, instead using the aidl tool to describe the desired interface, having it generate the appropriate Binder subclass.

You can, however, derive directly from Binder to implement your own custom RPC protocol or simply instantiate a raw Binder object directly to use as a token that can be shared across processes.



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

