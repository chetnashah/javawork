
## Fragments

A fragment is a reusable piece of UI; fragments can be reused and embedded in one or more activities.
Basically a peice of UI with some controller logic.

Fragments introduce modularity and reusability into your activity’s UI by allowing you to divide the UI into discrete chunks.

**You can even show multiple fragments at once on a single screen, such as a master-detail layout for tablet devices**

## Implemented interfaces:
LifecycleOwner, ViewModelStoreOwner

## Using from androidx

```groovy
    // Java language implementation
    implementation("androidx.fragment:fragment:$fragment_version")
    // Kotlin
    implementation("androidx.fragment:fragment-ktx:$fragment_version")
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

## Fragment lifecycle

To manage lifecycle, `Fragment` implements `LifecycleOwner`, exposing a `Lifecycle` object that you can access through the `getLifecycle()` method.

**A fragment's view has a separate Lifecycle that is managed independently from that of the fragment's Lifecycle.**.
Fragments maintain a separate LifecycleOwner for their view, which can be accessed using `getViewLifecycleOwner()`.

`onAttach()` is always called before any Lifecycle state changes.
`onDetach()` is always called after any Lifecycle state changes.

![fragment lifecycle vs fragment view lifecycle](images/fragment-view-lifecycle.png)

Each possible Lifecycle state is represented in the Lifecycle.State enum.

* INITIALIZED
* CREATED
* STARTED
* RESUMED
* DESTROYED
