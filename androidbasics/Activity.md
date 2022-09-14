## onCreate

If your activity was starting fresh, this Bundle savedInstanceState in `onCreate()` is null. 

So if the bundle is not null, you know you're "re-creating" the activity from a previously known point.


## ComponentActivity

Knows nothing about fragments
https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/ComponentActivity.java;l=157?q=componentact&sq=

## FragmentActivity

Built on top of ComponentActivity, has basic support of fragments

https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:fragment/fragment/src/main/java/androidx/fragment/app/FragmentActivity.java;l=107

## AppCompat Activity

Typically used by devs as an extension point, built on top of FragmentActivity.

https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:appcompat/appcompat/src/main/java/androidx/appcompat/app/AppCompatActivity.java;l=89?q=appcompatact&sq=

### AndroidManifest.xml activity attribute parentActivityName

`parentActivityName`: With the parentActivityName attribute, you indicate that the main activity is the parent of the second activity. This `relationship is used for Up navigation in your app: the app bar for the second activity will have a left-facing arrow so the user can navigate "upward" to the main activity`.

One way to do other than adding this attribute is to add meta-data via following:
```xml
<activity android:name=".SecondActivity"
    android:label = "Second Activity"
    android:parentActivityName=".MainActivity">
    <meta-data        <!-- alternate way -->
        android:name="android.support.PARENT_ACTIVITY"
        android:value=
                  "com.example.android.twoactivities.MainActivity" />
</activity>
```


### label attribute

The label attribute adds the title of the Activity to the app bar.

### How to return data from activity via intent before finishing a activity?

**Update** - https://stackoverflow.com/questions/62671106/onactivityresult-method-is-deprecated-what-is-the-alternative is the newest way
https://developer.android.com/training/basics/intents/result#register

`Old way`:
`setResult` and `finish` on finishing activity side
`startActivityForResult(requestCode, intent)` to start activity and `Activity.onActivityResult()` is a callback on original parent activity side

```java
onCick(){
    String reply = mReply.getText().toString();
    Intent replyIntent = new Intent(); // no need to put reference to parentActivity
    replyIntent.putExtra(EXTRA_REPLY, reply);
    setResult(RESULT_OK, replyIntent);
    finish();
}
```

When an activity exits, it can call `setResult(int)` to return data back to its parent. It must always supply a result code, which can be the standard results `RESULT_CANCELED`, `RESULT_OK`, or any custom values starting at `RESULT_FIRST_USER`. In addition, it can optionally return back an Intent containing any additional data it wants. All of this information appears back on the parent's `Activity.onActivityResult()`, along with the integer identifier it originally supplied

## Home button behavior

Activity stays in memory but in STOPPED state. Activity will not be destroyed.


## Back button behavior

using the `Back button causes the activity (and the app) to be entirely closed`. The execution of the `onDestroy()` method means that the activity was fully shut down and can be garbage-collected. 

Garbage collection refers to the automatic cleanup of objects that you'll no longer use. After onDestroy() is called, the system knows that those resources are discardable, and it starts cleaning up that memory..

## Ways an activity can get destroyed

1. back button press
2. self calling `finish()`.
3. App force-quit using close/recents
4. OS memory/resource killer

## Configuration changes

A configuration change happens when the state of the device changes so radically that the easiest way for the system to resolve the change is to completely shut down and rebuild the activity.

cases when configuration change might happen:
1. device rotation
2. dock & keyboard

`Activity is destroyed and recreated on configuration change by default.`

### use onSaveInstanceState to save bundle data (by instance state we mean activity instance state - i.e serialized data)

`onSaveInstanceStatEe() is called after activity has been stopped but before being destroyed`.
This is usually used to save data before destruction as a part of configuration change,
and this data can be recovered in `onRestoreInstanceState()` calback or in `onCreate` callback both.

Combination of `onSaveInstanceState` + `onCreate` is an interesting combination to persist Activity level instance variables (i.e. activity instance state) across process restarts without using stuff like sharedprefs/files or any other persisted mechanism.

`onSavedInstanceState()` serializes data to disk, which is available in `onCreate`/`onSaveRestoreInstanceState()`. Note it does not retain live objects, only its data in serialized form.

Do not use store onSavedInstanceState() to store large amounts of data, such as bitmaps, nor complex data structures that require lengthy serialization or deserialization.

## Config changes and object retain across config changes

`public Object onRetainNonConfigurationInstance ()` - Called by the system, as part of destroying an activity due to a configuration change, when it is known that a new instance will immediately be created for the new configuration. 

You can return any object you like here, including the activity instance itself, which can later be retrieved by calling `getLastNonConfigurationInstance()` in the new activity instance.

## Accessing NavHostFragment in Activity

```kt
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        setupActionBarWithNavController(navController)
    }
}
```

## Checking if intent to an activity actually resolves to something before navigating

```kt
if (activity?.packageManager?.resolveActivity(intent, 0) != null) {
    startActivity(intent)
}
```

## When is onDestroy called?

onDestroy() is called only when system is low on resources(memory, cpu time and so on) and makes a decision to kill your activity/application or when somebody calls finish() on your activity.

It may not be called during process kill.

if your app is no longer running normally (you crashed) or your app is gone (process terminated), onDestroy() is not relevant.

`onDestroy` can be useful for graceful termination of started threads if any, but it can be ignored, as process termination already kills all the threads along with process. db/cleanup work should ideally be in onStop instead of onDestroy.

## Lifecycle call order for two activities

### Going from activity A -> activity B

* ActivitA: onPause
* ActivityB: onCreate
* ActivityB: onStart
* ActivityB: onResume
* ActivityA: onStop


### Going back from Activity B -> Activity A

* ActivityB: onPause
* ActivityA: onRestart
* ActivityA: onStart
* ActivityA: onResume
* ActivityB: onStop
* ActivityB: onDestroy


## Lifecycle call order on screen lock

* onPause
* onStop

## Lifecycle order on screen unlock

* onRestart
* onStart
* onResume

## Home button click 

* onPause
* onStop

### resume app from recents

* onRestart
* onStart
* onResume


## Killing app from recents

* onPause
* onStop
* onDestroy

## Fresh app launch

* onCreate
* onStart
* onResume


## Activity/task launch after process death

Lets say we have activities: A->B->C, we press home and task moved to background.

Now we open a lot more apps and our app process is killed/evicted.

If we relaunch the app or go from recents, which activity is opened?
Ans: **The innermost activity where the user left off**, e.g. in this case **C activity**. Also note: A and B are not started at this point, only C.

//TODO verify with code.


## Launch modes

### Standard

Multiple activities allowed, even if same activity present on top.
E.g. A->B->C->C->C is fine

### Singletop

If same activity on top, do not push and deliver `onNewIntent()`.
If not same activity ont top, push it.

In essence think of it as double top not possible.
e.g. `A->B->C->C` is not allowed!
but `A->B->C->D->C` is fine.


### Single Task

Single task means -> single instance in a task. (pop everything if necessary).
e.g. if your task stack is `A->B->C->D` and you push `B`, your stack will pop everything upto B.
and result in `A->B` and call `B` activity's `onNewIntent()`.

### SingleInstance

It is similar to singleTask except that **no other activities will be created in the same task**. If another Activity is called from this kind of Activity, a new Task would be automatically created to place that new Activity.

If the same activity is already present in an isolated task, it gets `onNewIntent()` instead of double instances.