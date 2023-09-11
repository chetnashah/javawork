
## Bitmaps on the GPU

Android displays bitmaps as OpenGL textures, and the first time a bitmap is displayed in a frame, it's uploaded to the GPU. 

You can see this in Systrace **in the render thread** as **Texture upload(id) width x height**. This can take several milliseconds (see Figure 2), but it's necessary to display the image with the GPU.

