
Dont use platform version, only use `androidx` version.

They implement `LifecycleOwner` and `ViewModelStoreOwner`.

## Fragments

A fragment is a reusable piece of UI; fragments can be reused and embedded in one or more activities.
Basically a peice of UI with some controller logic.
`UI controllers like fragment` control the UI by drawing views on the screen, capturing user events, and anything else related to the UI that the user interacts with. 
Data in the app or any decision-making logic about that data should not be in the UI controller classes

The Android system can destroy UI controllers like fragments at any time based on certain user interactions or because of system conditions like low memory. 
Because these events aren't under your control, you shouldn't store any app data or state in UI controllers e.g. lets say shared state between many fragments.

Fragments introduce modularity and reusability into your activity’s UI by allowing you to divide the UI into discrete chunks.

You should avoid depending on or manipulating one fragment from another.


**You can even show multiple fragments at once on a single screen, such as a master-detail layout for tablet devices**

## Implemented interfaces:
`LifecycleOwner`, `ViewModelStoreOwner`

## Using from androidx

```groovy
    // Java language implementation
    implementation("androidx.fragment:fragment:$fragment_version")
    // Kotlin
    implementation("androidx.fragment:fragment-ktx:$fragment_version")
```

## Fragment instantiation/constructor stacktrace

```
<init>:28, ListFragment (com.example.samplememorynotes.presentation)
newInstance0:-1, Constructor (java.lang.reflect)
newInstance:343, Constructor (java.lang.reflect)
instantiate:615, Fragment (androidx.fragment.app)
instantiate:57, FragmentContainer (androidx.fragment.app)
instantiate:448, FragmentManager$2 (androidx.fragment.app)
navigate:190, FragmentNavigator (androidx.navigation.fragment)
navigate:162, FragmentNavigator (androidx.navigation.fragment)
navigate:83, NavGraphNavigator (androidx.navigation)
navigate:49, NavGraphNavigator (androidx.navigation)
navigateInternal:261, NavController (androidx.navigation)
navigate:1715, NavController (androidx.navigation)
onGraphCreated:1158, NavController (androidx.navigation)
setGraph:1086, NavController (androidx.navigation)
setGraph:1039, NavController (androidx.navigation)
onCreate:155, NavHostFragment (androidx.navigation.fragment)
performCreate:2981, Fragment (androidx.fragment.app)
create:474, FragmentStateManager (androidx.fragment.app)
moveToExpectedState:257, FragmentStateManager (androidx.fragment.app)
executeOpsTogether:1840, FragmentManager (androidx.fragment.app)
removeRedundantOperationsAndExecute:1764, FragmentManager (androidx.fragment.app)
execSingleAction:1670, FragmentManager (androidx.fragment.app)
commitNowAllowingStateLoss:323, BackStackRecord (androidx.fragment.app)
<init>:158, FragmentContainerView (androidx.fragment.app)
onCreateView:53, FragmentLayoutInflaterFactory (androidx.fragment.app)
onCreateView:135, FragmentController (androidx.fragment.app)
dispatchFragmentsOnCreateView:295, FragmentActivity (androidx.fragment.app)
onCreateView:274, FragmentActivity (androidx.fragment.app)
tryCreateView:1073, LayoutInflater (android.view)
createViewFromTag:1001, LayoutInflater (android.view)
createViewFromTag:965, LayoutInflater (android.view)
rInflate:1127, LayoutInflater (android.view)
rInflateChildren:1088, LayoutInflater (android.view)
inflate:686, LayoutInflater (android.view)
inflate:538, LayoutInflater (android.view)
inflate:485, LayoutInflater (android.view)
setContentView:706, AppCompatDelegateImpl (androidx.appcompat.app)
setContentView:195, AppCompatActivity (androidx.appcompat.app)
onCreate:9, MainActivity (com.example.samplememorynotes)
performCreate:8051, Activity (android.app)
performCreate:8031, Activity (android.app)
callActivityOnCreate:1329, Instrumentation (android.app)
performLaunchActivity:3608, ActivityThread (android.app)
handleLaunchActivity:3792, ActivityThread (android.app)
execute:103, LaunchActivityItem (android.app.servertransaction)
executeCallbacks:135, TransactionExecutor (android.app.servertransaction)
execute:95, TransactionExecutor (android.app.servertransaction)
handleMessage:2210, ActivityThread$H (android.app)
dispatchMessage:106, Handler (android.os)
loopOnce:201, Looper (android.os)
loop:288, Looper (android.os)
main:7839, ActivityThread (android.app)
invoke:-1, Method (java.lang.reflect)
run:548, RuntimeInit$MethodAndArgsCaller (com.android.internal.os)
main:1003, ZygoteInit (com.android.internal.os)
```

The method inside Fragment class for instantiation looks like this:
```java
    @NonNull
    public static Fragment instantiate(@NonNull Context context, @NonNull String fname,
            @Nullable Bundle args) {
        try {
            Class<? extends Fragment> clazz = FragmentFactory.loadFragmentClass(
                    context.getClassLoader(), fname);
            Fragment f = clazz.getConstructor().newInstance();
            if (args != null) {
                args.setClassLoader(f.getClass().getClassLoader());
                f.setArguments(args);
            }
            return f;
        } catch (java.lang.InstantiationException e) {
            throw new InstantiationException("Unable to instantiate fragment " + fname
                    + ": make sure class name exists, is public, and has an"
                    + " empty constructor that is public", e);
        } catch (IllegalAccessException e) {
            throw new InstantiationException("Unable to instantiate fragment " + fname
                    + ": make sure class name exists, is public, and has an"
                    + " empty constructor that is public", e);
        } catch (NoSuchMethodException e) {
            throw new InstantiationException("Unable to instantiate fragment " + fname
                    + ": could not find Fragment constructor", e);
        } catch (InvocationTargetException e) {
            throw new InstantiationException("Unable to instantiate fragment " + fname
                    + ": calling Fragment constructor caused an exception", e);
        }
    }
```


## Where would I need to instatiate/create new fragment in application code?

inside `FragmentPagerAdapter.getItem()`


## newInstance convention to create fragment with arguments

```java
MyFragment newInstance(Long someVideoId) { // construct fragment and set given arguments
    MyFragment myFragment = new MyFragment();
    Bundle bundle = new Bundle();
    bundle.setArguments("id", someId);
    myFragment.setArguments(bundle)
    return myFragment
}
```

When a fragment gets recreated, you can access these arguments in `onCreate` via
`Long videoId = getArguments().getLong("id")`

`setArguments`: Supply the construction arguments for this fragment. The arguments supplied here will be retained across fragment destroy and creation.

## lifecycle summary

To manage lifecycle, `Fragment` implements `LifecycleOwner`, exposing a `Lifecycle` object that you can access through the `getLifecycle()` method.

**A fragment's view has a separate Lifecycle that is managed independently from that of the fragment's Lifecycle.**.
Fragments maintain a separate LifecycleOwner for their view, which can be accessed using `getViewLifecycleOwner()`.

A fragment's lifecycle state can never be greater than its parent. For example, a parent fragment or activity must be started before its child fragments. Likewise, child fragments must be stopped before their parent fragment or activity.

![fragment lifecycle](images/fragmentlifecycle.png)

`onCreate()`: The fragment has been instantiated and is in the CREATED state. However, its corresponding view has not been created yet. you can not rely on things like the activity's content view hierarchy being initialized at this point.
`onCreateView()`: This method is where you inflate the layout. The fragment has entered the CREATED state.
`onViewCreated()`: This is called after the view is created. In this method, you would typically bind specific views to properties by calling findViewById(). This is where you can bind views and setup view stuff like layoutadapters, api calls, click listeners.
`onStart()`: The fragment has entered the STARTED state.
`onResume()`: The fragment has entered the RESUMED state and now has focus (can respond to user input).
`onPause()`: The fragment has re-entered the STARTED state. The UI is visible to the user
`onStop()`: The fragment has re-entered the CREATED state. The object is instantiated but is no longer presented on screen.
`onDestroyView()`: Called right before the fragment enters the DESTROYED state. The view has already been removed from memory, but the fragment object still exists.
`onDestroy()`: The fragment enters the DESTROYED state.


## Fragment onCreate stacktrace

```
onCreate:33, ListFragment (com.example.samplememorynotes.presentation)
performCreate:2981, Fragment (androidx.fragment.app)
create:474, FragmentStateManager (androidx.fragment.app)
moveToExpectedState:257, FragmentStateManager (androidx.fragment.app)
executeOpsTogether:1840, FragmentManager (androidx.fragment.app)
removeRedundantOperationsAndExecute:1764, FragmentManager (androidx.fragment.app)
execPendingActions:1701, FragmentManager (androidx.fragment.app)
dispatchStateChange:2849, FragmentManager (androidx.fragment.app)
dispatchCreate:2773, FragmentManager (androidx.fragment.app)
onCreate:1913, Fragment (androidx.fragment.app)
onCreate:169, NavHostFragment (androidx.navigation.fragment)
performCreate:2981, Fragment (androidx.fragment.app)
create:474, FragmentStateManager (androidx.fragment.app)
moveToExpectedState:257, FragmentStateManager (androidx.fragment.app)
executeOpsTogether:1840, FragmentManager (androidx.fragment.app)
removeRedundantOperationsAndExecute:1764, FragmentManager (androidx.fragment.app)
execSingleAction:1670, FragmentManager (androidx.fragment.app)
commitNowAllowingStateLoss:323, BackStackRecord (androidx.fragment.app)
<init>:158, FragmentContainerView (androidx.fragment.app)
onCreateView:53, FragmentLayoutInflaterFactory (androidx.fragment.app)
onCreateView:135, FragmentController (androidx.fragment.app)
dispatchFragmentsOnCreateView:295, FragmentActivity (androidx.fragment.app)
onCreateView:274, FragmentActivity (androidx.fragment.app)
tryCreateView:1073, LayoutInflater (android.view)
createViewFromTag:1001, LayoutInflater (android.view)
createViewFromTag:965, LayoutInflater (android.view)
rInflate:1127, LayoutInflater (android.view)
rInflateChildren:1088, LayoutInflater (android.view)
inflate:686, LayoutInflater (android.view)
inflate:538, LayoutInflater (android.view)
inflate:485, LayoutInflater (android.view)
setContentView:706, AppCompatDelegateImpl (androidx.appcompat.app)
setContentView:195, AppCompatActivity (androidx.appcompat.app)
onCreate:9, MainActivity (com.example.samplememorynotes)
performCreate:8051, Activity (android.app)
performCreate:8031, Activity (android.app)
callActivityOnCreate:1329, Instrumentation (android.app)
performLaunchActivity:3608, ActivityThread (android.app)
handleLaunchActivity:3792, ActivityThread (android.app)
execute:103, LaunchActivityItem (android.app.servertransaction)
executeCallbacks:135, TransactionExecutor (android.app.servertransaction)
execute:95, TransactionExecutor (android.app.servertransaction)
handleMessage:2210, ActivityThread$H (android.app)
dispatchMessage:106, Handler (android.os)
loopOnce:201, Looper (android.os)
loop:288, Looper (android.os)
main:7839, ActivityThread (android.app)
invoke:-1, Method (java.lang.reflect)
run:548, RuntimeInit$MethodAndArgsCaller (com.android.internal.os)
main:1003, ZygoteInit (com.android.internal.os)
```

## Declaring fragments in XML only

```xml
    <fragment
        android:id="@+id/title_destination"
        android:name="com.example.android.guesstheword.screens.title.TitleFragment"
        android:label="title_fragment"
        tools:layout="@layout/title_fragment">
    </fragment>
```
and corresponding `TitleFragment` class:
```kt
class TitleFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val binding: TitleFragmentBinding = DataBindingUtil.inflate(
                inflater, R.layout.title_fragment, container, false)

        binding.playGameButton.setOnClickListener {
            findNavController().navigate(TitleFragmentDirections.actionTitleToGame())
        }
        return binding.root
    }
}
```

`Note`: prefer `XYZViewBinding/Databinding` inflater given from XML generated Viewbinding class e.g.
```kt
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // use the xml binding auto generated class inflater
        val wordListBinding: FragmentWordListBinding =  FragmentWordListBinding.inflate(inflater, container, false)
        return wordListBinding.root
    }
```

## onInflate

Called when a fragment is being created as part of a view layout inflation, typically from setting the content view of an activity. This may be called immediately after the fragment is created from a `FragmentContainerView` in a layout file. Note this is before the fragment's onAttach has been called; all you should do here is parse the attributes and save them away.

This is called the first time the fragment is inflated. If it is being inflated into a new instance with saved state, this method will not be called a second time for the restored state fragment.

## onCreateView

`onCreateView(LayoutInflater, ViewGroup, Bundle)` creates and returns the view hierarchy associated with the fragment.

## Creating fragments

```kotlin
class ExampleFragment : Fragment(R.layout.example_fragment)
```

## Adding fragment to activity via XML

To declaratively add a fragment to your activity layout's XML, use a `FragmentContainerView` element.

```xml
<!-- res/layout/example_activity.xml -->
<androidx.fragment.app.FragmentContainerView
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/fragment_container_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:name="com.example.ExampleFragment" />
```
The `android:name` attribute specifies the class name of the Fragment to instantiate. 

When the activity's layout is inflated, the specified fragment is instantiated, `onInflate()` is called on the newly instantiated fragment, and a FragmentTransaction is created to add the fragment to the FragmentManager.

**Note** - Avoid using the `<fragment>` tag to add a fragment using XML, as the `<fragment>` tag allows a fragment to move beyond the state of its FragmentManager. Instead, always use FragmentContainerView for adding a fragment using XML.


## Adding fragment to activity programmatically

To programmatically add a fragment to your activity's layout, the layout should include a `FragmentContainerView` to serve as a fragment container, as shown in the following example:

```xml
<!-- res/layout/example_activity.xml -->
<androidx.fragment.app.FragmentContainerView
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/fragment_container_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

a `FragmentTransaction` is used to instantiate a fragment and add it to the activity's layout.

In your `FragmentActivity`, you can get an instance of the `FragmentManager`, which can be used to create a `FragmentTransaction`.

## Fragment manager and transactions

At runtime, a `FragmentManager` can add, remove, replace, and perform other actions with fragments in response to user interaction. 

Each set of fragment changes that you commit is called a transaction, and you can specify what to do inside the transaction using the APIs provided by the `FragmentTransaction` class.

 You can group multiple actions into a single transaction—for example, a transaction can add or replace multiple fragments. This grouping can be useful for when you have multiple sibling fragments displayed on the same screen, such as with split views.

## Sharing data between fragments

You can have a shared viewmodel on activity scope, which can be shared between all the fragments inside that activity.
The shared viewmodel would be tied to activity scope instead of fragment scope.

```kt
class StartFragment : Fragment() {
    // Binding object instance corresponding to the fragment_start.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment.
    private var binding: FragmentStartBinding? = null

    private val sharedViewModel: OrderViewModel by activityViewModels()
```

## FragmentFactory

https://proandroiddev.com/android-fragments-fragmentfactory-ceec3cf7c959

class used to control the instantiation of Fragment instances.

Traditionally, a Fragment instance could only be instantiated using its default empty constructor. This is because the system would need to reinitialize it under certain circumstances like configuration changes and the app’s process recreation. If it weren’t for the default constructor restriction, the system wouldn’t know how to reinitialize the Fragment instance.

If your Fragment has a non-empty constructor, you will need to create a FragmentFactory that will handle initializing it.

Fragments are managed by FragmentManagers, so it’s only natural that the `FragmentFactory needs to be attached to a FragmentManager in order to be used`

To provide dependencies to your fragment, or to use any custom constructor, you must instead create a custom FragmentFactory subclass and then override FragmentFactory.instantiate. You can then override the FragmentManager's default factory with your custom factory, which is then used to instantiate your fragments.

E.g. DessertsFragment depends on DessertsRepository
```java
public class DessertsFragment extends Fragment {
    private DessertsRepository dessertsRepository;

    public DessertsFragment(DessertsRepository dessertsRepository) {
        super();
        this.dessertsRepository = dessertsRepository;
    }

    // Getter omitted.

    ...
}
```

Fragment factory for injecting fragment dependencies

```java
public class MyFragmentFactory extends FragmentFactory {
    private DessertsRepository repository;

    public MyFragmentFactory(DessertsRepository repository) {
        super();
        this.repository = repository;
    }

    // note custom implementation
    @NonNull
    @Override
    public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String className) {
        Class<? extends Fragment> fragmentClass = loadFragmentClass(classLoader, className);
        if (fragmentClass == DessertsFragment.class) {
            return new DessertsFragment(repository);
        } else {
            return super.instantiate(classLoader, className);
        }
    }
}
```

Registering custom fragmentfactory:
```java
public class MealActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        DessertsRepository repository = DessertsRepository.getInstance();
        getSupportFragmentManager().setFragmentFactory(new MyFragmentFactory(repository));
        super.onCreate(savedInstanceState);
    }
}
```

## `viewModels()` extension on `Fragment` class

This is one more way to get viewmodel in a fragment, other than using `ViewModelProvider(fragInstance).get(ModelClass)`.
Returns a Lazy ViewModel, to which delegation can happen.

```kt
public inline fun <reified VM : ViewModel> Fragment.viewModels(
    noinline ownerProducer: () -> ViewModelStoreOwner = { this },
    noinline factoryProducer: (() -> Factory)? = null
): Lazy<VM> = createViewModelLazy(VM::class, { ownerProducer().viewModelStore }, factoryProducer)
```

Why use it?
We want to own instantiation of the ViewModel, instead delegate it to `Fragments.viewModels` extension:
The delegate class creates the viewModel object for you on the first access, and retains its value through configuration changes and returns the value when requested.

### Can't access ViewModels from detached fragment

If I try to access the lazy ViewModel returned from `viewModels` extension, before it is the fragment is attached,
I will get following error:
```
    init {
        Log.d("Gamefragment", "fragment init viewmodel = "+viewModel)
    }

        Caused by: java.lang.IllegalStateException: Can't access ViewModels from detached fragment
        at androidx.fragment.app.Fragment.getDefaultViewModelProviderFactory(Fragment.java:427)
        at androidx.fragment.app.FragmentViewModelLazyKt$createViewModelLazy$factoryPromise$1.invoke(FragmentViewModelLazy.kt:95)
        at androidx.fragment.app.FragmentViewModelLazyKt$createViewModelLazy$factoryPromise$1.invoke(Unknown Source:0)
        at androidx.lifecycle.ViewModelLazy.getValue(ViewModelProvider.kt:52)
        at androidx.lifecycle.ViewModelLazy.getValue(ViewModelProvider.kt:41)
        at com.example.android.unscramble.ui.game.GameFragment.getViewModel(GameFragment.kt:38)
        at com.example.android.unscramble.ui.game.GameFragment.<init>(GameFragment.kt:41)
```

### lazy Viewmodel can be accessed in `onAttach` and `onCreate`

```kt
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("GameFragment", "onAttach callback, viewmodel = " + viewModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("GameFragment", "onCreate callback, viewmodel = " + viewModel)
    }

 D/GameFragment: onAttach callback, viewmodel = com.example.android.unscramble.ui.game.GameViewModel@a42443d
 D/GameFragment: onCreate callback, viewmodel = com.example.android.unscramble.ui.game.GameViewModel@a42443d
```

## Destroy vs Detach

when a Fragment is detached, its `onPause`, `onStop` and `onDestroyView` methods are called only (in that order). 

On the other hand, when a Fragment is removed, its `onPause`, `onStop`, `onDestroyView`, `onDestroy` and `onDetach` methods are called (in that order). 

Similarly, when attaching, the Fragment's `onCreateView`, `onStart` and `onResume` methods are called only; 
and when adding, the Fragment's `onAttach`, `onCreate`, `onCreateView`, `onStart` and `onResume` methods are called (in that order).

So attaching/detaching does not call onCreate/onDestroy and onAttach/onDetach
But add/remove adds calls like onCreate/onDestroy and onAttach/onDetach.

## Why onAttach is called before onCreate?

https://stackoverflow.com/questions/29677812/why-is-onattach-called-before-oncreate

`onAttach()` assigns hosting activity to the Fragment. If it had been called after `onCreate()` then there would be no context for your fragment (`getActivity()` would return null) and you would not be able to do anything in `onCreate()` method without that context anyway.

Another fitting reason is that Fragment's lifecycle is similar to Activity's lifecycle. In `Activity.attach()` activity gets attached to its parent (a window). Similarly in `Fragment.onAttach()` fragment gets attached to its parent (an activity), before any other initialization is done. Note, `Activity.attach()` happens before `Activity.onCreate`, but `onAttachedToWindow` callback is called from `performTraversals` which is late.



## Fragment and viewmodel init order log

Viewmodel init happens after fragment -> init -> attach -> onCreate.
```
2022-06-05 17:44:30.513 11388-11388/com.example.android.unscramble D/GameFragment: init!
2022-06-05 17:44:30.517 11388-11388/com.example.android.unscramble D/GameFragment: onAttach
2022-06-05 17:44:30.518 11388-11388/com.example.android.unscramble D/GameFragment: onCreate
2022-06-05 17:44:30.519 11388-11388/com.example.android.unscramble D/MainActivity: onCreate
2022-06-05 17:44:30.545 11388-11388/com.example.android.unscramble D/GameViewModel: init
2022-06-05 17:44:30.546 11388-11388/com.example.android.unscramble D/GameFragment: GameFragment View created/re-created!
2022-06-05 17:44:30.569 11388-11388/com.example.android.unscramble D/MainActivity: onAttachedToWindow
```

## What is a fragment host?

An example of a fragment host is `Activity`, There can be other classes that can host fragments.

It is the responsibility of the host to take care of the Fragment's lifecycle. The methods provided by `FragmentController` are for that purpose.

### FragmentController

An object of this class is used by fragment hosting objects like `Activity` to forward all event/methods and also get encapsulates FragmentManager (`via mHost i.e. FragmentHostCallback class instance also defined in FragmentActivity`).

https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:fragment/fragment/src/main/java/androidx/fragment/app/FragmentActivity.java;l=89

```java
public class FragmentController {
    private final FragmentHostCallback<?> mHost;

    /**
     * Returns a {@link FragmentController}.
     */
    @NonNull
    public static FragmentController createController(@NonNull FragmentHostCallback<?> callbacks) {
        return new FragmentController(checkNotNull(callbacks, "callbacks == null"));
    }

    private FragmentController(FragmentHostCallback<?> callbacks) {
        mHost = callbacks;
    }

    /**
     * Returns a {@link FragmentManager} for this controller.
     */
    @NonNull
    public FragmentManager getSupportFragmentManager() {
        return mHost.mFragmentManager;
    }

    //...
```

### FragmentContainer

```java
/**
 * Callbacks to a {@link Fragment}'s container.
 */
public abstract class FragmentContainer {
    /**
     * Return the view with the given resource ID. May return {@code null} if the
     * view is not a child of this container.
     */
    @Nullable
    public abstract View onFindViewById(@IdRes int id);

    /**
     * Return {@code true} if the container holds any view.
     */
    public abstract boolean onHasView();

    /**
     * Creates an instance of the specified fragment, can be overridden to construct fragments
     * with dependencies, or change the fragment being constructed. By default just calls
     * {@link Fragment#instantiate(Context, String, Bundle)}.
     * @deprecated Use {@link FragmentManager#setFragmentFactory} to control how Fragments are
     * instantiated.
     */
    @SuppressWarnings("deprecation")
    @Deprecated
    @NonNull
    public Fragment instantiate(@NonNull Context context, @NonNull String className,
            @Nullable Bundle arguments) {
        return Fragment.instantiate(context, className, arguments);
    }
}
```

### public abstract `class FragmentHostCallback<E> extends FragmentContainer`

Integration points with the Fragment host.

Fragments may be hosted by any object; such as an Activity. 
In order to host fragments, implement `FragmentHostCallback`, overriding the methods applicable to the host.


**Holds a final reference to FragmentManager**:
```java
public abstract class FragmentHostCallback<E> extends FragmentContainer {
    @Nullable private final Activity mActivity;
    @NonNull private final Context mContext;
    @NonNull private final Handler mHandler;
    private final int mWindowAnimations;
    final FragmentManager mFragmentManager = new FragmentManagerImpl();
```

## FragmentManager vs FragmentStateManager

The `FragmentManager` only has state that applies to all fragments, FragmentManager is the class responsible for performing actions on your app's fragments, such as adding, removing, or replacing them, and adding them to the back stack. Each set of changes are committed together as a single unit called a `FragmentTransaction`.

The `FragmentStateManager` manages the state at the individual fragment level.

![Fragment managers](images/fragmentmanagersref.png)

### FragmentManager holds reference to `FragmentStore mFragmentStore` reference

```java
    private final FragmentStore mFragmentStore = new FragmentStore();
```

### FragmentManager.addFragment(Fragment fragment) returns FragmentStateManager

```java
// FragmentManager.java
FragmentStateManager addFragment(@NonNull Fragment fragment) {
    if (fragment.mPreviousWho != null) {
        FragmentStrictMode.onFragmentReuse(fragment, fragment.mPreviousWho);
    }
    if (isLoggingEnabled(Log.VERBOSE)) Log.v(TAG, "add: " + fragment);
    FragmentStateManager fragmentStateManager = createOrGetFragmentStateManager(fragment);
    fragment.mFragmentManager = this;
    mFragmentStore.makeActive(fragmentStateManager);
    if (!fragment.mDetached) {
        mFragmentStore.addFragment(fragment);
        fragment.mRemoving = false;
        if (fragment.mView == null) {
            fragment.mHiddenChanged = false;
        }
        if (isMenuAvailable(fragment)) {
            mNeedMenuInvalidate = true;
        }
    }
    return fragmentStateManager;
}
```

## BeginTransaction returns a `BackStackRecord`

```java
    // FragmentManager.java
    public FragmentTransaction beginTransaction() {
        return new BackStackRecord(this);
    }
```

### BackstackRecord

holds ref to `final FragmentManager mManager`, instance it gets in constructor.
For add/remove etc ops, forwards calls to `mManager`.

```java
class BackStackRecord extends FragmentTransaction implements
        FragmentManager.BackStackEntry, FragmentManager.OpGenerator
```

## FragmentStore

HOlds a lot of Fragment state

```java
//
class FragmentStore {
    private static final String TAG = FragmentManager.TAG;

    private final ArrayList<Fragment> mAdded = new ArrayList<>();
    private final HashMap<String, FragmentStateManager> mActive = new HashMap<>();
    private final HashMap<String, Bundle> mSavedState = new HashMap<>();

    private FragmentManagerViewModel mNonConfig;
    // ...
}
```