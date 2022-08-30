

A running application service

## Imp members

```java
final class ServiceRecord extends Binder implements ComponentName.WithComponentName {
    final ActivityManagerService ams;
    final ComponentName name; // service component.
    final ComponentName instanceName; // service component's per-instance name.
    final String shortInstanceName; // instanceName.flattenToShortString().
    final String definingPackageName;
                            // Can be different from appInfo.packageName for external services
    final int definingUid;
                            // Can be different from appInfo.uid for external services
    final ServiceInfo serviceInfo;
                            // all information about the service.
    ApplicationInfo appInfo;
                            // information about service's app.
    final int userId;       // user that this service is running as
    final String packageName; // the package implementing intent's component
    final String processName; // process where this component wants to run
    final String permission;// permission needed to access service
    final boolean exported; // from ServiceInfo.exported
    final ArrayMap<Intent.FilterComparison, IntentBindRecord> bindings
            = new ArrayMap<Intent.FilterComparison, IntentBindRecord>();
                            // All active bindings to the service.
    private final ArrayMap<IBinder, ArrayList<ConnectionRecord>> connections
            = new ArrayMap<IBinder, ArrayList<ConnectionRecord>>();
                            // IBinder -> ConnectionRecord of all bound clients

    ProcessRecord app;      // where this service is running or null.
    boolean delayed;        // are we waiting to start this service in the background?
    boolean fgRequired;     // is the service required to go foreground after starting?
    boolean fgWaiting;      // is a timeout for going foreground already scheduled?
    boolean isNotAppComponentUsage; // is service binding not considered component/package usage?
    boolean isForeground;   // is service currently in foreground mode?
    Notification foregroundNoti; // Notification record of foreground state.
    int foregroundServiceType; // foreground service types.
    
    // any current binding to this service has BIND_ALLOW_BACKGROUND_ACTIVITY_STARTS flag?
    private boolean mIsAllowedBgActivityStartsByBinding;
    // is this service currently allowed to start activities from background by providing
    // allowBackgroundActivityStarts=true to startServiceLocked()?
    private boolean mIsAllowedBgActivityStartsByStart;
    // These are the originating tokens that currently allow bg activity starts by service start.
    // This is used to trace back the grant when starting activities. We only pass such token to the
    // ProcessRecord if it's the *only* cause for bg activity starts exemption, otherwise we pass
    // null.
    @GuardedBy("ams")
    private List<IBinder> mBgActivityStartsByStartOriginatingTokens = new ArrayList<>();
    // allow while-in-use permissions in foreground service or not.
    // while-in-use permissions in FGS started from background might be restricted.
    boolean mAllowWhileInUsePermissionInFgs;
    // A copy of mAllowWhileInUsePermissionInFgs's value when the service is entering FGS state.
    boolean mAllowWhileInUsePermissionInFgsAtEntering;

    // the most recent package that start/bind this service.
    String mRecentCallingPackage;
    // the most recent uid that start/bind this service.
    int mRecentCallingUid;
    // ApplicationInfo of the most recent callingPackage that start/bind this service.
    @Nullable ApplicationInfo mRecentCallerApplicationInfo;

    final ArrayList<StartItem> deliveredStarts = new ArrayList<StartItem>();
                            // start() arguments which been delivered.
    final ArrayList<StartItem> pendingStarts = new ArrayList<StartItem>();
                            // start() arguments that haven't yet been delivered.
```


## Nested inner class StartItem

```java
    static class StartItem {
        final ServiceRecord sr;
        final boolean taskRemoved;
        final int id;
        final int callingId;
        final Intent intent;
        final NeededUriGrants neededGrants;
        long deliveredTime;
        int deliveryCount;
        int doneExecutingCount;
        UriPermissionOwner uriPermissions;
    }
```