
https://medium.com/androiddevelopers/viewmodel-one-off-event-antipatterns-16a1da869b95

https://medium.com/androiddevelopers/migrating-from-livedata-to-kotlins-flow-379292f419fb
##

The ViewModel class is designed to store and manage UI-related data in a lifecycle conscious way. The ViewModel class allows data to survive configuration changes such as screen rotations.

## Typically a viewmodel will expose a `LiveData<SomeUiState>` or `StateFlow<SomeUiState>` to UI components

## Restrictions

**A ViewModel must never reference a View, Lifecycle, or any class that may hold a reference to the activity context. because activities, fragments, and views do not survive configuration changes, thus resulting in leak**

ViewModel objects are designed to outlive specific instantiations of views or LifecycleOwners. This design also means you can write tests to cover a ViewModel more easily as it doesn't know about view and Lifecycle objects.

## What is ViewModelProvider?

An utility class that provides ViewModels for a scope.

Default `ViewModelProvider` for an Activity or a Fragment can be obtained by passing it to the constructor: `ViewModelProvider(myFragment)`

## Creating a ViewModel via ViewModelProvider (Recommended way)

**Always use `ViewModelProvider` to create ViewModel objects rather than directly instantiating an instance of ViewModel.**
`The reason`: instantiating directly from fragment/activity might result in multiple viewmodels, but we want to viewmodel to live across config changes/fragment/activity recreation.


ViewModelProvider internally does the lifecycle management with the first argument it gets: `ViewModelProvider.get(this)..`

How `ViewModelProvider` works:
* `ViewModelProvider` returns an existing ViewModel if one exists, or it creates a new one if it does not already exist.
* `ViewModelProvider` creates a ViewModel instance in association with the given scope (an activity or a fragment).
* The `created ViewModel is retained as long as the scope is alive`. For example, if the scope is a fragment, the ViewModel is retained until the fragment is detached.

Getting a viewModel instance from `ViewModelProvider`
```kt
private lateinit var viewModel: GameViewModel

// inside onCreateView() or onCreate()
Log.i("GameFragment", "Called ViewModelProvider.get")
viewModel = ViewModelProvider(this).get(GameViewModel::class.java)
```

`ViewModelProvider(ViewModelStoreOwner owner)`: Creates ViewModelProvider. This will create ViewModels and retain them in a store of the given ViewModelStoreOwner. Usually this `owner` will be either `Activity` or `Fragment`.

## How long does a Viewmodel live?

The ViewModel remains in memory until the Lifecycle it's scoped to goes away permanently: **in the case of an activity, when it finishes, while in the case of a fragment, when it's detached**. Note how Viewmodel stays intact when lifecycle like onCreate/onDestroy are called on activity/fragment.

## author a simple ViewModel (Not recommended)

```kt
class GameViewModel : ViewModel() {
   init {
       Log.i("GameViewModel", "GameViewModel created!")
   }
}
```

## Linking up a viewmodel with fragment/activity

```kt
lateinit var gameViewModel: GameViewModel
```


## Using viewmodel via delegation in a fragment

delegation happening to an object returned by extension method `viewModels()` in `Fragment` class.
All access to `viewModel` are routed via the returned object from `viewModels()`, and it automatically manages lifecycle stuff.

```kt
class GameFragment : Fragment() {
    // delegation happening to an object returned by extension method `viewModels()
    private val viewModel: GameViewModel by viewModels()

```

## lifecycle of a viewmodel

### onCleared callback

The ViewModel is destroyed when the associated fragment is detached, or when the activity is finished. Right before the ViewModel is destroyed, the `onCleared()` callback of viewmodel is called to clean up the resources.

## ViewModelFactory

A `ViewModelFactory instantiates ViewModel objects`, with or without constructor parameters.

`isAssignableFrom`: `clazz.isAssignableFrom(Foo.class)` will be true whenever the class represented by the clazz object is a superclass or superinterface of Foo, or in other words, Foo is a subclass of clazz.

`ViewModelProvider.Factory` interface has one method to be implemented: `create` method.

e.g.
```java
class ScoreViewModelFactory(private val finalScore: Int) : ViewModelProvider.Factory {
    // this method is abstract in the parent factory
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        TODO("Not yet implemented")
    }
}

class ScoreViewModel(finalScore: Int) : ViewModel() {
    // The final score
    var score = finalScore
    init {
        Log.i("ScoreViewModel", "Final score is $finalScore")
    }
}


SomeFragment {
    onCreateView() {
        //... 
        viewModelFactory = ScoreViewModelFactory(100)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ScoreViewModel::class.java)
    }
}
```

## Viewmodel creation is typically lazy

On first access via `ViewModelProvider.get()` or on first access via fragment delegate, the first creation would happen on first need/access. tODO: verify.

## Internals

### ViewModelStore

```java
/*
Class to store ViewModels.

An instance of ViewModelStore must be retained through configuration changes: if an owner of this ViewModelStore is destroyed and recreated due to configuration changes, new instance of an owner should still have the same old instance of ViewModelStore.

If an owner of this ViewModelStore is destroyed and is not going to be recreated, then it should call clear() on this ViewModelStore, so ViewModels would be notified that they are no longer used.

Use ViewModelStoreOwner.getViewModelStore() to retrieve a ViewModelStore for activities and fragments.
*/
public class ViewModelStore {
    private final HashMap<String, ViewModel> mMap = new HashMap<>();
    //...
}
```

### ViewModelStoreOwner Interface (Implementors are Activity and Fragment)

An object that holds a `ViewModelStore`:
```java
/**
 * A scope that owns {@link ViewModelStore}.
 * <p>
 * A responsibility of an implementation of this interface is to retain owned ViewModelStore
 * during the configuration changes and call {@link ViewModelStore#clear()}, when this scope is
 * going to be destroyed.
 *
 * @see ViewTreeViewModelStoreOwner
 */
public interface ViewModelStoreOwner {
    /**
     * Returns owned {@link ViewModelStore}
     */
    @NonNull
    ViewModelStore getViewModelStore();
}
```

### FragmentManagerViewModel

FragmentManagerViewModel is the always up to date view of the Fragment's non configuration state.
```java
final class FragmentManagerViewModel extends ViewModel {
    private final HashMap<String, Fragment> mRetainedFragments = new HashMap<>();
    private final HashMap<String, FragmentManagerViewModel> mChildNonConfigs = new HashMap<>();
    private final HashMap<String, ViewModelStore> mViewModelStores = new HashMap<>();
}
```

## Scoped viewmodels

there are 3 major scoped ViewModels extensions

`viewModels()` — scoped to its fragment, can use it in fragment
`activityViewModels()` — scoped to its activity, can use it in fragment (for shared viewmodel between fragment use cases)
`navGraphViewModels()` — scoped to its sub-navGraph.

## Sharing viewmodels between multiple components

https://developer.android.com/topic/libraries/architecture/viewmodel#sharing

## dependencies for testing

```groovy
testImplementation 'junit:junit:4.+'
testImplementation 'androidx.arch.core:core-testing:2.1.0'
```

### Why use `observeForever` in tests?

Unless you observe forever, transformations will not run to save battery/resources.
Use following to trgger all transformations.
```kt
viewModel.price.observeForever {}
```

## ViewModelScope

A `ViewModelScope` is the built-in coroutine scope defined for each ViewModel in your app. 
**Any coroutine launched in this scope is automatically canceled if the ViewModel is cleared.**

e.g.
```kt
private fun getMarsPhotos() {
    viewModelScope.launch {
        val listResult = MarsApi.retrofitService.getPhotos()
    }
}
```