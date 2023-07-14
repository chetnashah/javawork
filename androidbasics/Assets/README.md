

## File based access to assets

There is no "absolute path for a file existing in the asset folder". The content of your project's `assets/` folder are packaged in the APK file. Use an `AssetManager` object to get an `InputStream` on an asset.

For WebView, you can use the file Uri scheme in much the same way you would use a URL. The syntax for assets is `file:///android_asset/...` (note: three slashes) where the ellipsis is the path of the file from within the `assets/` folder.


