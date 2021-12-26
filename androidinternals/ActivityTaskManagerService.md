
System service for managing activities and their containers (task, displays,... ).



### IActivityTaskManager.aidl

System private API for talking with the activity task manager that handles how activities are managed on screen.

```java
interface IActivityTaskManager {
    int startActivity(in IApplicationThread caller, in String callingPackage,
            in String callingFeatureId, in Intent intent, in String resolvedType,
            in IBinder resultTo, in String resultWho, int requestCode,
            int flags, in ProfilerInfo profilerInfo, in Bundle options);

    void startRecentsActivity(in Intent intent, in long eventTime,
            in IRecentsAnimationRunner recentsAnimationRunner);
    int startActivityFromRecents(int taskId, in Bundle options);
    void setFrontActivityScreenCompatMode(int mode);
    void setFocusedTask(int taskId);
    boolean removeTask(int taskId);
    List<ActivityManager.RunningTaskInfo> getTasks(int maxNum, boolean filterOnlyVisibleRecents,
            boolean keepIntentExtra);
    void moveTaskToFront(in IApplicationThread app, in String callingPackage, int task,
            int flags, in Bundle options);
    List<IBinder> getAppTasks(in String callingPackage);
    int addAppTask(in IBinder activityToken, in Intent intent,
            in ActivityManager.TaskDescription description, in Bitmap thumbnail);
    void registerTaskStackListener(in ITaskStackListener listener);
    void unregisterTaskStackListener(in ITaskStackListener listener);
    void moveRootTaskToDisplay(int taskId, int displayId);

    void moveTaskToRootTask(int taskId, int rootTaskId, boolean toTop);
    List<ActivityTaskManager.RootTaskInfo> getAllRootTaskInfos();
    ActivityTaskManager.RootTaskInfo getRootTaskInfo(int windowingMode, int activityType);
    /**
     * Informs ActivityTaskManagerService that the keyguard is showing.
     *
     * @param showingKeyguard True if the keyguard is showing, false otherwise.
     * @param showingAod True if AOD is showing, false otherwise.
     */
    void setLockScreenShown(boolean showingKeyguard, boolean showingAod);
    /**
     * Notify the system that the keyguard is going away.
     *
     * @param flags See
     *              {@link android.view.WindowManagerPolicyConstants#KEYGUARD_GOING_AWAY_FLAG_TO_SHADE}
     *              etc.
     */
    void keyguardGoingAway(int flags);

    /**
     * Updates global configuration and applies changes to the entire system.
     * @param values Update values for global configuration. If null is passed it will request the
     *               Window Manager to compute new config for the default display.
     * @throws RemoteException
     * @return Returns true if the configuration was updated.
     */
    boolean updateConfiguration(in Configuration values);

```