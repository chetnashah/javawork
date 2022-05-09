## onCreate

If your activity was starting fresh, this Bundle savedInstanceState in `onCreate()` is null. 

So if the bundle is not null, you know you're "re-creating" the activity from a previously known point.



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

### use onSaveInstanceState to save bundle data (by instance state we mean activity instance state)

`onSaveInstanceStatEe() is called after activity has been stopped but before being destroyed`.
This is usually used to save data before destruction as a part of configuration change,
and this data can be recovered in `onRestoreInstanceState()` calback or in `onCreate` callback both.

Combination of `onSaveInstanceState` + `onCreate` is an interesting combination to persist Activity level instance variables (i.e. activity instance state) across process restarts without using stuff like sharedprefs/files or any other persisted mechanism.



