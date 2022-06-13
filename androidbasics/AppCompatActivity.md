
Part of `androidx/appcompat/app/AppCompatActivity`

AppCompatActivity is a compatibility class that ensures your activity looks the same across different platforms OS levels.


every activity that extends this class has to be themed with `Theme.AppCompat` or a theme that extends that theme.

Supports actionbar well with `setSupportActionBar`.


The `app` namespace is for attributes that come from either your custom code or from libraries and not the core Android framework.
e.g. using `app:srcCompat` instead of `android:src` on a `https://developer.android.com/reference/androidx/appcompat/widget/AppCompatImageView` instead of a `ImageView`.

## onSupportNavigateUp

Override this method to support up navigation

