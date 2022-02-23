

```java

/**
 * System private API for communicating with the application.  This is given to
 * the activity manager by an application  when it starts up, for the activity
 * manager to tell the application about things it needs to do.
 *
 * {@hide}
 */
oneway interface IApplicationThread {
    void scheduleTransaction(in ClientTransaction transaction);
    void scheduleReceiver(in Intent intent, in ActivityInfo info,
            in CompatibilityInfo compatInfo,
            int resultCode, in String data, in Bundle extras, boolean sync,
            int sendingUser, int processState);
    void scheduleCreateService(IBinder token, in ServiceInfo info,
            in CompatibilityInfo compatInfo, int processState);
    void scheduleStopService(IBinder token);
    void bindApplication(in String packageName, in ApplicationInfo info,
            in List<ProviderInfo> providers, in ComponentName testName,
            in ProfilerInfo profilerInfo, in Bundle testArguments,
            IInstrumentationWatcher testWatcher, IUiAutomationConnection uiAutomationConnection,
            int debugMode, boolean enableBinderTracking, boolean trackAllocation,
            boolean restrictedBackupMode, boolean persistent, in Configuration config,
            in CompatibilityInfo compatInfo, in Map services,
            in Bundle coreSettings, in String buildSerial, boolean isAutofillCompatEnabled);
    void scheduleExit();
    void scheduleServiceArgs(IBinder token, in ParceledListSlice args);
    void updateTimeZone();
    void processInBackground();
    void scheduleBindService(IBinder token,
            in Intent intent, boolean rebind, int processState);
    void scheduleUnbindService(IBinder token,
            in Intent intent);
    void scheduleLowMemory();
    void scheduleSleeping(IBinder token, boolean sleeping);
    void scheduleOnNewActivityOptions(IBinder token, in Bundle options);
    void scheduleTrimMemory(int level);
    void setProcessState(int state);
    void scheduleApplicationInfoChanged(in ApplicationInfo ai);
}
```