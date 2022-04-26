
### Why use toolbar instead of action bar?

the native ActionBar behaves differently depending on what version of the Android system a device may be using.

Using the support library's toolbar helps ensure that your app will have consistent behavior across the widest range of devices. For example, the Toolbar widget provides a material design experience on devices running Android 2.1 (API level 7) or later, but the native action bar doesn't support material design unless the device is running Android 5.0 (API level 21) or later.

### How to use toolbar?

1. Extend `AppCompatActivity`
2. Theme should not have an actionbar e.g. 
```
<application
    android:theme="@style/Theme.AppCompat.Light.NoActionBar"
    />
```
3. Use toolbar directly in activity layout file:
```
<android.support.v7.widget.Toolbar
   android:id="@+id/my_toolbar"
   android:layout_width="match_parent"
   android:layout_height="?attr/actionBarSize"
   android:background="?attr/colorPrimary"
   android:elevation="4dp"
   android:theme="@style/ThemeOverlay.AppCompat.ActionBar"
   app:popupTheme="@style/ThemeOverlay.AppCompat.Light"/>
   ...
```
4. Use `Activity.setSupportActionBar`
```
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_my);
    Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
    setSupportActionBar(myToolbar);
}
```