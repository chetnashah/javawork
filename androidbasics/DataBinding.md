

## Resources

https://guides.codepath.com/android/Applying-Data-Binding-for-Views#mvvm-architecture-and-data-binding
https://developer.android.com/topic/libraries/data-binding
https://medium.com/androiddevelopers/data-binding-lessons-learnt-4fd16576b719

## ViewBinding vs DataBinding

`DataBinding` - two way data binding, like angularjs. Binding data (from code) to views + ViewBinding (Binding views to code).

`ViewBinding` - Roughly speaking, view binding is a subset of data binding. You do not get the data binding expressions, but you do get the generated binding class that you can use to handle the findViewById() calls and give you type-safe and null-safe access to the widgets from the layout.

An instance of this binding class contains direct reference to all views in that have id in the corresponding layout.


## Enabling viewbinding

gradle v3.6
```groovy
android {
    viewBinding {
        enabled = true
    }
}
```

but in gradle 4+
```groovy
android {
    buildFeatures {
        viewBinding = true
    }
}
```

## Enabling Databinding

In app/build.gradle
```gradle
dataBinding {
    enabled=true
}
```

**Note** : To pariticipate in `DataBinding` -> In your layout files: Wrap your layouts with `<layout>...</layout>`

The layout container tag tells Android Studio that this layout should take the extra processing during compilation time to find all the interesting Views and note them for use with the data binding library.

Example usage:
```kotlin
class MainActivity : AppCompatActivity() {
    // Store the binding
    private lateinit var binding: ActivityMainBinding // generated class to hold contentview binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the content view (replacing `setContentView`)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        // Store the field now if you'd like without any need for casting
        val tvLabel: TextView = binding.tvLabel
        tvLabel.isAllCaps = true
        // Or use the binding to update views directly on the binding
        binding.tvLabel.text = "Foo"
    }
}
```
layout file:
```xml
<!-- layout tag is necessary to take part in databinding -->
<layout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools">
    <RelativeLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:paddingLeft="@dimen/activity_horizontal_margin"
            android:paddingRight="@dimen/activity_horizontal_margin"
            android:paddingTop="@dimen/activity_vertical_margin"
            android:paddingBottom="@dimen/activity_vertical_margin"
            tools:context=".MainActivity">

        <TextView
                android:id="@+id/tvLabel"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"/>

    </RelativeLayout>
</layout>
```


### `layout` tag can contain `data` variables for binding in xml

This is how we make data available to layout
e.g.
```xml
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    tools:context=".SummaryFragment">
    <data>
        <variable
            name="viewModel"
            type="com.example.cupcake.OrderViewModel" />
    </data>
    <!-- ... some views -->
</layout>
```

Anyt `variable` declared in the `data` tag in xml, is available in kotlin/java under `XYZBinding` generated class.
e.g. for
```xml
    <data>
        <variable
            name="myName"
            type="String" />
    </data>
```

Can be manipulated using
```kt
bindingVar?.myName = "hi"
```

This also used to access hosting class/fragment/activity instance inside the layout to call listener in the UI contorller.
E.g.
```xml
        <variable
            name="flavorFragment"
            type="com.example.cupcake.FlavorFragment" />

    <!-- some stuff -->
    <!-- note onClick callsback into hosting fragment -->
    <Button
                android:id="@+id/order_six_cupcakes"
                android:layout_width="wrap_content"
                android:onClick="@{() -> startFragment.orderCupcake(6)}" 
                android:layout_height="wrap_content"
                android:layout_gravity="center_horizontal"
                android:layout_marginTop="@dimen/margin_between_elements"
                android:minWidth="@dimen/order_cupcake_button_width"
                android:text="@string/six_cupcakes" />
```

```kt
override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding?.flavorFragment = this
}

fun orderCupcake(quantity: Int) {
    sharedViewModel.setQuantity(quantity)
    if(sharedViewModel.hasNoFlavorSet()) {
        sharedViewModel.setFlavor(getString(R.string.vanilla))
    }
    findNavController().navigate(R.id.action_startFragment_to_flavorFragment)
}
```

### Databinding expressions in layout file

Prefixed by `@` and wrapped in curly braces:
e.g. `<Element attr="@{expression}">`
e.g.
```xml
<TextView android:text="@{viewmodel.name}">
```

### Exposing code viewmodel to layout viewmodel

```kt
onViewCreated() {
    val binding = DataBindingUtil.inflate<MySampleBinding>(inflater, R.layout.fragment_layout, container, false)
    binding.viewModel = orderViewModel // now layout has viewmodel access
}
```


## Getting error: Data Binding : Resources$NotFoundException when attribute of object is int

Use `String.valueOf()` because int means resourceId
e.g.
```xml
<TextView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text="@{String.valueOf(user.age)}"/>
```

### Observing livedata changes

Set lifecycleowner on binding object, app will be able to observe livedata objects.

```kt
binding?.apply {   
    lifecycleOwner = viewLifecycleOwner
    ...
}
```

### No need to do liveData.value in bindings


### Everything works internally using a bindingadapter


### beginner

1. get rid of `findViewById`:


### Inflate part

Replace
```
binding = GameFragmentBinding.inflate(inflater, container, false)
```
With
```kt
binding = DataBindingUtil.inflate(inflater, R.layout.game_fragment, container, false)
```

### Usage by DataBindingUtil in Activity onCreate

```kt
DataBindingUtil.setContentView<ActivityGardenBinding>(this, R.layout.activity_garden)
```

### Data elements in XML

the binding variables that can be used in expressions are defined inside a data element that is a sibling of the UI layout's root element. Both elements are wrapped in a layout tag

```xml
<layout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto">
    <data>
        <variable
            name="viewmodel"
            type="com.myapp.data.ViewModel" />
    </data>
    <ConstraintLayout... /> <!-- UI layout's root element -->
</layout>
```

### Intermediate


### Expeert

