

https://vadzimv.dev/2021/01/01/android-pick-file.html

After Android 11 the only way to access file system is Storage Access Framework.

My goal is to open document one time, read the content and upload it to the server. So `Intent.ACTION_GET_CONTENT` is exactly what I needed.

**An ACTION_GET_CONTENT could allow the user to create the data as it runs (for example taking a picture or recording a sound), let them browse over the web and download the desired data, etc.**

https://developer.android.com/reference/android/content/Intent#ACTION_GET_CONTENT

```kt
fun Fragment.openDocumentPicker() {
    val openDocumentIntent = Intent(Intent.ACTION_GET_CONTENT).apply {
        addCategory(Intent.CATEGORY_OPENABLE)
        type = "*/*"
    }

    startActivityForResult(openDocumentIntent, OPEN_DOCUMENT_REQUEST_CODE)
}

const val OPEN_DOCUMENT_REQUEST_CODE = 2
```

## ACTION_OPEN_DOCUMENT

Activity Action: Allow the user to select and return one or more existing documents. When invoked, the system will display the various DocumentsProvider instances installed on the device, letting the user interactively navigate through them. These documents include local media, such as photos and video, and documents provided by installed cloud storage providers.

Each document is represented as a `content://` URI backed by a `DocumentsProvider`, which can be opened as a stream with `ContentResolver#openFileDescriptor(Uri, String)`, or queried for `DocumentsContract.Document` metadata.

