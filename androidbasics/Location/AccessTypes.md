
## Allow only while using the app
This option is the recommended option for most apps. Also known as "while-in-use" or "foreground only" access, this option was added in Android 10 and allows developers to retrieve location only while the app is actively being used. 
An app is considered to be active if either of the following is true:
1. An activity is visible.
2. A foreground service is running with an ongoing notification.

## One time only (Started from Android 11)
Added in Android 11, this is the same as Allow only while using the app, but for a limited amount of time. For more information, see One-time permissions.


https://developer.android.com/about/versions/11/privacy/permissions#one-time

https://developer.android.com/training/permissions/requesting#one-time

Your app can then access the related data for a period of time that depends on your app's behavior and the user's actions:

1. While your app's activity is visible, your app can access the data.
2. If the user sends your app to the background, your app can continue to access the data for a short period of time.
3. If you launch a foreground service while the activity is visible, and the user then moves your app to the background, your app can continue to access the data until the foreground service stops.

`Note` - When the user next opens your app and a feature in your app requests access to location, microphone, or camera, the user is prompted for the permission again.


## Deny
This option prevents access to location information.

## Allow all the time (background updates)
This option allows location access all the time, but requires an extra permission for Android 10 and higher. You must also make sure you have a valid use case and comply with location policies. You won't cover this option in this codelab, as it's a rarer use case. However, if you have a valid use case and want to understand how to properly handle all-the-time location, including accessing location in the background, review the LocationUpdatesBackgroundKotlin sample.
