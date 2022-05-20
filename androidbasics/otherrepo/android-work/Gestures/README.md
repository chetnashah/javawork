
### ScaleGestureDetector

If you return false, you get values relative to initial begin gesture, 
but if you return `true`, that is gesture was handled, you will get values
relative to the previous motionEvent, e.g. scaleFactor will be relative to
previous motionEvent.

