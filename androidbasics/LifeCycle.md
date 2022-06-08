
Lifecycles are managed by the operating system or the framework code running in your process. They are core to how Android works and your application must respect them.

##

`Lifecycle` is a class that holds the information about the lifecycle state of a component (like an activity or a fragment) and allows other objects to observe this state.

Think of the states as nodes of a graph and events as the edges between these nodes.

![lifecycle states](images/lifecycle-states.svg)

`Note`: on_pause, we don't say "Paused" state, but we say `STARTED` state. similarly on_stop, we don't say `STOPPED` state, instead we say `CREATED` state.

ON_CREATE, ON_START, ON_RESUME events in this class are dispatched after the LifecycleOwner's related method returns. ON_PAUSE, ON_STOP, ON_DESTROY events in this class are dispatched before the LifecycleOwner's related method is called. For instance, ON_START will be dispatched after onStart returns, ON_STOP will be dispatched before onStop is called. This gives you certain guarantees on which state the owner is in.

## Events

The lifecycle events that are dispatched from the framework and the Lifecycle class. These events map to the callback events in activities and fragments.


## States

The current state of the component tracked by the Lifecycle object.

## abstract class `LifeCycle`

Important methods are to `getCurrentState`, `add` and `remove` observers.

```java
    public abstract void addObserver(@NonNull LifecycleObserver observer);
    public abstract void removeObserver(@NonNull LifecycleObserver observer);
    public abstract State getCurrentState();
    public enum Event { ... }
    public enum State { ... }
```

## Interface LifeCycleObserver (empty marker interface)

```java
/**
 * Marks a class as a LifecycleObserver. Don't use this interface directly. Instead implement either
 * {@link DefaultLifecycleObserver} or {@link LifecycleEventObserver} to be notified about
 * lifecycle events.
 *
 * @see Lifecycle Lifecycle - for samples and usage patterns.
 */
@SuppressWarnings("WeakerAccess")
public interface LifecycleObserver {

}
```

## Interface LifecycleOwner (to get Lifecycle object)

LifecycleOwner is a single method interface that denotes that the class has a Lifecycle. (Examples are `Activity` and `Fragment`).

```java
public interface LifecycleOwner {
    /**
     * Returns the Lifecycle of the provider..
     */
    @NonNull
    Lifecycle getLifecycle();
}
```

This interface is implemented by `Activity` and `Fragment` and expose access to `Lifecycle` via `getLifecycle` method.


## Adding lifecycle observer on `Lifecycle` objects (to get notified of changes in lifecycle)

`LifecycleEventObserver` - Class that can receive any lifecycle change and dispatch it to the receiver via single method `onStateChanged`.
```java
public interface LifecycleEventObserver extends LifecycleObserver {
    /**
     * Called when a state transition event happens.
     *
     * @param source The source of the event
     * @param event The event
     */
    void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event);
}
```

WE would often use a higher level abstraction like `DefaultLifecycleObserver` e.g.
`DefaultLifeCycleObserver` - Callback interface for listening to LifecycleOwner state changes
callbacks inside `DefaultLifeCycleObserver`:
* `onCreate(LifeCycleOwner owner)`
* `onStart(LifeCycleOwner owner)`
* `onResume(LifeCycleOwner owner)`
* `onPause(LifeCycleOwner owner)`
* `onStop(LifeCycleOwner owner)`
* `onDestroy(LifeCycleOwner owner)`


**Note** - `DefaultLifecycleObserver extends FullLifecycleObserver extends LifeCycleObserver`

```java
public class MyObserver implements DefaultLifecycleObserver {
    @Override
    public void onResume(LifecycleOwner owner) {
        connect()
    }

    @Override
    public void onPause(LifecycleOwner owner) {
        disconnect()
    }
}

myLifecycleOwner.getLifecycle().addObserver(new MyObserver());
```

Components that implement `DefaultLifecycleObserver` work seamlessly with components that implement `LifecycleOwner` because an owner can provide a lifecycle, which an observer can register to watch.


## How are events mapped to callbacks being called?

```java
class FullLifecycleObserverAdapter implements LifecycleEventObserver {

    private final FullLifecycleObserver mFullLifecycleObserver;
    private final LifecycleEventObserver mLifecycleEventObserver;

    FullLifecycleObserverAdapter(FullLifecycleObserver fullLifecycleObserver,
            LifecycleEventObserver lifecycleEventObserver) {
        mFullLifecycleObserver = fullLifecycleObserver;
        mLifecycleEventObserver = lifecycleEventObserver;
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        switch (event) {
            case ON_CREATE:
                mFullLifecycleObserver.onCreate(source);
                break;
            case ON_START:
                mFullLifecycleObserver.onStart(source);
                break;
            case ON_RESUME:
                mFullLifecycleObserver.onResume(source);
                break;
            case ON_PAUSE:
                mFullLifecycleObserver.onPause(source);
                break;
            case ON_STOP:
                mFullLifecycleObserver.onStop(source);
                break;
            case ON_DESTROY:
                mFullLifecycleObserver.onDestroy(source);
                break;
            case ON_ANY:
                throw new IllegalArgumentException("ON_ANY must not been send by anybody");
        }
        if (mLifecycleEventObserver != null) {
            mLifecycleEventObserver.onStateChanged(source, event);
        }
    }
}
```

## LifeCycleRegistry

```java
/**
 * An implementation of {@link Lifecycle} that can handle multiple observers.
 * <p>
 * It is used by Fragments and Support Library Activities. You can also directly use it if you have
 * a custom LifecycleOwner.
 */
public class LifecycleRegistry extends Lifecycle {
        /**
     * The provider that owns this Lifecycle.
     * Only WeakReference on LifecycleOwner is kept, so if somebody leaks Lifecycle, they won't leak
     * the whole Fragment / Activity. However, to leak Lifecycle object isn't great idea neither,
     * because it keeps strong references on all other listeners, so you'll leak all of them as
     * well.
     */
    private final WeakReference<LifecycleOwner> mLifecycleOwner;
    /**
     * Custom list that keeps observers and can handle removals / additions during traversal.
     *
     * Invariant: at any moment of time for observer1 & observer2:
     * if addition_order(observer1) < addition_order(observer2), then
     * state(observer1) >= state(observer2),
     */
    private FastSafeIterableMap<LifecycleObserver, ObserverWithState> mObserverMap =
            new FastSafeIterableMap<>();
    /**
     * Current state
     */
    private State mState;
}
```
