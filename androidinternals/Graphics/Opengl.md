
## GLSurfaceView

GLSurfaceView is just one way to incorporate OpenGL ES graphics into your application

### GLSurfaceView.Renderer

The GLSurfaceView.Renderer interface requires that you implement the following methods:

1. `onSurfaceCreated()`: The system calls this method once, when creating the GLSurfaceView. Use this method to perform actions that need to happen only once, such as setting OpenGL environment parameters or initializing OpenGL graphic objects.
2. `onDrawFrame()`: The system calls this method on each redraw of the GLSurfaceView. Use this method as the primary execution point for drawing (and re-drawing) graphic objects.
3. `onSurfaceChanged()`: The system calls this method when the GLSurfaceView geometry changes, including changes in size of the GLSurfaceView or orientation of the device screen. For example, the system calls this method when the device changes from portrait to landscape orientation. Use this method to respond to changes in the GLSurfaceView container.
