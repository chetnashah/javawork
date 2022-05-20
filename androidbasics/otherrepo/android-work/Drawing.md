
### Canvas

`Canvas.cliprect(rect)`: 
`Canvas.clipRect(left, top, right, bottom)` reduces the region of the screen that future draw operations can write to. It sets the clipBounds to be the spacial intersection of the current clipping rectangle and the rectangle specified

### postInvalidate

`postInvalidateOnAnimation` will put a callback into the animation callbacks and `postInvalidate` will will put into the traversal callbacks.

The key difference for me is that the animation callbacks are called before the traversal callbacks (layouts, draws).

