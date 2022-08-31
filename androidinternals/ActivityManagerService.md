


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

## attachApplicationLocked method

```java
    @GuardedBy("this")
    private boolean attachApplicationLocked(@NonNull IApplicationThread thread,
            int pid, int callingUid, long startSeq) {

        // Find the application record that is being attached...  either via
        // the pid if we are running in multiple processes, or just pull the
        // next app record if we are emulating process with anonymous threads.
        ProcessRecord app;
        long startTime = SystemClock.uptimeMillis();
        long bindApplicationTimeMillis;
        if (pid != MY_PID && pid >= 0) {
            synchronized (mPidsSelfLocked) {
                app = mPidsSelfLocked.get(pid);
            }
            if (app != null && (app.getStartUid() != callingUid || app.getStartSeq() != startSeq)) {
                String processName = null;
                final ProcessRecord pending = mProcessList.mPendingStarts.get(startSeq);
                if (pending != null) {
                    processName = pending.processName;
                }
                final String msg = "attachApplicationLocked process:" + processName
                        + " startSeq:" + startSeq
                        + " pid:" + pid
                        + " belongs to another existing app:" + app.processName
                        + " startSeq:" + app.getStartSeq();
                Slog.wtf(TAG, msg);
                // SafetyNet logging for b/131105245.
                EventLog.writeEvent(0x534e4554, "131105245", app.getStartUid(), msg);
                // If there is already an app occupying that pid that hasn't been cleaned up
                cleanUpApplicationRecordLocked(app, pid, false, false, -1,
                        true /*replacingPid*/, false /* fromBinderDied */);
                removePidLocked(pid, app);
                app = null;
            }
        } else {
            app = null;
        }

        // It's possible that process called attachApplication before we got a chance to
        // update the internal state.
        if (app == null && startSeq > 0) {
            final ProcessRecord pending = mProcessList.mPendingStarts.get(startSeq);
            if (pending != null && pending.getStartUid() == callingUid
                    && pending.getStartSeq() == startSeq
                    && mProcessList.handleProcessStartedLocked(pending, pid,
                        pending.isUsingWrapper(), startSeq, true)) {
                app = pending;
            }
        }

        if (app == null) {
            Slog.w(TAG, "No pending application record for pid " + pid
                    + " (IApplicationThread " + thread + "); dropping process");
            EventLogTags.writeAmDropProcess(pid);
            if (pid > 0 && pid != MY_PID) {
                killProcessQuiet(pid);
                //TODO: killProcessGroup(app.info.uid, pid);
                // We can't log the app kill info for this process since we don't
                // know who it is, so just skip the logging.
            } else {
                try {
                    thread.scheduleExit();
                } catch (Exception e) {
                    // Ignore exceptions.
                }
            }
            return false;
        }

        // If this application record is still attached to a previous
        // process, clean it up now.
        if (app.getThread() != null) {
            handleAppDiedLocked(app, pid, true, true, false /* fromBinderDied */);
        }

        // Tell the process all about itself.

        if (DEBUG_ALL) Slog.v(
                TAG, "Binding process pid " + pid + " to record " + app);

        final String processName = app.processName;
        try {
            AppDeathRecipient adr = new AppDeathRecipient(
                    app, pid, thread);
            thread.asBinder().linkToDeath(adr, 0);
            app.setDeathRecipient(adr);
        } catch (RemoteException e) {
            app.resetPackageList(mProcessStats);
            mProcessList.startProcessLocked(app,
                    new HostingRecord(HostingRecord.HOSTING_TYPE_LINK_FAIL, processName),
                    ZYGOTE_POLICY_FLAG_EMPTY);
            return false;
        }

        EventLogTags.writeAmProcBound(app.userId, pid, app.processName);

        synchronized (mProcLock) {
            app.mState.setCurAdj(ProcessList.INVALID_ADJ);
            app.mState.setSetAdj(ProcessList.INVALID_ADJ);
            app.mState.setVerifiedAdj(ProcessList.INVALID_ADJ);
            mOomAdjuster.setAttachingSchedGroupLSP(app);
            app.mState.setForcingToImportant(null);
            updateProcessForegroundLocked(app, false, 0, false);
            app.mState.setHasShownUi(false);
            app.mState.setCached(false);
            app.setDebugging(false);
            app.setKilledByAm(false);
            app.setKilled(false);
            // We carefully use the same state that PackageManager uses for
            // filtering, since we use this flag to decide if we need to install
            // providers when user is unlocked later
            app.setUnlocked(StorageManager.isUserKeyUnlocked(app.userId));
        }

        mHandler.removeMessages(PROC_START_TIMEOUT_MSG, app);

        boolean normalMode = mProcessesReady || isAllowedWhileBooting(app.info);
        List<ProviderInfo> providers = normalMode
                                            ? mCpHelper.generateApplicationProvidersLocked(app)
                                            : null;

        if (providers != null && mCpHelper.checkAppInLaunchingProvidersLocked(app)) {
            Message msg = mHandler.obtainMessage(CONTENT_PROVIDER_PUBLISH_TIMEOUT_MSG);
            msg.obj = app;
            mHandler.sendMessageDelayed(msg,
                    ContentResolver.CONTENT_PROVIDER_PUBLISH_TIMEOUT_MILLIS);
        }

        checkTime(startTime, "attachApplicationLocked: before bindApplication");

        if (!normalMode) {
            Slog.i(TAG, "Launching preboot mode app: " + app);
        }

        if (DEBUG_ALL) Slog.v(
            TAG, "New app record " + app
            + " thread=" + thread.asBinder() + " pid=" + pid);
        final BackupRecord backupTarget = mBackupTargets.get(app.userId);
        try {
            int testMode = ApplicationThreadConstants.DEBUG_OFF;
            if (mDebugApp != null && mDebugApp.equals(processName)) {
                testMode = mWaitForDebugger
                    ? ApplicationThreadConstants.DEBUG_WAIT
                    : ApplicationThreadConstants.DEBUG_ON;
                app.setDebugging(true);
                if (mDebugTransient) {
                    mDebugApp = mOrigDebugApp;
                    mWaitForDebugger = mOrigWaitForDebugger;
                }
            }

            boolean enableTrackAllocation = false;
            synchronized (mProcLock) {
                if (mTrackAllocationApp != null && mTrackAllocationApp.equals(processName)) {
                    enableTrackAllocation = true;
                    mTrackAllocationApp = null;
                }
            }

            // If the app is being launched for restore or full backup, set it up specially
            boolean isRestrictedBackupMode = false;
            if (backupTarget != null && backupTarget.appInfo.packageName.equals(processName)) {
                isRestrictedBackupMode = backupTarget.appInfo.uid >= FIRST_APPLICATION_UID
                        && ((backupTarget.backupMode == BackupRecord.RESTORE)
                                || (backupTarget.backupMode == BackupRecord.RESTORE_FULL)
                                || (backupTarget.backupMode == BackupRecord.BACKUP_FULL));
            }

            final ActiveInstrumentation instr = app.getActiveInstrumentation();

            if (instr != null) {
                notifyPackageUse(instr.mClass.getPackageName(),
                                 PackageManager.NOTIFY_PACKAGE_USE_INSTRUMENTATION);
            }
            ProtoLog.v(WM_DEBUG_CONFIGURATION, "Binding proc %s with config %s",
                    processName, app.getWindowProcessController().getConfiguration());
            ApplicationInfo appInfo = instr != null ? instr.mTargetInfo : app.info;
            app.setCompat(compatibilityInfoForPackage(appInfo));

            ProfilerInfo profilerInfo = mAppProfiler.setupProfilerInfoLocked(thread, app, instr);

            // We deprecated Build.SERIAL and it is not accessible to
            // Instant Apps and target APIs higher than O MR1. Since access to the serial
            // is now behind a permission we push down the value.
            final String buildSerial = (!appInfo.isInstantApp()
                    && appInfo.targetSdkVersion < Build.VERSION_CODES.P)
                            ? sTheRealBuildSerial : Build.UNKNOWN;

            // Figure out whether the app needs to run in autofill compat mode.
            AutofillOptions autofillOptions = null;
            if (UserHandle.getAppId(app.info.uid) >= Process.FIRST_APPLICATION_UID) {
                final AutofillManagerInternal afm = LocalServices.getService(
                        AutofillManagerInternal.class);
                if (afm != null) {
                    autofillOptions = afm.getAutofillOptions(
                            app.info.packageName, app.info.longVersionCode, app.userId);
                }
            }
            ContentCaptureOptions contentCaptureOptions = null;
            if (UserHandle.getAppId(app.info.uid) >= Process.FIRST_APPLICATION_UID) {
                final ContentCaptureManagerInternal ccm =
                        LocalServices.getService(ContentCaptureManagerInternal.class);
                if (ccm != null) {
                    contentCaptureOptions = ccm.getOptionsForPackage(app.userId,
                            app.info.packageName);
                }
            }
            SharedMemory serializedSystemFontMap = null;
            final FontManagerInternal fm = LocalServices.getService(FontManagerInternal.class);
            if (fm != null) {
                serializedSystemFontMap = fm.getSerializedSystemFontMap();
            }

            checkTime(startTime, "attachApplicationLocked: immediately before bindApplication");
            bindApplicationTimeMillis = SystemClock.uptimeMillis();
            mAtmInternal.preBindApplication(app.getWindowProcessController());
            final ActiveInstrumentation instr2 = app.getActiveInstrumentation();
            if (mPlatformCompat != null) {
                mPlatformCompat.resetReporting(app.info);
            }
            final ProviderInfoList providerList = ProviderInfoList.fromList(providers);
            if (app.getIsolatedEntryPoint() != null) {
                // This is an isolated process which should just call an entry point instead of
                // being bound to an application.
                thread.runIsolatedEntryPoint(
                        app.getIsolatedEntryPoint(), app.getIsolatedEntryPointArgs());
            } else if (instr2 != null) {
                thread.bindApplication(processName, appInfo,
                        app.sdkSandboxClientAppVolumeUuid, app.sdkSandboxClientAppPackage,
                        providerList,
                        instr2.mClass,
                        profilerInfo, instr2.mArguments,
                        instr2.mWatcher,
                        instr2.mUiAutomationConnection, testMode,
                        mBinderTransactionTrackingEnabled, enableTrackAllocation,
                        isRestrictedBackupMode || !normalMode, app.isPersistent(),
                        new Configuration(app.getWindowProcessController().getConfiguration()),
                        app.getCompat(), getCommonServicesLocked(app.isolated),
                        mCoreSettingsObserver.getCoreSettingsLocked(),
                        buildSerial, autofillOptions, contentCaptureOptions,
                        app.getDisabledCompatChanges(), serializedSystemFontMap,
                        app.getStartElapsedTime(), app.getStartUptime());
            } else {
                thread.bindApplication(processName, appInfo,
                        app.sdkSandboxClientAppVolumeUuid, app.sdkSandboxClientAppPackage,
                        providerList, null, profilerInfo, null, null, null, testMode,
                        mBinderTransactionTrackingEnabled, enableTrackAllocation,
                        isRestrictedBackupMode || !normalMode, app.isPersistent(),
                        new Configuration(app.getWindowProcessController().getConfiguration()),
                        app.getCompat(), getCommonServicesLocked(app.isolated),
                        mCoreSettingsObserver.getCoreSettingsLocked(),
                        buildSerial, autofillOptions, contentCaptureOptions,
                        app.getDisabledCompatChanges(), serializedSystemFontMap,
                        app.getStartElapsedTime(), app.getStartUptime());
            }
            if (profilerInfo != null) {
                profilerInfo.closeFd();
                profilerInfo = null;
            }

            // Make app active after binding application or client may be running requests (e.g
            // starting activities) before it is ready.
            synchronized (mProcLock) {
                app.makeActive(thread, mProcessStats);
                checkTime(startTime, "attachApplicationLocked: immediately after bindApplication");
            }
            updateLruProcessLocked(app, false, null);
            checkTime(startTime, "attachApplicationLocked: after updateLruProcessLocked");
            final long now = SystemClock.uptimeMillis();
            synchronized (mAppProfiler.mProfilerLock) {
                app.mProfile.setLastRequestedGc(now);
                app.mProfile.setLastLowMemory(now);
            }
        } catch (Exception e) {
            // We need kill the process group here. (b/148588589)
            Slog.wtf(TAG, "Exception thrown during bind of " + app, e);
            app.resetPackageList(mProcessStats);
            app.unlinkDeathRecipient();
            app.killLocked("error during bind", ApplicationExitInfo.REASON_INITIALIZATION_FAILURE,
                    true);
            handleAppDiedLocked(app, pid, false, true, false /* fromBinderDied */);
            return false;
        }

        // Remove this record from the list of starting applications.
        mPersistentStartingProcesses.remove(app);
        if (DEBUG_PROCESSES && mProcessesOnHold.contains(app)) Slog.v(TAG_PROCESSES,
                "Attach application locked removing on hold: " + app);
        mProcessesOnHold.remove(app);

        boolean badApp = false;
        boolean didSomething = false;

        // See if the top visible activity is waiting to run in this process...
        if (normalMode) {
            try {
                didSomething = mAtmInternal.attachApplication(app.getWindowProcessController());
            } catch (Exception e) {
                Slog.wtf(TAG, "Exception thrown launching activities in " + app, e);
                badApp = true;
            }
        }

        // Find any services that should be running in this process...
        if (!badApp) {
            try {
                didSomething |= mServices.attachApplicationLocked(app, processName);
                checkTime(startTime, "attachApplicationLocked: after mServices.attachApplicationLocked");
            } catch (Exception e) {
                Slog.wtf(TAG, "Exception thrown starting services in " + app, e);
                badApp = true;
            }
        }

        if (!badApp) {
            updateUidReadyForBootCompletedBroadcastLocked(app.uid);
        }

        // Check if a next-broadcast receiver is in this process...
        if (!badApp && isPendingBroadcastProcessLocked(pid)) {
            try {
                didSomething |= sendPendingBroadcastsLocked(app);
                checkTime(startTime, "attachApplicationLocked: after sendPendingBroadcastsLocked");
            } catch (Exception e) {
                // If the app died trying to launch the receiver we declare it 'bad'
                Slog.wtf(TAG, "Exception thrown dispatching broadcasts in " + app, e);
                badApp = true;
            }
        }

        // Check whether the next backup agent is in this process...
        if (!badApp && backupTarget != null && backupTarget.app == app) {
            if (DEBUG_BACKUP) Slog.v(TAG_BACKUP,
                    "New app is backup target, launching agent for " + app);
            notifyPackageUse(backupTarget.appInfo.packageName,
                             PackageManager.NOTIFY_PACKAGE_USE_BACKUP);
            try {
                thread.scheduleCreateBackupAgent(backupTarget.appInfo,
                        compatibilityInfoForPackage(backupTarget.appInfo),
                        backupTarget.backupMode, backupTarget.userId, backupTarget.operationType);
            } catch (Exception e) {
                Slog.wtf(TAG, "Exception thrown creating backup agent in " + app, e);
                badApp = true;
            }
        }

        if (badApp) {
            app.killLocked("error during init", ApplicationExitInfo.REASON_INITIALIZATION_FAILURE,
                    true);
            handleAppDiedLocked(app, pid, false, true, false /* fromBinderDied */);
            return false;
        }

        if (!didSomething) {
            updateOomAdjLocked(app, OomAdjuster.OOM_ADJ_REASON_PROCESS_BEGIN);
            checkTime(startTime, "attachApplicationLocked: after updateOomAdjLocked");
        }


        final HostingRecord hostingRecord = app.getHostingRecord();
        String shortAction = getShortAction(hostingRecord.getAction());
        FrameworkStatsLog.write(
                FrameworkStatsLog.PROCESS_START_TIME,
                app.info.uid,
                pid,
                app.info.packageName,
                FrameworkStatsLog.PROCESS_START_TIME__TYPE__COLD,
                app.getStartElapsedTime(),
                (int) (bindApplicationTimeMillis - app.getStartUptime()),
                (int) (SystemClock.uptimeMillis() - app.getStartUptime()),
                hostingRecord.getType(),
                hostingRecord.getName(),
                shortAction,
                HostingRecord.getHostingTypeIdStatsd(hostingRecord.getType()));
        return true;
    }
```