


### definition

```java
public class ActivityManagerService extends IActivityManager.Stub
        implements Watchdog.Monitor, BatteryStatsImpl.BatteryCallback, ActivityManagerGlobalLock {
}
```

### important inner classes:

```java
    final class UiHandler extends Handler {}
    final class MainHandler extends Handler {}
    public final class LocalService extends ActivityManagerInternal
            implements ActivityManagerLocal {}
```

### Locking abbrevations

* `LOSP`:    Locked with any Of global am Service or Process lock
* `LSP`:     Locked with both of global am Service and Process lock
* `Locked`:  Locked with global am service lock alone
* `LPr`:     Locked with Process lock alone

### Important members/state

Service stuff is delegated to `ActiveServices mServices`, and process stuff is delegated to `ProcessList mProcessList`.

```java
    final ActiveServices mServices;
    /**
     * Process management.
     */
    final ProcessList mProcessList;

    /** All system services */
    SystemServiceManager mSystemServiceManager;
    /**
     * The global lock for AMS, it's de-facto the ActivityManagerService object as of now.
     */
    final ActivityManagerGlobalLock mGlobalLock = ActivityManagerService.this;
    final ActivityManagerGlobalLock mProcLock = ENABLE_PROC_LOCK
            ? new ActivityManagerProcLock() : mGlobalLock;

    // Convenient for easy iteration over the queues. Foreground is first
    // so that dispatch of foreground broadcasts gets precedence.
    final BroadcastQueue[] mBroadcastQueues = new BroadcastQueue[3];
    /**
     * Process management.
     */
    final ProcessList mProcessList;
    /**
     * All of the processes we currently have running organized by pid.
     * The keys are the pid running the application.
     *
     * <p>NOTE: This object is protected by its own lock, NOT the global activity manager lock!
     */
    final PidMap mPidsSelfLocked = new PidMap();
    /**
     * Keeps track of all IIntentReceivers that have been registered for broadcasts.
     * Hash keys are the receiver IBinder, hash value is a ReceiverList.
     */
    @GuardedBy("this")
    final HashMap<IBinder, ReceiverList> mRegisteredReceivers = new HashMap<>();
    /*
     * When service association tracking is enabled, this is all of the associations we
     * have seen.  Mapping is target uid -> target component -> source uid -> source process name
     * -> association data.
     */
    @GuardedBy("this")
    final SparseArray<ArrayMap<ComponentName, SparseArray<ArrayMap<String, Association>>>>
            mAssociations = new SparseArray<>();
    /**
     * List of initialization arguments to pass to all processes when binding applications to them.
     * For example, references to the commonly used services.
     */
    ArrayMap<String, IBinder> mAppBindArgs;
    ArrayMap<String, IBinder> mIsolatedAppBindArgs;
    final Context mContext;


    /**
     * For reporting to battery stats the apps currently running foreground
     * service.  The ProcessMap is package/uid tuples; each of these contain
     * an array of the currently foreground processes.
     */
    @GuardedBy("this")
    final ProcessMap<ArrayList<ProcessRecord>> mForegroundPackages
            = new ProcessMap<ArrayList<ProcessRecord>>();
    public WindowManagerService mWindowManager;
    WindowManagerInternal mWmInternal;
    public ActivityTaskManagerService mActivityTaskManager;
    public ActivityTaskManagerInternal mAtmInternal;
    public final ActivityManagerInternal mInternal;
    final ActivityThread mSystemThread;
    public final ServiceThread mHandlerThread;
    final MainHandler mHandler;
    final Handler mUiHandler;
    final ServiceThread mProcStartHandlerThread;
    final Handler mProcStartHandler;
    PackageManagerInternal mPackageManagerInt;


    // ------------------ broadcasts stuff -------------------------------
    // Use an offload queue for long broadcasts, e.g. BOOT_COMPLETED.
    // For simplicity, since we statically declare the size of the array of BroadcastQueues,
    // we still create this new offload queue, but never ever put anything on it.
    final boolean mEnableOffloadQueue;

    final BroadcastQueue mFgBroadcastQueue;
    final BroadcastQueue mBgBroadcastQueue;
    final BroadcastQueue mOffloadBroadcastQueue;
    // Convenient for easy iteration over the queues. Foreground is first
    // so that dispatch of foreground broadcasts gets precedence.
    final BroadcastQueue[] mBroadcastQueues = new BroadcastQueue[3];
```


### Important Methods:
```java
    final int broadcastIntentLocked(ProcessRecord callerApp, String callerPackage,
            @Nullable String callerFeatureId, Intent intent, String resolvedType,
            IIntentReceiver resultTo, int resultCode, String resultData,
            Bundle resultExtras, String[] requiredPermissions,
            String[] excludedPermissions, int appOp, Bundle bOptions,
            boolean ordered, boolean sticky, int callingPid, int callingUid,
            int realCallingUid, int realCallingPid, int userId,
            boolean allowBackgroundActivityStarts,
            @Nullable IBinder backgroundActivityStartsToken,
            @Nullable int[] broadcastAllowList) {
            
            }
```