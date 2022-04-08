

## Resources

https://guides.codepath.com/android/Applying-Data-Binding-for-Views#mvvm-architecture-and-data-binding
https://developer.android.com/topic/libraries/data-binding


## Enabling it

In app/build.gradle
```gradle
dataBinding {
    enabled=true
}
```

In your layout files:
Wrap your layouts with `<layout>...</layout>`

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

### Everything works internally using a bindingadapter


### beginner

1. get rid of `findViewById`:


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

