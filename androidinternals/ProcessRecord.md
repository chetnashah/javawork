
Full information about process that is currently running


## Important memebers

```java
class ProcessRecord implements WindowProcessListener {
    static final String TAG = TAG_WITH_CLASS_NAME ? "ProcessRecord" : TAG_AM;

    final ActivityManagerService mService; // where we came from
    private final ActivityManagerGlobalLock mProcLock;

    volatile ApplicationInfo info; // all about the first app in the process
    final ProcessInfo processInfo; // if non-null, process-specific manifest info
    final boolean isolated;     // true if this is a special isolated process
    public final boolean isSdkSandbox; // true if this is an SDK sandbox process
    final boolean appZygote;    // true if this is forked from the app zygote
    final int uid;              // uid of process; may be different from 'info' if isolated
    final int userId;           // user of process.
    final String processName;   // name of the process

        /**
     * Overall state of process's uid.
     */
    @CompositeRWLock({"mService", "mProcLock"})
    private UidRecord mUidRecord;

    /**
     * List of packages running in the process.
     */
    private final PackageList mPkgList = new PackageList(this);


    /**
     * The process of this application; 0 if none.
     */
    @CompositeRWLock({"mService", "mProcLock"})
    int mPid;

    /**
     * The actual proc...  may be null only if 'persistent' is true
     * (in which case we are in the process of launching the app).
     */
    @CompositeRWLock({"mService", "mProcLock"})
    private IApplicationThread mThread;
    /**
     * Process start is pending.
     */
    @GuardedBy("mService")
    private boolean mPendingStart;
    /**
     * Who is watching for the death.
     */
    @GuardedBy("mService")
    private IBinder.DeathRecipient mDeathRecipient;

    /**
     * Set to currently active instrumentation running in process.
     */
    @CompositeRWLock({"mService", "mProcLock"})
    private ActiveInstrumentation mInstr;

    /**
     * True once we know the process has been killed.
     */
    @CompositeRWLock({"mService", "mProcLock"})
    private boolean mKilled;
    /**
     * Whether this process should be killed and removed from process list.
     * It is set when the package is force-stopped or the process has crashed too many times.
     */
    private volatile boolean mRemoved;
    /**
     * All about the process state info (proc state, oom adj score) in this process.
     */
    final ProcessStateRecord mState;

    /**
     * All about the services in this process.
     */
    final ProcessServiceRecord mServices;

    /**
     * All about the providers in this process.
     */
    final ProcessProviderRecord mProviders;

    /**
     * All about the receivers in this process.
     */
    final ProcessReceiverRecord mReceivers;


```