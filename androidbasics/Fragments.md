
## Fragments

A fragment is a reusable piece of UI; fragments can be reused and embedded in one or more activities.
Basically a peice of UI with some controller logic.

**You can even show multiple fragments at once on a single screen, such as a master-detail layout for tablet devices**

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


