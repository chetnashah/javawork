

### ActivityThread

`ActivityThread` extends `ClientTransactionHandler`

Important classes

### imp members

```java
mH = new ActivityThread.H();
final ArrayMap<IBinder, ActivityClientRecord> mActivities = new ArrayMap<>();
final ArrayMap<IBinder, Service> mServices = new ArrayMap<>();
```

#### ActivityThread.H

All important messages are posted through this handler.
`mH = new ActivityThread.H()` is a iniline initializer

#### ActivityThread.ApplicationThread extends IApplicationThread.Stub

`mAppThread = new ActivityThread.ApplicationThread()` is an inline initializer

```java
    /**
     * Maps from activity token to local record of running activities in this process.
     *
     * This variable is readable if the code is running in activity thread or holding {@link
     * #mResourcesManager}. It's only writable if the code is running in activity thread and holding
     * {@link #mResourcesManager}.
     */
    @UnsupportedAppUsage
    final ArrayMap<IBinder, ActivityClientRecord> mActivities = new ArrayMap<>();
```

### ActivityThread.AppBindData

AppBindData class/data structure

```java
static final class AppBindData {
    ApplicationInfo appInfo;
    LoadedApk info;
}
```

### ActivityThread.ActivityClientRecord
Client Activity book keeping

### ActivityThread has `main` method:

This method creates the Main (UI) Thread for an OS process, 
sets up the Looper on it and starts the event loop.

```java
  // called for normal process UI thread creation
  public static void main(String[] args) {
      //....
    Looper.prepareMainLooper();
    ActivityThread thread = new ActivityThread();// this will initialize mH, mAppThread etc on thread
    thread.attach(false, startSeq);// false means app process, not system server
    Looper.loop();
  }
```


#### system_Server i.e. system main init:

```java
// called for system server init
   public static ActivityThread systemMain() {
        ThreadedRenderer.initForSystemProcess();
        ActivityThread thread = new ActivityThread();
        thread.attach(true, 0);
        return thread;
    }
```

### ActivityThread.attach

attaching tells AMS to attach mAppThread

```java
private void attach(boolean system, long startSeq) {// Instance method
    if (!system) {
        RuntimeInit.setApplicationObject(mAppThread.asBinder());
        final IActivityManager mgr = ActivityManager.getService();
        try {
            mgr.attachApplication(mAppThread, startSeq);// activitymanager service.attachApplication
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    } else {
        android.ddm.DdmHandleAppName.setAppName("system_process",
                    UserHandle.myUserId());
        try {
            mInstrumentation = new Instrumentation();
            mInstrumentation.basicInit(this);
            ContextImpl context = ContextImpl.createAppContext(
                    this, getSystemContext().mPackageInfo);
            mInitialApplication = context.mPackageInfo.makeApplication(true, null);
            mInitialApplication.onCreate();
        } catch (Exception e) {
            throw new RuntimeException(
                    "Unable to instantiate Application():" + e.toString(), e);
        }
    }
}
```


### ActivityThread.handleBindApplication

```java
// instance method
private void handleBindApplication(AppBindData data) {
    mBoundApplication = data;
    final ContextImpl appContext = ContextImpl.createAppContext(this, data.info);
    // Continue loading instrumentation.
    if (ii != null) {
        initInstrumentation(ii, data, appContext);
    } else {
        mInstrumentation = new Instrumentation();
        mInstrumentation.basicInit(this);
    }
    Application app;
    app = data.info.makeApplication(data.restrictedBackupMode, null);
    mInitialApplication = app;
    mInstrumentation.onCreate(data.instrumentationArgs);
    mInstrumentation.callApplicationOnCreate(app); // Application subclass -> onCreate
}
```



### Activity create and Launch flow

callbacks come from AMS

#### ActivityThread.handleLaunchActivity

#### ActivityThread.performLaunchActivity




