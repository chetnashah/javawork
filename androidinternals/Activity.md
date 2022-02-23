

Activity has a default no-arg constructor

### Creation 

```
<init>:87, AppCompatActivity (androidx.appcompat.app)
<init>:6, MainActivity (com.example.helloworld)
newInstance:-1, Class (java.lang)
instantiateActivity:95, AppComponentFactory (android.app)
instantiateActivity:45, CoreComponentFactory (androidx.core.app)
newActivity:1273, Instrumentation (android.app)
performLaunchActivity:3532, ActivityThread (android.app)
handleLaunchActivity:3792, ActivityThread (android.app)
execute:103, LaunchActivityItem (android.app.servertransaction)
executeCallbacks:135, TransactionExecutor (android.app.servertransaction)
execute:95, TransactionExecutor (android.app.servertransaction)
handleMessage:2210, ActivityThread$H (android.app)
dispatchMessage:106, Handler (android.os)
loopOnce:201, Looper (android.os)
loop:288, Looper (android.os)
main:7839, ActivityThread (android.app)
invoke:-1, Method (java.lang.reflect)
run:548, RuntimeInit$MethodAndArgsCaller (com.android.internal.os)
main:1003, ZygoteInit (com.android.internal.os)
```

Creation in `ActivityThread.performLaunchActivity`:
Core implementation of activity launch.
```java
// instance method on ActivityThread
private Activity performLaunchActivity(ActivityClientRecord r, Intent customIntent) {
    ActivityInfo aInfo = r.activityInfo;
    ContextImpl appContext = createBaseContextForActivity(r);
    Activity activity = null;
    activity = mInstrumentation.newActivity(cl, component.getClassName(), r.intent);// calls into appcompnentfactory which finally makes a new Activity instance

    Application app = r.packageInfo.makeApplication(false, mInstrumentation);
    activity.attach(appContext, this, getInstrumentation(), r.token,
                        r.ident, app, r.intent, r.activityInfo, title, r.parent,
                        r.embeddedID, r.lastNonConfigurationInstances, config,
                        r.referrer, r.voiceInteractor, window, r.configCallback,
                        r.assistToken, r.shareableActivityToken);
                    mInstrumentation.callActivityOnCreate(activity, r.state);
    r.activity = activity;
    return activity;
}
```