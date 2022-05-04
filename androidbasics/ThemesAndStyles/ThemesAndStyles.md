https://developer.android.com/guide/topics/ui/look-and-feel/themes
https://medium.com/androiddevelopers/android-styling-themes-vs-styles-ebe05f917578
https://medium.com/androiddevelopers/android-styling-themes-overlay-1ffd57745207
https://medium.com/androiddevelopers/android-styling-common-theme-attributes-8f7c50c9eaba
https://medium.com/androiddevelopers/android-styling-prefer-theme-attributes-412caa748774

## Style

A style can specify attributes for a View, such as font color, font size, background color, and much more.


## Theme

A theme is a collection of styles that's applied to an entire app, activity, or view hierarchy—not just an individual View.

When you apply a theme to an app, activity, view, or view group, the theme is applied to that element and all of its children. Themes can also apply styles to non-view elements, such as the status bar and window background

Common theme attributes: https://medium.com/androiddevelopers/android-styling-common-theme-attributes-8f7c50c9eaba

A theme can be found/defined in `themes.xml`. Typically themes will extend on top of platform themes like following:
```xml
    <!-- Overriding Theme.MaterialComponents.DayNight.DarkActionBar -->
    <style name="Theme.TipTime" parent="Theme.MaterialComponents.DayNight.DarkActionBar">
        <!-- Primary brand color. -->
        <item name="colorPrimary">@color/purple_500</item>
        <item name="colorPrimaryVariant">@color/purple_700</item>
        <item name="colorOnPrimary">@color/white</item>
        <!-- Secondary brand color. -->
        <item name="colorSecondary">@color/teal_200</item>
        <item name="colorSecondaryVariant">@color/teal_700</item>
        <item name="colorOnSecondary">@color/black</item>
        <!-- Status bar color. -->
        <item name="android:statusBarColor" tools:targetApi="l">?attr/colorPrimaryVariant</item>
        <!-- Customize your theme here. -->
    </style>
``` 

**How to use this application defined custom theme** `Theme.TipTime`?
IN `Application` tag of `AndroidManifest.xml` like this:
```xml
    <application
        android:theme="@styles/Theme.TipTime"
    />
```

### Theme colors

1. primary - 
2. secondary -
3. surface - cards, sheets menus
4. background - behind scrollable content
5. error - errors in components such as text fields

"On colors" - Whenever other screen elements, such as **text or icons**, appear in front of surfaces using those colors, those elements should use colors specifically designed to appear clearly and legibly against the colors behind them. Usually **on-colors will be opposite to their background provide contrast**.

e.g.
6. on-primary
7. on-secondary
8. on-background
9. on-surface
10. on-error

To override these, you can try this tool for quick use:
https://material.io/resources/color/#!/?view.left=0&view.right=0&primary.color=42A5F5&secondary.color=CDDC39&primary.text.color=FDD835&secondary.text.color=2962FF

Common attributes
| Name | Theme attribute |
| --- | --- |
| Primary | colorPrimary |
| Primary Variant | colorPrimaryVariant |
| Secondary | colorSecondary |
| Secondary Variant | colorSecondaryVariant |
| Background | colorBackground |
| Surface | colorSurface |
| Error | colorError |
| On Primary | colorOnPrimary |
| On Secondary | colorOnSecondary |
| On Background | colorOnBackground |
| On Surface | colorOnSurface |
| On Error | colorOnError |




