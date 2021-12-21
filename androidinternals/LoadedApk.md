

### Main info for all things in loaded apk

Local state maintained about a currently loaded `.apk`.

### important field members:

```java
ActivityThread mActivityThread;
    private ApplicationInfo mApplicationInfo;
    private Application mApplication;

```



### application creation

```java
// instance method
public Application makeApplication(boolean forceDefaultAppClass,
            Instrumentation instrumentation) {
    ContextImpl appContext = ContextImpl.createAppContext(mActivityThread, this);
    app = mActivityThread.mInstrumentation.newApplication(
            cl, appClass, appContext);
    appContext.setOuterContext(app);
    mActivityThread.mAllApplications.add(app);
    mApplication = app;
    instrumentation.callApplicationOnCreate(app);
    return app;
```