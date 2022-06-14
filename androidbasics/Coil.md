
Image loading library backed by Kotlin coroutines (Stands for Coroutine Image Loader)

https://coil-kt.github.io/coil/

```groovy
implementation("io.coil-kt:coil:2.1.0")
```

## How to Use

The library adds a `load` extension function on `ImageView`.

```kt
// URL
imageView.load("https://www.example.com/image.jpg")
```