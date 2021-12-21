A Service is an application component representing either an application's desire
  to perform a longer-running operation while not interacting with the user
  or to supply functionality for other applications to use.
A bound service is the server in a client-server interface.

Abstract class

### Service instance creation stack and onCreate

In `ActivityThread.handleCreateService`, `onCreate` also happen in the same call/stack

```
<init>:340, Service (android.app)
<init>:7, MyService (com.example.helloworld)
newInstance:-1, Class (java.lang)
instantiateService:129, AppComponentFactory (android.app)
instantiateService:75, CoreComponentFactory (androidx.core.app)
handleCreateService:4469, ActivityThread (android.app)
access$1700:247, ActivityThread (android.app)
handleMessage:2072, ActivityThread$H (android.app)
dispatchMessage:106, Handler (android.os)
loopOnce:201, Looper (android.os)
loop:288, Looper (android.os)
main:7839, ActivityThread (android.app)
invoke:-1, Method (java.lang.reflect)
run:548, RuntimeInit$MethodAndArgsCaller (com.android.internal.os)
main:1003, ZygoteInit (com.android.internal.os)
```


### Definition

```java
public abstract class Service extends ContextWrapper implements ComponentCallbacks2,
        ContentCaptureManager.ContentCaptureClient {
    
    public Service() {
        super(null);
    }
//

    public final void stopSelf(int startId) {
        if (mActivityManager == null) {
            return;
        }
        try {
            mActivityManager.stopServiceToken(
                    new ComponentName(this, mClassName), mToken, startId);
        } catch (RemoteException ex) {
        }
    }
    
    public final void startForeground(int id, Notification notification) {
        try {
            mActivityManager.setServiceForeground(
                    new ComponentName(this, mClassName), mToken, id,
                    notification, 0, FOREGROUND_SERVICE_TYPE_MANIFEST);
        } catch (RemoteException ex) {
        }
    }
```

### Important members of service class:

```java
    // set by the thread after the constructor and before onCreate(Bundle icicle) is called.
    private ActivityThread mThread = null;
    private String mClassName = null;
    private IBinder mToken = null;
    private Application mApplication = null;
```


### attach API

```java
    public final void attach(
            Context context,
            ActivityThread thread, String className, IBinder token,
            Application application, Object activityManager) {
        attachBaseContext(context);
        mThread = thread;           // NOTE:  unused - remove?
        mClassName = className;
        mToken = token;
        mApplication = application;
        mActivityManager = (IActivityManager)activityManager;
        mStartCompatibility = getApplicationInfo().targetSdkVersion
                < Build.VERSION_CODES.ECLAIR;

        setContentCaptureOptions(application.getContentCaptureOptions());
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        if (newBase != null) {
            newBase.setContentCaptureOptions(getContentCaptureOptions());
        }
    }

```

### ActivityThread.handleServiceCreate

```java
// instance method on ActivityThread
private void handleCreateService(CreateServiceData data) {
    LoadedApk packageInfo = getPackageInfoNoCheck(data.info.applicationInfo, data.compatInfo);
    Application app = packageInfo.makeApplication(false, mInstrumentation);
    service = packageInfo.getAppFactory().instantiateService(cl, data.info.name, data.intent);
    ContextImpl context = ContextImpl.getImpl(service
                    .createServiceBaseContext(this, packageInfo));
    service.attach(context, this, data.info.name, data.token, app,ActivityManager.getService());
    service.onCreate();
    mServicesData.put(data.token, data);
    mServices.put(data.token, service);
    ActivityManager.getService().serviceDoneExecuting(data.token, SERVICE_DONE_EXECUTING_ANON, 0, 0);
}
```

### onStartCommand stack

```java
onStartCommand:14, MyService (com.example.helloworld)
handleServiceArgs:4639, ActivityThread (android.app)
access$2000:247, ActivityThread (android.app)
handleMessage:2091, ActivityThread$H (android.app)
dispatchMessage:106, Handler (android.os)
loopOnce:201, Looper (android.os)
loop:288, Looper (android.os)
main:7839, ActivityThread (android.app)
invoke:-1, Method (java.lang.reflect)
run:548, RuntimeInit$MethodAndArgsCaller (com.android.internal.os)
main:1003, ZygoteInit (com.android.internal.os)
```


### ActivityThread.handleBindService

```java
private void handleBindService(BindServiceData data) {
    CreateServiceData createData = mServicesData.get(data.token);
    Service s = mServices.get(data.token);
    if (!data.rebind) {
        IBinder binder = s.onBind(data.intent);
        ActivityManager.getService().publishService(
                data.token, data.intent, binder);
    } else {
        s.onRebind(data.intent);
        ActivityManager.getService().serviceDoneExecuting(
                data.token, SERVICE_DONE_EXECUTING_ANON, 0, 0);
    }
}
```

### ActivityThread.handleServiceArgs

manages `onStartCommand`

```java
private void handleServiceArgs(ServiceArgsData data) {
    CreateServiceData createData = mServicesData.get(data.token);
    Service s = mServices.get(data.token);
    if (data.args != null) {
        data.args.setExtrasClassLoader(s.getClassLoader());
        data.args.prepareToEnterProcess(isProtectedComponent(createData.info),s.getAttributionSource());
    }
    int res;
    if (!data.taskRemoved) {
        res = s.onStartCommand(data.args, data.flags, data.startId);
    } else {
        s.onTaskRemoved(data.args);
        res = Service.START_TASK_REMOVED_COMPLETE;
    }

    QueuedWork.waitToFinish();
    try {
        ActivityManager.getService().serviceDoneExecuting(
                data.token, SERVICE_DONE_EXECUTING_START, data.startId, res);
    } catch (RemoteException e) {
        throw e.rethrowFromSystemServer();
    }

}
```