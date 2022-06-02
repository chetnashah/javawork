
## NavigationGraph

A new resource type, 
can be accessed via:
`@navigation/my_nav_graph`

## Navigation destinations

Can be `Activity` or `Fragment`

## Navigation actions


## NavigationController

**NavController** is the main class that helps you navigate from one destination to another.

Each `NavHostFragment` has an associated NavController, this is what triggers navigation actions.
e.g.
```kotlin
// can be retrieved as folows
var newsNavHostFragment = supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
var navController = newsNavHostFragment.navController
```

NavigationController can be hooked up to navigationViews like bottomtab or drawers etc
e.g.
```kt
    bottomNavigationView.setupWithNavController(newsNavHostFragment.navController)
```

### How to get access to NavController?

```java
//Activity
var navController = findNavController(R.id.nav_hostfragment);

// Fragmnt
var navController = findNavController()

// any view inside nav graph
var navController = view.findNavController()
```

## NavHostFragment

A central fragment that swaps in and out other fragments based on `NavigationGraph` specification.

### FRagment container View to setup NavHostFragment

It refers to navhost graph resource via `app:navGraph` attribute and its name is navhostframent i.e.
`android:name="androidx.navigation.fragment.NavHostFragment"`

Can be used as:
```xml
<!-- layout/main_activity.xml -->
 <!-- ... -->
    <androidx.fragment.app.FragmentContainerView
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:id="@+id/my_nav_host_fragment"
    android:name="androidx.navigation.fragment.NavHostFragment"
    app:navGraph="@navigation/nav_sample"
    app:defaultNavHost="true" />
```

### Arrows between fragments are represented by `actions` in XML

e.g. in a `@navigation/my_nav_graph.xml`, they can also have animations
```xml
<!-- @navigation/my_nav_graph.xml -->
    <fragment
        android:id="@+id/searchNewsFragment"
        android:name="com.androiddevs.mvvmnewsapp.ui.fragment.SearchNewsFragment"
        android:label="SearchNewsFragment" >
        <action
            android:id="@+id/action_searchNewsFragment_to_articleFragment"
            app:destination="@id/articleFragment" 
            app:enterAnim="@anim/slide_in_right"
            app:exitAnim="@anim/slide_out_left"
            app:popEnterAnim="@anim/slide_in_left"
            app:popExitAnim="@anim/slide_out_right"
        />
    </fragment>

```

## setting up bottom menu with navgraph

The navgraph fragment-ids should match menu ids in menu resource: e.g.
```xml
<!-- @menu/bottom_navigation_menu.xml -->
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <!-- id declared in navigation resourcefile for a given fragment -->
    <item android:id="@+id/breakingNewsFragment" 
        android:title="Breaking news"
        android:icon="@drawable/ic_breaking_news"
        />

    <item android:id="@+id/savedNewsFragment"
        android:title="Saved news"
        android:icon="@drawable/ic_favorite"
        />

    <item android:id="@+id/searchNewsFragment"
        android:title="Search news"
        android:icon="@drawable/ic_all_news"
        />
</menu>
```

## Bottom navigation UI component

Following view should be placed wherever you need bottom navigation view
```xml
<!-- layout/main_activity.xml -->
 <!-- ... -->
    <com.google.android.material.bottomnavigation.BottomNavigationView
        android:id="@+id/bottomNavigationView"
        android:layout_width="match_parent"
        android:layout_height="56dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:menu="@menu/bottom_navigation_menu"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.5"
        app:layout_constraintStart_toStartOf="parent"/>
```

In MainActivity.kt file in `onCreate()` after setContentView

```kt
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        var newsNavHostFragment = supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        bottomNavigationView.setupWithNavController(newsNavHostFragment.navController)
    }
```

Getting error `app:id/newsNavHostFragment} does not have a NavController set`?

## Navigation params

We create args/params using `SafeArgs` and we get a `Directions` object, that we pass to `navController.navigate(directions)`.