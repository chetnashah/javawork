

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