
## File upload with "File" only + Okhttp

`RequestBody` can be created from a `File` via extension functions.

```java
  public static final MediaType MEDIA_TYPE_MARKDOWN
      = MediaType.parse("text/x-markdown; charset=utf-8");

  private final OkHttpClient client = new OkHttpClient();

  public void run() throws Exception {
    File file = new File("README.md");

    Request request = new Request.Builder()
        .url("https://api.github.com/markdown/raw")
        // RequestBody from file
        .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file))
        .build();

    try (Response response = client.newCall(request).execute()) {
      if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

      System.out.println(response.body().string());
    }
  }
```




## Form field file + Okhttp

Basically we need `MulitpartBody.builder`

MultipartBody.Builder can build sophisticated request bodies compatible with HTML file upload forms. **Each part of a multipart request body is itself a request body, and can define its own headers.** If present, these headers should describe the part body, such as its Content-Disposition. The Content-Length and Content-Type headers are added automatically if they’re available.

https://square.github.io/okhttp/recipes/#posting-a-multipart-request-kt-java

That `MultipartBody.Builder` has `addFormDataPart()` methods that let you fill in the elements of the “form”. 
For including a “file” (from the standpoint of the form), `addFormDataPart()` can accept a `RequestBody`

```kt
    // Use the imgur image upload API as documented at https://api.imgur.com/endpoints/image
    val requestBody = MultipartBody.Builder()
        .setType(MultipartBody.FORM)
        .addFormDataPart("title", "Square Logo")
        .addFormDataPart("image", "logo-square.png",
            File("docs/images/logo-square.png").asRequestBody(MEDIA_TYPE_PNG))
        .build()
```

## File upload with Uri

https://commonsware.com/blog/2020/07/05/multipart-upload-okttp-uri.html

https://github.com/cketti/OkHttpWithContentUri

**Uri** like contentUris in android have become increasingly important.
But library like `Okhttp` is platform agnostic and would only understand `File`s.
As changes like scoped storage increase the need to be able to work with content Uri values, we need to be able to convert them to `File` objects.

**If you have a need to do a multipart form upload, and your content is identified by a Uri instead of a File, the InputStreamRequestBody that Jared Burrows created (with help from Jake Wharton) is what you need.**

Introducing Androidism - **OkHttp knows nothing about Uri or ContentResolver or other Android SDK classes. But RequestBody is an abstract class, so we can create our own implementation that can use Android SDK classes for our own Android projects. Intents like ACTION_GET_DOCUMENT or ACTION_GET_CONTENT tend to return content URIs**

We create our own `RequestBody` that can copy all data from a `Uri` by creating an input stream by opening it via Contentresolver.
And copying all data from that InputStream into itself.

```kt
class InputStreamRequestBody(
  private val contentType: MediaType,
  private val contentResolver: ContentResolver,
  private val uri: Uri
) : RequestBody() {
  override fun contentType() = contentType

  override fun contentLength(): Long = -1

  @Throws(IOException::class)
  override fun writeTo(sink: BufferedSink) {
    val input = contentResolver.openInputStream(uri)

    input?.use { sink.writeAll(it.source()) }
      ?: throw IOException("Could not open $uri")
  }
}
```

Using `InputStreamRequestBody` in a `MultipartBody.Builder`:
You can create an instance of `InputStreamRequestBody` by passing it a `Uri`,
 and include it in an `addFormDataPart()` call where the OkHttp recipe shows the one based on the File. 

```kt
val contentPart = InputStreamRequestBody(type, resolver, content)

val requestBody = MultipartBody.Builder()
  .setType(MultipartBody.FORM)
  // TODO add other form elements here
  .addFormDataPart("something", name, contentPart)
  .build()

val request = Request.Builder()
  .url(serverUrl)
  .post(requestBody)
  .build()

ok.newCall(request).execute().use { response ->
  // TODO something with the response
}
```