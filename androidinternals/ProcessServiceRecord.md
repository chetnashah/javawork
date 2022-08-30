The state info of all services in the process.

## imp members

```java
final class ProcessServiceRecord {
    /**
     * Are there any client services with activities?
     */
    private boolean mHasClientActivities;

    /**
     * Running any services that are foreground?
     */
    private boolean mHasForegroundServices;

    /**
     * Service that applied current connectionGroup/Importance.
     */
    private ServiceRecord mConnectionService;

    /**
     * Last group set by a connection.
     */
    private int mConnectionGroup;

    /**
     * Bound using BIND_TREAT_LIKE_ACTIVITY.
     */
    private boolean mTreatLikeActivity;

    /**
     * All ServiceRecord running in this process.
     */
    final ArraySet<ServiceRecord> mServices = new ArraySet<>();
    /**
     * Services that are currently executing code (need to remain foreground).
     */
    private final ArraySet<ServiceRecord> mExecutingServices = new ArraySet<>();

    /**
     * All ConnectionRecord this process holds.
     */
    private final ArraySet<ConnectionRecord> mConnections = new ArraySet<>();

    /**
     * A set of UIDs of all bound clients.
     */
    private ArraySet<Integer> mBoundClientUids = new ArraySet<>();


    final ProcessRecord mApp;

    private final ActivityManagerService mService;
```

