

### part of wm

os/server side state of an Activity.

```java
/**
 * An entry in the history task, representing an activity.
 */
final class ActivityRecord extends WindowToken{
    private ActivityState mState;    // current state we are in
    final ActivityTaskManagerService mAtmService;
    final ActivityInfo info; // activity info provided by developer in AndroidManifest
    // TODO: rename to mActivityToken
    final ActivityRecord.Token appToken;
    // Which user is this running for?
    final int mUserId;
    // The package implementing intent's component
    // TODO: rename to mPackageName
    final String packageName;
    // the intent component, or target of an alias.
    final ComponentName mActivityComponent;
    final Intent intent;    // the original intent that generated us
    final String processName; // process where this component wants to run

}
```