A task is a collection of activities that users interact with when trying to do something in your app. 
These activities are arranged in a stack—the back stack—in the order in which each activity is opened.

A task is a cohesive unit that can move to the background when a user begins a new task or goes to the Home screen.

While in the background, all the activities in the task are stopped, but the back stack for the task remains intact—the task has simply lost focus while another task takes place.


```java
    enum ActivityState {
        INITIALIZING,
        STARTED,
        RESUMED,
        PAUSING,
        PAUSED,
        STOPPING,
        STOPPED,
        FINISHING,
        DESTROYING,
        DESTROYED,
        RESTARTING_PROCESS
    }
    Intent intent;          // The original intent that started the task. Note that this value can
                            // be null.
    int effectiveUid;       // The current effective uid of the identity of this task.
    final RootWindowContainer mRootWindowContainer;
    final ActivityTaskManagerService mAtmService;

    /** ActivityRecords that are exiting, but still on screen for animations. */
    final ArrayList<ActivityRecord> mExitingActivities = new ArrayList<>();
    /**
     * When we are in the process of pausing an activity, before starting the
     * next one, this variable holds the activity that is currently being paused.
     *
     * Only set at leaf tasks.
     */
    @Nullable
    private ActivityRecord mPausingActivity = null;
        /**
     * Current activity that is resumed, or null if there is none.
     * Only set at leaf tasks.
     */
    @Nullable
    private ActivityRecord mResumedActivity = null;


```