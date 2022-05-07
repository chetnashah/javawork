

## Using ACTION_SEND

to send data from one activity to another, **even across process boundaries**.

You need to specify:
1. `data` - can be any thing e.g. EXTRA_TEXT for text sending, place a URI to the data in the EXTRA_STREAM 
2. `type` - 

System will automatically identify compatible activities and display them to user.

## Using ShareSheet (Via `Intent.createChooser()`)

Apps discouraged from creating their own list of share targets or sharesheet variations.

How to **not show sharesheet**(instead prefer OS disambiguation dialog) ?
DO not call `Intent.createChooser()`. Directly do `startActivity(intent)`.

### Extra features

1. when user completed share and to whare
2. custom ChooserTarget and app targets
3. rich text previews
4. exclude targets matching specific componentnames

## Intent.createChooser

Takes an intent, and returns a version of that intent which will always display android ShareSheet.

e.g.
```kotlin
val sendIntent: Intent = Intent().apply {
    action = Intent.ACTION_SEND
    putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
    type = "text/plain"
}

val shareIntent = Intent.createChooser(sendIntent, null)
startActivity(shareIntent)
```