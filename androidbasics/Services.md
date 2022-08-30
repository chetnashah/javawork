https://www.youtube.com/watch?v=gxj4sQX9m5g

https://guides.codepath.com/android/managing-threads-and-custom-services

**note** - Only `activities`, `services`, and `content providers` can bind to a service—you can't bind to a service from a broadcast receiver.

Thread management is important to understand because a custom service still runs in your application's main thread by default. If you create a custom Service, then you will still need to manage the background threads manually.

## Choosing between service and thread

A service is simply a component that can run in the background, even when the user is not interacting with your application, so you should create a service only if that is what you need.

If you must perform work outside of your main thread, but only while the user is interacting with your application, you should instead create a new thread.

Remember that if you do use a service, it still runs in your application's main thread by default, so you should still create a new thread within the service if it performs intensive or blocking operations.

## When to bind/unbind to a service?

You usually pair the binding and unbinding during matching bring-up and tear-down moments of the client's lifecycle, as described in the following examples:

If you need to interact with the service only while your activity is visible, you should bind during `onStart()` and unbind during `onStop()`.

If you want your activity to receive responses even while it is stopped in the background, then you can bind during `onCreate()` and unbind during `onDestroy()`. Beware that this implies that your activity needs to use the service the entire time it's running (even in the background), so if the service is in another process, then you increase the weight of the process and it becomes more likely that the system will kill it.

Note: You don't usually bind and unbind during your activity's onResume() and onPause(), because these callbacks occur at every lifecycle transition and you should keep the processing that occurs at these transitions to a minimum. Also, if multiple activities in your application bind to the same service and there is a transition between two of those activities, the service may be destroyed and recreated as the current activity unbinds (during pause) before the next one binds (during resume). This activity transition for how activities coordinate their lifecycles is described in the Activities document.

## How do we know if a service is started service?

Usually callers will call it with `startService()` and it will have majority implementation inside `onStartCommand()` instead of `onBInd()`, and `the implementation of onBind() returns null`.

e.g. https://cs.android.com/android/platform/superproject/+/master:packages/apps/Calendar/src/com/android/calendar/alerts/AlertService.kt?q=AlertService&ss=android%2Fplatform%2Fsuperproject

Started service will usually have command processor pattern, where command is provided using startService(intent), one may or maynote stop service after processing the command, depending on implementation (e.g. Intentservice is an example that stops self after processing command).



