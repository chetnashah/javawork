

```java
/**
 * System private API for talking with the activity manager service.  This
 * provides calls from the application back to the activity manager.
 *
 * {@hide}
 */
interface IActivityManager {
    ParcelFileDescriptor openContentUri(in String uriString);
    int startActivity(in IApplicationThread caller, in String callingPackage, in Intent intent,
            in String resolvedType, in IBinder resultTo, in String resultWho, int requestCode,
            int flags, in ProfilerInfo profilerInfo, in Bundle options);
    boolean finishActivity(in IBinder token, int code, in Intent data, int finishTask);
    Intent registerReceiver(in IApplicationThread caller, in String callerPackage,
            in IIntentReceiver receiver, in IntentFilter filter,
            in String requiredPermission, int userId, int flags);
    void unregisterReceiver(in IIntentReceiver receiver);
    int broadcastIntent(in IApplicationThread caller, in Intent intent,
            in String resolvedType, in IIntentReceiver resultTo, int resultCode,
            in String resultData, in Bundle map, in String[] requiredPermissions,
            int appOp, in Bundle options, boolean serialized, boolean sticky, int userId);
    oneway void finishReceiver(in IBinder who, int resultCode, in String resultData, in Bundle map,
            boolean abortBroadcast, int flags);
    void attachApplication(in IApplicationThread app, long startSeq);
    void moveTaskToFront(in IApplicationThread caller, in String callingPackage, int task,
            int flags, in Bundle options);
    ComponentName startService(in IApplicationThread caller, in Intent service,
            in String resolvedType, boolean requireForeground, in String callingPackage,
            in String callingFeatureId, int userId);
    int stopService(in IApplicationThread caller, in Intent service,
            in String resolvedType, int userId);
    int bindService(in IApplicationThread caller, in IBinder token, in Intent service,
            in String resolvedType, in IServiceConnection connection, int flags,
            in String callingPackage, int userId);
    void publishService(in IBinder token, in Intent intent, in IBinder service);
    boolean startInstrumentation(in ComponentName className, in String profileFile,
            int flags, in Bundle arguments, in IInstrumentationWatcher watcher,
            in IUiAutomationConnection connection, int userId,
            in String abiOverride);
    int startActivityAsUser(in IApplicationThread caller, in String callingPackage,
            in Intent intent, in String resolvedType, in IBinder resultTo, in String resultWho,
            int requestCode, int flags, in ProfilerInfo profilerInfo,
            in Bundle options, int userId);
    void moveTaskToRootTask(int taskId, int rootTaskId, boolean toTop);
    void setFocusedRootTask(int taskId);

// permissions stuff
    int checkUriPermission(in Uri uri, int pid, int uid, int mode, int userId,
            in IBinder callerToken);
    int[] checkUriPermissions(in List<Uri> uris, int pid, int uid, int mode,
                in IBinder callerToken);
    void grantUriPermission(in IApplicationThread caller, in String targetPkg, in Uri uri,
            int mode, int userId);
    void revokeUriPermission(in IApplicationThread caller, in String targetPkg, in Uri uri,
            int mode, int userId);

    IIntentSender getIntentSender(int type, in String packageName, in IBinder token,
            in String resultWho, int requestCode, in Intent[] intents, in String[] resolvedTypes,
            int flags, in Bundle options, int userId);
    void setServiceForeground(in ComponentName className, in IBinder token,
            int id, in Notification notification, int flags, int foregroundServiceType);
    void killApplication(in String pkg, int appId, int userId, in String reason);
    void killApplicationProcess(in String processName, int uid);
    void killBackgroundProcesses(in String packageName, int userId);
    boolean removeTask(int taskId);
    void killAllBackgroundProcesses();
    boolean killProcessesBelowForeground(in String reason);
    void killUid(int appId, int userId, in String reason);

    // L transactions
        void registerTaskStackListener(in ITaskStackListener listener);
    int startActivityFromRecents(int taskId, in Bundle options);

    // Start of N transactions
    boolean startBinderTracking();
    boolean unlockUser(int userid, in byte[] token, in byte[] secret,
            in IProgressListener listener);
    void killPackageDependents(in String packageName, int userId);
    void makePackageIdle(String packageName, int userId);
    void setRenderThread(int tid);

    // start of O transactions
    int restartUserInBackground(int userId);
    /**
     * Add a bare uid to the background restrictions whitelist.  Only the system uid may call this.
     */
    void backgroundAllowlistUid(int uid);


    // start of P transactions
    /**
     *  Similar to {@link #startUserInBackground(int userId), but with a listener to report
     *  user unlock progress.
     */
    boolean startUserInBackgroundWithListener(int userid, IProgressListener unlockProgressListener);
    /**
     * Start user, if it us not already running, and bring it to foreground.
     * unlockProgressListener can be null if monitoring progress is not necessary.
     */
    boolean startUserInForegroundWithListener(int userid, IProgressListener unlockProgressListener);
    ParceledListSlice<ApplicationExitInfo> getHistoricalProcessExitReasons(String packageName,
            int pid, int maxNum, int userId);
    /*
     * Kill the given PIDs, but the killing will be delayed until the device is idle
     * and the given process is imperceptible.
     */
    void killProcessesWhenImperceptible(in int[] pids, String reason);
    /**
     * Set locus context for a given activity.
     * @param activity
     * @param locusId a unique, stable id that identifies this activity instance from others.
     * @param appToken ActivityRecord's appToken.
     */
    void setActivityLocusContext(in ComponentName activity, in LocusId locusId,
            in IBinder appToken);

    boolean enableAppFreezer(in boolean enable);


}
```