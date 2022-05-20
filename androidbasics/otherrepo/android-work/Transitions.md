
### Interpolator

An `Interpolator` define how specific values in an animation are calculated as a function of time. For example, you can specify animations to happen linearly across the whole animation, meaning the animation moves evenly the entire time, or you can specify animations to use non-linear time, for example, using acceleration or deceleration at the beginning or end of the animation.

e.g. `LinearInterpolator`, `AccelerateDecelerateInterpolator` for easing, etc.

### KeyFrame

A `Keyframe` object consists of a `time/value pair` that lets you define a specific state at a specific time of an animation. Each keyframe can also have its own interpolator to control the behavior of the animation in the interval between the previous keyframe's time and the time of this keyframe.

e.g.
```java
Keyframe kf0 = Keyframe.ofFloat(0f, 0f);
Keyframe kf1 = Keyframe.ofFloat(.5f, 360f);
Keyframe kf2 = Keyframe.ofFloat(1f, 0f);
PropertyValuesHolder pvhRotation = PropertyValuesHolder.ofKeyframe("rotation", kf0, kf1, kf2);
ObjectAnimator rotationAnim = ObjectAnimator.ofPropertyValuesHolder(target, pvhRotation);
rotationAnim.setDuration(5000);
```

### Animator Interface

```
interface Animator{

    interface AnimatorListener{
        void onAnimationCancel(Animator anim);
        void onAnimationEnd(Animator anim);
        void onAnimationRepeat(Animator anim);
        void onAnimationStart(Animator anim);
    }

    void start();
    void resume();
    void cancel();
    void end();
    void pause();
    boolean isPaused/isRunning/isStarted();

    setTarget();
    setInterpolator();
    set/getDuration();

    void setupStartValues();
    void setupEndValues();

    void add/removeListener(Animator.AnimatorListener)
}
```

### ViewPropertyAnimator

Simple animations on a single view in a fire-and-forget model. Animations
cannot be repeated or cancelled.


### ValueAnimator

This class provides a simple timing engine for running animations which calculate animated values and set them on target objects.

ValueAnimator by itself is pure and only changes a value based on time,
To make actual use for value changes one must listen to a value animator.

```java
class ValueAnimator{
    addUpdateListener(ValueAnimator.AnimatorUpdateListener listener);
}
```

`ValueAnimator.AnimatorUpdateListener`: Implementors of this interface can add themselves as update listeners to an `ValueAnimator` instance to receive callbacks on every animation frame, after the current frame's values have been calculated for that `ValueAnimator`.

### ObjectAnimator

The `ObjectAnimator` is a subclass of the `ValueAnimator` (discussed in the previous section) and combines the timing engine and value computation of ValueAnimator with the ability to animate a named property of a target object. This makes animating any object much easier, as you no longer need to implement the `ValueAnimator.AnimatorUpdateListener`, because the animated property updates automatically.

Instantiating an `ObjectAnimator` is similar to a `ValueAnimator`, but you also specify the object and the name of that object's property (as a String) along with the values to animate between:
e.g.
```java
ObjectAnimator animation = ObjectAnimator.ofFloat(textView, "translationX", 100f);
animation.setDuration(1000);
animation.start();
```
**Note**: Requirements for `ObjectAnimator`:
1. `set<PropertyName>` method on object
2. `get<PropertyName>` method on object.

### PropertyValuesHolder 

This class holds information about a property and the values that that property should take on during an animation. `PropertyValuesHolder` objects can be used to create animations with `ValueAnimator` or `ObjectAnimator` that operate on several different properties in parallel.

The actual usage of this class comes in `ObjectAnimator.ofPropertyValuesHolder(View target, PropertyValuesHolder... pHs)`.

```java
        PropertyValuesHolder pvhBottom =
                PropertyValuesHolder.ofInt("bottom", 0, 1);
        PropertyValuesHolder pvhScaleX =
                PropertyValuesHolder.ofFloat("scaleX", 1f, 0f, 1f);
        Animator anim = ObjectAnimator.ofPropertyValuesHolder(view, pvhBottom, pvhScaleX);
```

### AnimatorSet

This class plays a set of Animator objects in the specified order. Animations can be set up to play together, in sequence, or after a specified delay.
Basically choreograph a set of animations.

### Transition (Added in API Level 19)

`Transition`: A Transition holds information about animations that will be run on its targets during a scene change.

Any Transition has two main jobs: 
1. capture property values, and
2. play animations based on changes to captured property values.

Basically think of `Transition` as factories that take in two different layouts and create animations/animators to go from first to second.

`Target`: Each view that the transition animates is called a `target`. You can only select targets that are part of the view hierarchy associated with a scene

To remove one or more views from the list of targets, call the `removeTarget()` method before starting the transition. To add only the views you specify to the list of targets, call the `addTarget()` function

For example, the `Fade` transition tracks changes to visibility-related properties and is able to construct and run animations that fade items in or out based on changes to those properties.

`ChangeBounds` transition: transition captures the layout bounds of target views before and after the scene change and animates those changes during the transition.

`ChangeTransform`: This Transition captures scale and rotation for Views before and after the scene change and animates those changes during the transition. A change in parent is handled as well by capturing the transforms from the parent before and after the scene change and animating those during the transition

```java
interface Transition {
    void captureStartValues(TransitionValues values);
    void captureEndValues(TransitionValues values);
    Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues);
    void add/removeTarget(View target);

    // internal methods
    createAnimators(); // creates all animators
    runAnimators();
    playTransition();// calls createAnimators followed by runAnimators. 
}
```

`ViewGroup.isTransitionGroup`:
Whether or not the `ViewGroup` should be treated as a unit in `Activity` transitions. If `false`, the `ViewGroup` won't transition, only its children. If true, the entire ViewGroup will transition together.

#### Transition without Scenes

Typically can happen with `addView()`, `removeView()`.

you can create and apply a transition between two states of a view hierarchy using a `delayed transition`. This feature of the transitions framework starts with the current view hierarchy state, records changes you make to its views, and applies a transition that animates the changes when the system redraws the user interface.



#### TransitionManager

Manages the set of transitions that fire when there is a change of Scene.

`beginDelayedTransition(ViewGroup sceneRoot, Transition transition)`: Animate all and any changes in the viewgroup.

`go(Scene scene, Transition transition)`: Convenience method to simply change to the given scene using the given transition

`setTransition(Scene scene, Transition transition)`: Sets a specific transition to occur when the given scene is entered.

`transitionTo(Scene scene)`: Change to the given scene, using the appropriate transition for this particular scene change (as specified to the TransitionManager, or the default if no such transition exists).



#### TransitionValues

```java

/**
 * Data structure which holds cached values for the transition.
 * The view field is the target which all of the values pertain to.
 * The values field is a map which holds information for fields
 * according to names selected by the transitions. These names should
 * be unique to avoid clobbering values stored by other transitions,
 * such as the convention project:transition_name:property_name. For
 * example, the platform might store a property "alpha" in a transition
 * "Fader" as "android:fader:alpha".
 *
 * <p>These values are cached during the
 * {@link androidx.transition.Transition#captureStartValues(TransitionValues)}
 * capture} phases of a scene change, once when the start values are captured
 * and again when the end values are captured. These start/end values are then
 * passed into the transitions via the
 * for {@link androidx.transition.Transition#createAnimator(android.view.ViewGroup,
 * TransitionValues, TransitionValues)} method.</p>
 */
public class TransitionValues {

    /**
     * The set of values tracked by transitions for this scene
     */
    public final Map<String, Object> values = new HashMap<>();

    /**
     * The View with these values
     */
    public View view;

    /**
     * The Transitions that targeted this view.
     */
    final ArrayList<Transition> mTargetedTransitions = new ArrayList<>();

    @Override
    public boolean equals(Object other) {
        if (other instanceof TransitionValues) {
            if (view == ((TransitionValues) other).view) {
                if (values.equals(((TransitionValues) other).values)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 31 * view.hashCode() + values.hashCode();
    }

    @Override
    public String toString() {
        String returnValue = "TransitionValues@" + Integer.toHexString(hashCode()) + ":\n";
        returnValue += "    view = " + view + "\n";
        returnValue += "    values:";
        for (String s : values.keySet()) {
            returnValue += "    " + s + ": " + values.get(s) + "\n";
        }
        return returnValue;
    }
}

```

`TransitionSet`: A collection of transitions with default ordering being : `ORDERING_TOGETHER`.

The `TransitionSet` class extends from the `Transition` class, so you can use it with a transition manager just like any other Transition instance.

```xml
<transitionSet xmlns:android="http://schemas.android.com/apk/res/android"
    android:transitionOrdering="sequential">
    <fade android:fadingMode="fade_out" />
    <changeBounds />
    <fade android:fadingMode="fade_in" />
</transitionSet>
```


### Touch Driven Animations

Useful for drag driven or finger following or scroll tracking animations.

Use `onTouchEvent` or `onTouch` to get touch values,
On top of that, in order to get velocity, use `VelocityTracker` class.

PUt `onScroll` listeners to track scroll values.

