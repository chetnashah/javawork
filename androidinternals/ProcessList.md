

### Definition

```java

/**
 * Activity manager code dealing with processes.
 */
public final class ProcessList {
}
```

Constructor:
```java
    ProcessList() {
        MemInfoReader minfo = new MemInfoReader();
        minfo.readMemInfo();
        mTotalMemMb = minfo.getTotalSize()/(1024*1024);
        updateOomLevels(0, 0, false);
    }
```

### dump info

```
dumpsys activity processes
```

### Important members


```java
    /**
     * Contains {@link ProcessRecord} objects for pending process starts.
     *
     * Mapping: {@link #mProcStartSeqCounter} -> {@link ProcessRecord}
     */
    @GuardedBy("mService")
    final LongSparseArray<ProcessRecord> mPendingStarts = new LongSparseArray<>();

    /**
     * List of running applications, sorted by recent usage.
     * The first entry in the list is the least recently used.
     */
    @CompositeRWLock({"mService", "mProcLock"})
    private final ArrayList<ProcessRecord> mLruProcesses = new ArrayList<ProcessRecord>();
    /**
     * Where in mLruProcesses that the processes hosting activities start.
     */
    @CompositeRWLock({"mService", "mProcLock"})
    private int mLruProcessActivityStart = 0;

    /**
     * Where in mLruProcesses that the processes hosting services start.
     * This is after (lower index) than mLruProcessesActivityStart.
     */
    @CompositeRWLock({"mService", "mProcLock"})
    private int mLruProcessServiceStart = 0;
    /**
     * The currently running isolated processes.
     */
    @GuardedBy("mService")
    final SparseArray<ProcessRecord> mIsolatedProcesses = new SparseArray<>();

    /**
     * The currently running application zygotes.
     */
    @GuardedBy("mService")
    final ProcessMap<AppZygote> mAppZygotes = new ProcessMap<AppZygote>();

    /**
     * Processes that are being forcibly torn down.
     */
    @GuardedBy("mService")
    final ArrayList<ProcessRecord> mRemovedProcesses = new ArrayList<ProcessRecord>();

    /**
     * Processes that are killed by us and being waiting for the death notification.
     */
    @GuardedBy("mService")
    final ProcessMap<ProcessRecord> mDyingProcesses = new ProcessMap<>();

    // Self locked with the inner lock within the RemoteCallbackList
    private final RemoteCallbackList<IProcessObserver> mProcessObservers =
            new RemoteCallbackList<>();
                // No lock is needed as it's accessed from single thread only
    private ProcessChangeItem[] mActiveProcessChanges = new ProcessChangeItem[5];

    @GuardedBy("mProcessChangeLock")
    private final ArrayList<ProcessChangeItem> mPendingProcessChanges = new ArrayList<>();

    @GuardedBy("mProcessChangeLock")
    final ArrayList<ProcessChangeItem> mAvailProcessChanges = new ArrayList<>();
    /**
     * All of the applications we currently have running organized by name.
     * The keys are strings of the application package name (as
     * returned by the package manager), and the keys are ApplicationRecord
     * objects.
     */
    @CompositeRWLock({"mService", "mProcLock"})
    private final MyProcessMap mProcessNames = new MyProcessMap();

    final class MyProcessMap extends ProcessMap<ProcessRecord> {
        @Override
        public ProcessRecord put(String name, int uid, ProcessRecord value) {
            final ProcessRecord r = super.put(name, uid, value);
            mService.mAtmInternal.onProcessAdded(r.getWindowProcessController());
            return r;
        }

        @Override
        public ProcessRecord remove(String name, int uid) {
            final ProcessRecord r = super.remove(name, uid);
            mService.mAtmInternal.onProcessRemoved(name, uid);
            return r;
        }
    }
```


### Important methods

```java
    ProcessRecord startProcessLocked(String processName, ApplicationInfo info,
            boolean knownToBeDead, int intentFlags, HostingRecord hostingRecord,
            int zygotePolicyFlags, boolean allowWhileBooting, boolean isolated, int isolatedUid,
            String abiOverride, String entryPoint, String[] entryPointArgs, Runnable crashHandler) {
    }
```

```java
    @GuardedBy("mService")
    boolean handleProcessStartedLocked(ProcessRecord app, int pid, boolean usingWrapper,
            long expectedStartSeq, boolean procAttached) {
    }
```