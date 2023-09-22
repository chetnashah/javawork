
## Triggering local FCM

### Find the broadcast receiver

You can search your code for the receiver that handles the following action:

`com.google.android.c2dm.intent.RECEIVE`.

### adb shell command to try

It uses adb's `am` - Activity Manager's `broadcast` subcommand to fire the intent that is picked up for Push Notification usually.

```
adb shell am broadcast -a com.google.android.c2dm.intent.RECEIVE -n <YOUR_PACKAGE_NAME>/<YOUR_RECEIVER_NAME> --es "<EXTRA_KEY>" "<EXTRA_VALUE>"
```

### Permission missing

Usually your broadcast receiver will also have following permission declared for sender:
`   android:permission="com.google.android.c2dm.permission.SEND"` but this permission is only held by Google play services. 

So either 
1. remove this permission temporarily OR
2. Try `adb root`, then 