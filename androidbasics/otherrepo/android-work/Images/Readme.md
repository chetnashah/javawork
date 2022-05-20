
### Large Images

Need to be broken into tiles.

`SubsamplingScaleImageView`, . It utilizes a tiled image segmentation algorithm like the one used in Android's stock Gallery and maps. That library supports rotations, scaling, and translating. It was specifically made to handle for OOM errors and it has been tested up to 20,000 by 20,000 pixel images.

https://github.com/facebook/fresco/issues/4

### Bitmap

In order to show images, they must be in decoded form as Bitmaps.
The bitmap cache stores decoded images as Android Bitmap objects

### Sampling and decoding

For fresco, The resize/rotation etc happens before decoding, i.e. 
on the encoded(compressed) image.

### Resizing

Does not modify file on disk, 
Resizing step is done in encoded in-memory form.

Resizing limitations:
it only supports JPEG files
the actual resize is carried out to the nearest 1/8 of the original size

### Scaling

It is an entierely hardware operation that does matrix manipulations on GPU.

### Downlsampling vs Resizing

Downsampling is generally faster than resizing, since it is part of the decode step, rather than a separate step of its own. It also supports PNG and WebP (except animated) images as well as JPEG.

### Scaling vs Resizing images

* image size less than views: You have less data, your best bet is upscaling

* image size greater than view: resize it, will improve memory usage, lesser evicts and hence lesser decodes.


### Images from the network

For network images, try to download an image as close as possible to the size you will be displaying. By downloading images of inappropriate size you are wasting the user’s time and data.



### ImageDecoder API

The new ImageDecoder api is much more powerful and supports a variety of different types. It can handle assets, gifs, resources, drawables and much more. You can pass it many different kinds of types and it will take care of converting them to Drawables or Bitmaps for you.

A class for converting encoded images (like `PNG`, `JPEG`, `WEBP`, `GIF`, or `HEIF`) into `Drawable` or `Bitmap` objects.

