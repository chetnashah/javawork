

## TAsk

A task is a stack of activities.
When all activities are removed from the stack, the task no longer exists.
The back stack always has the start destination of the app at the bottom of the stack.
Task uses back stack to manage activity stack.
**Up and back are identical within your app's task**

### Up and back are identical within your app's task

**Up button never exits your app**. up button is usually considered for in-app backstack popping. and in many cases will be missing in the home activity/start destination of the app.

**Back takes you where you were before, where as up takes you to afixed place, no matter where you came from.**

## Home behavior

if you tap Home on the device and the app was in focus, the whole task for the app is put into the background

When a user touches the icon for an app or shortcut in the app launcher (or on the Home screen), that app's task comes to the foreground. 

If no task exists for the app (the app has not been used recently), then a new task is created and the main activity for that app opens as the root activity in the stack.


## Recents behavior

The Recents screen (also referred to as the Overview screen, recent task list, or recent apps) is a system-level UI that lists recently accessed activities and tasks. 

The user can navigate through the list and select a task to resume, or the user can remove a task from the list by swiping it away

## MainActivity/start destinations

The start destination for any app.
An app might have a one-time setup or series of login screens. These `conditional screens should not be considered start destinations` because users see these screens only in certain cases.

The back stack always has the start destination of the app at the bottom of the stack.



