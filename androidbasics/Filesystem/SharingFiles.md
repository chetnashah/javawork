
## 
To securely offer a file from your app to another app, you need to configure your app to offer a secure handle to the file, in the form of a `content URI`. The Android `FileProvider` component generates content URIs for files, based on specifications you provide in XML.

## Specify file provider

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.myapp">
    <application
        ...>
        <provider
            android:name="androidx.core.content.FileProvider"
            android:authorities="com.example.myapp.fileprovider"
            android:grantUriPermissions="true"
            android:exported="false">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/filepaths" />
        </provider>
        ...
    </application>
</manifest>
```

## Receive file requests (file server/provider app)

To receive requests for files from client apps and respond with a content URI, your app should provide a file selection Activity. Client apps start this Activity by calling `startActivityForResult()` with an Intent containing the action `ACTION_PICK`. When the client app calls `startActivityForResult()`, your app can return a result to the client app, in the form of a content URI for the file the user selected.

### Have a file selection activity

```java
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
    ...
        <application>
        ...
            <activity
                android:name=".FileSelectActivity"
                android:label="@File Selector" >
                <intent-filter>
                    <action
                        android:name="android.intent.action.PICK"/>
                    <category
                        android:name="android.intent.category.DEFAULT"/>
                    <category
                        android:name="android.intent.category.OPENABLE"/>
                    <data android:mimeType="text/plain"/>
                    <data android:mimeType="image/*"/>
                </intent-filter>
            </activity>
```

In the file selection onItemClick
```java
onFileSelect(){
                /*
                 * Get a File for the selected file name.
                 * Assume that the file names are in the
                 * imageFilename array.
                 */
                File requestFile = new File(imageFilename[position]);
                /*
                 * Most file-related method calls need to be in
                 * try-catch blocks.
                 */
                // Use the FileProvider to get a content URI
                try {
                    fileUri = FileProvider.getUriForFile(
                            MainActivity.this,
                            "com.example.myapp.fileprovider",
                            requestFile);

                if (fileUri != null) {
                    // Grant temporary read permission to the content URI
                    resultIntent.addFlags(
                        Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
                    // Put the Uri and MIME type in the result Intent
                    resultIntent.setDataAndType(
                            fileUri,
                            getContentResolver().getType(fileUri));
                    // Set the result
                    MainActivity.this.setResult(Activity.RESULT_OK,
                            resultIntent);

}
```

## Send file request and get file data back (client apps that pick files from server/provider app)

To request a file from the server app, the client app calls `startActivityForResult` with an Intent containing the action such as `ACTION_PICK` and a `MIME type` that the client app can handle.

```java
        requestFileIntent = new Intent(Intent.ACTION_PICK);
        requestFileIntent.setType("image/jpg");
        startActivityForResult(requestFileIntent, 0);
```

Do `openFileDescripter` on returned URI from server app.
```java
Uri returnUri = returnIntent.getData();
inputPFD = getContentResolver().openFileDescriptor(returnUri, "r");
```

```java
    /*
     * When the Activity of the app that hosts files sets a result and calls
     * finish(), this method is invoked. The returned Intent contains the
     * content URI of a selected file. The result code indicates if the
     * selection worked or not.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode,
            Intent returnIntent) {
        // If the selection didn't work
        if (resultCode != RESULT_OK) {
            // Exit without doing anything else
            return;
        } else {
            // Get the file's content URI from the incoming Intent
            Uri returnUri = returnIntent.getData();
            /*
             * Try to open the file for "read" access using the
             * returned URI. If the file isn't found, write to the
             * error log and return.
             */
            try {
                /*
                 * Get the content resolver instance for this context, and use it
                 * to get a ParcelFileDescriptor for the file.
                 */
                inputPFD = getContentResolver().openFileDescriptor(returnUri, "r");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Log.e("MainActivity", "File not found.");
                return;
            }
            // Get a regular file descriptor for the file
            FileDescriptor fd = inputPFD.getFileDescriptor();
            ...
        }
    }
```