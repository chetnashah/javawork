
In Android APIs, External storage actually means shared/explorer readable storage.


Only secure way to offer a file from your app to another app is to send the receiving app
the files content URI and grant temp access permissions to that URI.

The android `FileProvider` component provides the method `getUriForFile()` for generating file's content URI.

### Scoped storage

Available on Android 10 or higher

### Documents

A document can either be an openable stream or a directory containing additional documents (with MIME_TYPE_DIR).

Each document under a provider is uniquely referenced by its `Document#COLUMN_DOCUMENT_ID`

### DocumentsProvider

A document provider offers read and write access to durable files, such as files
stored on a local disk or files stored in a cloud storage service.

When someone fires a Intent with action `ACTION_OPEN_DOCUMENT`, system will display
various `DocumentsProvider` instances installed on the device, letting user interactively navigate through them. 

Each document is represented as a `content://` URI backed by a `DocumentsProvder` which can
be opened as a stream with `COntentResolver#openFileDescriptor(Uri, String)`.

All selected documents are returned to the calling app with persistible read and write permission grants.

To have filtered/acceptable document types to select, callers must indicate MIME type trhough `setType`.

**Note** - Callers must include CATEGORY_OPENABLE in the INtent to obtain URIs that can be opened with `ContentResolver#openFileDescriptor(Uri, String)`.


### Interacting with mediaStore:

Your only option to interact with media store is to reach out for `ContentResolver` object that 
you retrieve from the app's context.

`ContentResolver` has methods like `query`, `insert` etc.

Well known tables:
1. `MediaStore.Images` - System scans files in `DCIM/` and `Pictures/` and adds these to this table.
2. `MediaStore.Video` - System adds files from `DCIM/`, `Movies/` and `Pictures/` to this table.
3. `MediaStore.Audio` - System adds files from `Music/`, `Alarms/` etc.
4. `MediaStore.Downloads` (Available only After API 29)
5. `MediaStore.Files` (Available only after API 29)


#### Opening and modifying media files



API Level 29:
FOllowing is deprecated:
1. `Environment.getExternalStorageDirectory()`
2. `Environment.getExternalStoragePublicDirectory(String type)`
