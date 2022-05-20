
### Core App quality

- Back button should work as expected. Never override behavior of Home button.
- Stack multiple notifications into single when possible.
- All dialogs should be dismissible using back button.
- Notifications should only contain relevant info, no ads etc.
- NOtifications to be used only when some context related to user that user cares about has changed, e.g. incoming message or exposing controls to a background service e.g. music.

- Min set of permissions.
- App functions properly when installed on SD card. Normal for 100MB+ apps.(https://developer.android.com/guide/topics/data/install-location.html)

- Audio does not play when the screen is off/ screen is locked/ screen is home screen, unless this is a core feature (for example, the app is a music player).

- Audio resumes when the app returns to the foreground, or indicates to the user that playback is in a paused state.

- Supports both orientations. Minor changes in content or views are acceptable.

- The app correctly handles rapid transitions between display orientations without rendering problems.

- The app should not leave any services running when the app is in the background, unless related to a core capability of the app.

- When the app is resumed from the Recents app switcher, the app returns the user to the exact state in which it was last used.

- When the app is resumed after the device wakes from sleep (locked) state, the app returns the user to the exact state in which it was last used.

- When the app is relaunched from Home or All Apps, the app restores the app state as closely as possible to the previous state.

- On Back keypresses (app is killed), so the app gives the user the option of saving any app or user state that would otherwise be lost on back-navigation

#### Performance

- The app loads quickly or provides onscreen feedback to the user (a progress indicator or similar cue) if the app takes longer than two seconds to load.

- no red flashes (performance warnings from **StrictMode**) are visible when exercising the app, including during game play, animations and UI transitions, and any other part of the app.	

### JOb Scheduler and Work Manager

WorkManager uses `jobscheduler` under the hood, and backports wherever it is not available.
WorkManager just seems like Google's answer to Evernote's Android-Job library, but with a few improvements. It uses JobScheduler, Firebase JobDispatcher and AlarmManager just like Android-Job depending on the device's API level.


### Building for billions

