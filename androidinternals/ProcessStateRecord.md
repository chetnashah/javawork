The state info of the process, including proc state, oom adj score, et al

## Imp members 

```java
final class ProcessStateRecord {
    private final ProcessRecord mApp;
    private final ActivityManagerService mService;
    private final ActivityManagerGlobalLock mProcLock;
    /**
     * Current OOM adjustment for this process.
     */
    @CompositeRWLock({"mService", "mProcLock"})
    private int mCurAdj = ProcessList.INVALID_ADJ;

    /**
     * Current capability flags of this process.
     * For example, PROCESS_CAPABILITY_FOREGROUND_LOCATION is one capability.
     */
    @CompositeRWLock({"mService", "mProcLock"})
    private int mCurCapability = PROCESS_CAPABILITY_NONE;
    /**
     * Currently computed process state.
     */
    @CompositeRWLock({"mService", "mProcLock"})
    private int mCurProcState = PROCESS_STATE_NONEXISTENT;
    /**
     * Process currently is on the service B list.
     */
    @CompositeRWLock({"mService", "mProcLock"})
    private boolean mServiceB;
    /**
     * Has this process not been in a cached state since last idle?
     */
    @GuardedBy("mProcLock")
    private boolean mNotCachedSinceIdle;
    /**
     * Running any activities that are foreground?
     */
    @CompositeRWLock({"mService", "mProcLock"})
    private boolean mHasForegroundActivities;

    /**
     * Has UI been shown in this process since it was started?
     */
    @GuardedBy("mService")
    private boolean mHasShownUi;
    /**
     * Is this process currently showing a non-activity UI that the user
     * is interacting with? E.g. The status bar when it is expanded, but
     * not when it is minimized. When true the
     * process will be set to use the ProcessList#SCHED_GROUP_TOP_APP
     * scheduling group to boost performance.
     */
    @GuardedBy("mService")
    private boolean mHasTopUi;

    /**
     * Is the process currently showing a non-activity UI that
     * overlays on-top of activity UIs on screen. E.g. display a window
     * of type android.view.WindowManager.LayoutParams#TYPE_APPLICATION_OVERLAY
     * When true the process will oom adj score will be set to
     * ProcessList#PERCEPTIBLE_APP_ADJ at minimum to reduce the chance
     * of the process getting killed.
     */
    @GuardedBy("mService")
    private boolean mHasOverlayUi;
    /**
     * Keep track of whether we changed 'mSetAdj'.
     */
    @CompositeRWLock({"mService", "mProcLock"})
    private boolean mProcStateChanged;
    /**
     * Is this an empty background process?
     */
    @GuardedBy("mService")
    private boolean mEmpty;

    /**
     * Is this a cached process?
     */
    @GuardedBy("mService")
    private boolean mCached;
    /**
     * Whether or not the app is background restricted (OP_RUN_ANY_IN_BACKGROUND is NOT allowed).
     */
    @GuardedBy("mService")
    private boolean mBackgroundRestricted = false;
    /**
     * Whether or not this process is reachable from given process.
     */
    @GuardedBy("mService")
    private boolean mReachable;

```