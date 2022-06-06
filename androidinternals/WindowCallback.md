API from a Window back to its caller.  This allows the client to intercept key dispatching, panels and menus, etc.


List is common methods like:
```java
        public boolean dispatchKeyEvent(KeyEvent event);
        public boolean dispatchTouchEvent(MotionEvent event);
        boolean onMenuItemSelected(int featureId, @NonNull MenuItem item);
        public void onWindowFocusChanged(boolean hasFocus);
        public void onAttachedToWindow();

```