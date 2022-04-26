
The good news is that there is an Android X compatibility library for vector drawables all the way back to API level 7. 2. Open build.gradle (Module: app). Add this line to the defaultConfig section:
```gradle
// build.gradle file defaultConfig section
vectorDrawables.useSupportLibrary = true
```

Change the `android:src` attribute in the `<ImageView>` element to be `app:srcCompat`