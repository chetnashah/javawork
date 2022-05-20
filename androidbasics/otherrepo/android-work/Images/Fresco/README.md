
### General Design

Fresco manages Bitmaps on its own, and it does not support providing Bitmap instances created elsewhere. This is necessary because of how Fresco manages memory.
You could save the modified image to disk/network and then just normally load it with Fresco.

### SimpleDraweeView

### Drawee

### Drawee branches

Drawees are made up of different image “branches”, one or more of which may be actually displayed at a time.

### DraweeView

A view that displays the `DraweeHierarchy`.
Hierarchy should be set prior to using this view, typically at creation time.
In order to display the image, controller should also be set.

Set controller on a DraweeView using:
```java
// getting controller
DraweeController mController = mSimpleDraweeView.getController();
// setting controller
mSimpleDraweeView.setController(controller);
```

Similarly DraweeView also has `getHierarchy` and `setHierarchy` methods.

This class does not support ImageView's setImageXxx, setScaleType and similar methods

#### GenericDraweeView

DraweeView that uses `GenericDraweeHierarchy`.

### DraweeHierarchy

This is the hierarchy of Android Drawable objects that will actually render your content. Think of it as the Model in an MVC.

Hierarchies are made up of `Drawables`, and `Drawables` on Android cannot be shared among multiple views.

`DraweeHierarchy`s can be constructed using `DraweeHierarchyBuilder`

If you are using `GenericDraweeHierarchyBuilder`, you must call `Resources.getDrawable` separate for each hierarchy. Do not call it just once and pass it to multiple hierarchies!

```
 * Example hierarchy with a placeholder, retry, failure and the actual image:
 *
 *  o RootDrawable (top level drawable)
 *  |
 *  +--o FadeDrawable (fades between children branches - placeholder, actual, failure)
 *     |
 *     +--o ScaleTypeDrawable (placeholder branch, optional)
 *     |  |
 *     |  +--o Drawable (placeholder image)
 *     |
 *     +--o ScaleTypeDrawable (actual image branch)
 *     |  |
 *     |  +--o ForwardingDrawable (actual image wrapper)
 *     |     |
 *     |     +--o Drawable (actual image)
 *     |
 *     +--o null (progress bar branch, optional)
 *     |
 *     +--o Drawable (retry image branch, optional)
 *     |
 *     +--o ScaleTypeDrawable (failure image branch, optional)
 *        |
 *        +--o Drawable (failure image)
```
**Note**:
* If overlays and/or background are specified, they are added to the same fade drawable, and are always being displayed. Overlay images don’t support scale-types and are scaled to the Drawee size.
* A Drawable instance cannot be shared between multiple Hierarchies.

#### ScaleTypeDrawable

Drawable that can scale underlying drawable based on specified `ScalingUtils.ScaleType` options.



### DraweeHolder

`DraweeHolder` is a class that holds one `DraweeHierarchy` and the associated `DraweeController`. It allows you to make use of all the functionality `Drawee` provides in your custom views and other places where you need a `drawable` instead of a `view`.

Created using `DraweeHolder.create(draweeHierarchy, context);`

### DraweeController

The `view` forwards events to the `controller`. The controller controls
its hierarchy based on those events.

Methods include `getHierarchy` and `setHierarchy`.

The `DraweeController` is the class responsible for actually dealing with the underlying image loader - whether Fresco’s own image pipeline, or another.
Manipulates the model, i.e. `DraweeViewHierarchy` and the `DraweeView`.
Each DraweeController has an `id`.

Touch events received by DraweeView should be forwarded to controller using `draweeController.onTouchEvent(event)`.

`SimpleDraweeView` has two methods for specifying an image. The easy way is to just call `setImageURI`.

If you want more control over how the `Drawee` displays your image, you can use a `DraweeController`.

`DraweeController` is build by using `DraweeControllerBuilder` with builder pattern.

#### Actual Image

The actual image is the target; everything else is either an alternative or a decoration. This is specified using a URI, which can point to an image over the Internet, a local file, a resource, or a content provider.
This is a property of the controller, not the hierarchy.

#### DraweeControllerListener

Listener for important events in controller, like `image requet submit`,
`intermediate image set on view`, `final image set on view`,`controller released the image`.

### GenericDraweeHierarchy

A `DraweeHierarchy` that has `placeholder` and `retry` branches.

### ImageRequest

ImageRequest is the go to class for any image request whether it be from memory, disk or network.

Set the image request in the controller and set controller on View to make it happen.

`ImageRequest` is constructed using `ImageRequestBuilder`.

### RequestListener

The `RequestListener` comes with a large interface of callback methods. Most importantly, you will notice that they all provide the unique requestId which allows to track a request across multiple stages. Specified as a part of ImagePipelineConfig.

```java
final Set<RequestListener> listeners = new HashSet<>();
listeners.add(new MyRequestLoggingListener());

ImagePipelineConfig imagePipelineConfig = ImagePipelineConfig.newBuilder(this)
  .setRequestListeners(listeners)
  .build();

Fresco.initialize(this, imagePipelineConfig);
```

