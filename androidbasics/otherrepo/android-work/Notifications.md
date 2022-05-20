
**The user interface refers to notification channels as "categories."**

Starting in Android 7.0 (API level 24), you can display related notifications in a group (previously called "bundled" notifications).

Notification groups are not the same as notification channel groups.

Notice that the code on this page uses the `NotificationCompat` APIs from the Android support library. These APIs allow you to add features available only on newer versions of Android while still providing compatibility back to Android 4.0 (API level 14). However, some new features such as the inline reply action result in a no-op on older versions.

### Heads up notification

Above android Oreo, set channel importance to high.

Below android Oreo, set priority high and notification sound must also be set.

### Notifiation settings

"Push Notifications", in terms of FCM/GCM/etc, are a different concept from "App Notifications".

The "Notifications" screen in System Settings is only about what apps are, or are not, allowed to use NotificationManager to pop up notifications in the system tray. That's blocked at the NotificationManager level in the system process, and isn't communicated to the application. Push Notifications can be thought of as simply a specific form of network communication, and that is not tied to whether or not the app is allowed to display a notification in the tray.

### Notification behaviors

Notifications can take different forms:

1. A persistent icon that goes in the status bar and is accessible through the launcher, (when the user selects it, a designated Intent can be launched),
2. Turning on or flashing LEDs on the device, or
3. Alerting the user by flashing the backlight, playing a sound, or vibrating.

Each of the notify methods takes an int id parameter and optionally a String tag parameter, which may be null. These parameters are used to form a pair (tag, id), or (null, id) if tag is unspecified. This pair identifies this notification from your app to the system, so that pair should be unique within your app. If you call one of the notify methods with a (tag, id) pair that is currently active and a new set of notification parameters, it will be updated

### Creating a notification

Before you can deliver the notification on Android 8.0 and higher, you must register your app's notification channel with the system by passing an instance of `NotificationChannel` to `createNotificationChannel()`. (See later sections)

Notice that the `NotificationCompat.Builder` constructor requires that you provide a channel ID. This is required for compatibility with Android 8.0 (API level 26) and higher, but is ignored by older versions.

```java
NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
        .setSmallIcon(R.drawable.notification_icon)
        .setContentTitle(textTitle)
        .setContentText(textContent)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
```

you need to set the notification's content and channel using a `NotificationCompat.Builder` object.

If not using `NotificationCompat`, there is probably a method known as `setChannelId(channelId)`
on the notification builder object.

### Showing the notification

```java
NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
// notificationId is a unique int for each notification that you must define
notificationManager.notify(notificationId, builder.build());
```

### Updating an existing notification

To update this notification after you've issued it, call `NotificationManagerCompat.notify()` again, passing it a notification with the same ID you used previously. If the previous notification has been dismissed, a new notification is created instead.

### Notification tap action

Every notification should respond to a tap, usually to open an activity in your app that corresponds to the notification. To do so, you must specify a content intent defined with a `PendingIntent` object and pass it to `setContentIntent()`.

```java
// Create an explicit intent for an Activity in your app
Intent intent = new Intent(this, AlertDetails.class);
intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
        .setSmallIcon(R.drawable.notification_icon)
        .setContentTitle("My notification")
        .setContentText("Hello World!")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        // Set the intent that will fire when the user taps the notification
        .setContentIntent(pendingIntent)
        .setAutoCancel(true);
```

### Notification action buttons

**Note** - this is different than the tap action mentioned above, it means a seperate action button/UI.

A notification can offer up to three action buttons that allow the user to respond quickly, such as snooze a reminder or even reply to a text message. But these action buttons should not duplicate the action performed when the user taps the notification.

To add an action button, pass a `PendingIntent` to the `addAction()` method. This is just like setting up the notification's default tap action, except instead of launching an activity, you can do a variety of other things such as start a `BroadcastReceiver` that performs a job in the background so the action does not interrupt the app that's already open.

```java
Intent snoozeIntent = new Intent(this, MyBroadcastReceiver.class);
snoozeIntent.setAction(ACTION_SNOOZE);
snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, 0);
PendingIntent snoozePendingIntent =
        PendingIntent.getBroadcast(this, 0, snoozeIntent, 0);

NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
        .setSmallIcon(R.drawable.notification_icon)
        .setContentTitle("My notification")
        .setContentText("Hello World!")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setContentIntent(pendingIntent)
        .addAction(R.drawable.ic_snooze, getString(R.string.snooze),
                snoozePendingIntent);
```
### Notification Removal/dismissal

Notifications remain visible until one of the following happens:

1. The user dismisses the notification.
2. The user clicks the notification, and you called setAutoCancel() when you created the notification.
3. You call `cancel()` for a specific notification ID. This method also deletes ongoing notifications.
4. You call `cancelAll()`, which removes all of the notifications you previously issued.
5. If you set a timeout when creating a notification using setTimeoutAfter(), the system cancels the notification after the specified duration elapses. If required, you can cancel a notification before the specified timeout duration elapses.

### Notification Group

You should use notification groups if all of the following conditions are true for your use case:

The child notifications are complete notifications and can be displayed individually without the need for a group summary.
1. There is a benefit to surfacing the child notifications individually. For example:
2. They are actionable, with actions specific to each notification.
3. There is more information in each notification that the user should see.

### Notification importance

Android uses the importance of a notification to determine how much the notification should interrupt the user (visually and audibly). The higher the importance of a notification, the more interruptive the notification will be.

* On Android 8.0 (API level 26) and above, importance of a notification is determined by the importance of the channel the notification was posted to. Users can change the importance of a notification channel in the system settings (figure 12). 

* On Android 7.1 (API level 25) and below, importance of each notification is determined by the notification's priority.

The possible importance levels are the following:

1. Urgent: Makes a sound and appears as a heads-up notification.
2. High: Makes a sound.
3. Medium: No sound.
4. Low: No sound and does not appear in the status bar.


### Notification Channel

On devices running Android 7.1 (API level 25) and lower, users can manage notifications on a per-app basis only (effectively each app only has one channel on Android 7.1 and lower).


The user interface refers to notification channels as "categories."

For each channel, you can set the visual and auditory behavior that is applied to all notifications in that channel. Then, users can change these settings and decide which notification channels from your app should be intrusive or visible at all.

After you create a notification channel, you cannot change the notification behaviors—the user has complete control at that point. Though you can still change a channel's name and description.

#### Creating a notification channel

To create a notification channel, follow these steps:

1. Construct a `NotificationChannel` object with a unique `channel ID`, a user-visible `name`, and an `importance level`.
2. Optionally, specify the description that the user sees in the system settings with `setDescription()`.
3. Register the notification channel by passing it to `createNotificationChannel()`.

e.g.
```java
private void createNotificationChannel() {
    // Create the NotificationChannel, but only on API 26+ because
    // the NotificationChannel class is new and not in the support library
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        CharSequence name = getString(R.string.channel_name);
        String description = getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}
```
### Notification Channel Group

This is a good idea when your app supports multiple user accounts (such as for work profiles), so you can create a notification channel group for each account.

### Notification Category

Android uses a some pre-defined system-wide categories to determine whether to disturb the user with a given notification when the user has enabled Do Not Disturb mode.

If your notification falls into one of the pre-defined notification categories defined in `NotificationCompat`—such as `CATEGORY_ALARM`, `CATEGORY_REMINDER`, `CATEGORY_EVENT`, or `CATEGORY_CALL`—you should declare it as such by passing the appropriate category to `setCategory()`.
```java
NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
        .setSmallIcon(R.drawable.notification_icon)
        .setContentTitle("My notification")
        .setContentText("Hello World!")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setCategory(NotificationCompat.CATEGORY_MESSAGE);
```

### Notification lock screen visibility

To control the level of detail visible in the notification from the lock screen, call `setVisibility()` and specify one of the following values:

1. `VISIBILITY_PUBLIC` shows the notification's full content.
2. `VISIBILITY_SECRET` doesn't show any part of this notification on the lock screen.
3. `VISIBILITY_PRIVATE` shows basic information, such as the notification's icon and the content title, but hides the notification's full content.

### DND (Do not disturb) mode

Starting in Android 5.0 (API level 21), users can enable Do Not Disturb mode, which silences sounds and vibration for all notifications. Notifications still appear in the system UI as normal, unless the user specifies otherwise.

There are three different levels available in Do Not Disturb mode:

1. Total silence: blocks all sounds and vibrations, including from alarms, music, videos, and games.
2. Alarms only: blocks all sounds and vibrations, except from alarms.
3. Priority only: users can configure which system-wide categories can interrupt them (such as only alarms, reminders, events, calls, or messages). For messages and calls, users can also choose to filter based on who the sender or caller is (figure 13).

On Android 8.0 (API level 26) and above, users can additionally allow notifications through for app-specific categories (also known as channels) by overriding Do Not Disturb on a channel-by-channel basis (E.g. see PagerDuty app)

On devices running Android 7.1 (API level 25) and below, users can allow notifications through on an app by app basis, rather than on a channel by channel basis.
