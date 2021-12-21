


### Persistent apps

systema apps are considered persistent.
This property allows you to start a background service on Oreo and prevents it from being automatically killed.

### methods that check service start restrictions

```java
    int appRestrictedInBackgroundLOSP(int uid, String packageName, int packageTargetSdk) {
    }

    int appServicesRestrictedInBackgroundLOSP(int uid, String packageName, int packageTargetSdk) {
    }

    private boolean uidOnBackgroundAllowlistLOSP(final int uid) {
    }
```

### AMS whitelist for battery optimization

```java

    /**
     * Power-save allowlisted app-ids (not including except-idle-allowlisted ones).
     */
    @CompositeRWLock({"this", "mProcLock"})
    int[] mDeviceIdleAllowlist = new int[0];

    /**
     * Power-save allowlisted app-ids (including except-idle-allowlisted ones).
     */
    @CompositeRWLock({"this", "mProcLock"})
    int[] mDeviceIdleExceptIdleAllowlist = new int[0];

    /**
     * Set of app ids that are temporarily allowed to escape bg check due to high-pri message
     */
    @CompositeRWLock({"this", "mProcLock"})
    int[] mDeviceIdleTempAllowlist = new int[0];


```