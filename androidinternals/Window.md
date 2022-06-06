

A convinience abstract base that represents a `Window`.

Actual instance/implementation class of this in android framework is `PhoneWindow`.

## Window abstract class methods

```java
    public void setTheme(int resId) {}
    public void setFlags(int flags, int mask) {}
    public abstract void setContentView(View view);
    public abstract @NonNull View getDecorView();
    public abstract void addContentView(View view, ViewGroup.LayoutParams params);
    public void setContainer(Window container) {}
    public abstract boolean superDispatchTouchEvent(MotionEvent event);
    public <T extends View> T findViewById(@IdRes int id) {
        return getDecorView().findViewById(id);// delegating to implementor of getDecorView()
    }

    /**
     * Set the Callback interface for this window, used to intercept key
     * events and other dynamic operations in the window.
     *
     * @param callback The desired Callback interface.
     */
    public void setCallback(Callback callback) {
        mCallback = callback;
    }
    public WindowManager getWindowManager() {
        return mWindowManager;
    }
    public abstract View peekDecorView();
    public final void makeActive() { /* ... */ }

```

## Phonewindow implementation

```java
public class PhoneWindow extends Window implements MenuBuilder.Callback {

    // This is the top-level view of the window, containing the window decor.
    private DecorView mDecor; // initialized via installDecor() method, done lazily on need or setContentView
    // This is the view in which the window contents are placed. It is either
    // mDecor itself, or a child of mDecor where the contents go.
    ViewGroup mContentParent;

    @Override
    public final @NonNull View getDecorView() {
        if (mDecor == null || mForceDecorInstall) {
            installDecor();
        }
        return mDecor;
    }

    // This is lazily called when someone requests a decorview or does setContentView
    private void installDecor() {
        mForceDecorInstall = false;
        if (mDecor == null) {
            mDecor = generateDecor(-1);
        } else {
            mDecor.setWindow(this);
        }
        if (mContentParent == null) {
            mContentParent = generateLayout(mDecor);
            // ...
        }
        // ...
        // ...
    }

    // DecorView created here
    protected DecorView generateDecor(int featureId) {
        // System process doesn't have application context and in that case we need to directly use
        // the context we have. Otherwise we want the application context, so we don't cling to the
        // activity.
        Context context;
        if (mUseDecorContext) {
            Context applicationContext = getContext().getApplicationContext();
            if (applicationContext == null) {
                context = getContext();
            } else {
                context = new DecorContext(applicationContext, this);
                if (mTheme != -1) {
                    context.setTheme(mTheme);
                }
            }
        } else {
            context = getContext();
        }
        return new DecorView(context, featureId, this, getAttributes());
    }

    // setContentView layoutId version
    @Override
    public void setContentView(int layoutResID) {
        if (mContentParent == null) {
            installDecor();
        } else if (!hasFeature(FEATURE_CONTENT_TRANSITIONS)) {
            mContentParent.removeAllViews();
        }

        mLayoutInflater.inflate(layoutResID, mContentParent);
        mContentParent.requestApplyInsets();
        final Callback cb = getCallback();
        if (cb != null && !isDestroyed()) {
            cb.onContentChanged();
        }
        mContentParentExplicitlySet = true;
    }

    // setContentView View version
    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if (mContentParent == null) {
            installDecor();
        } else if (!hasFeature(FEATURE_CONTENT_TRANSITIONS)) {
            mContentParent.removeAllViews();
        }

        mContentParent.addView(view, params);
        mContentParent.requestApplyInsets();
        final Callback cb = getCallback();
        if (cb != null && !isDestroyed()) {
            cb.onContentChanged();
        }
        mContentParentExplicitlySet = true;
    }
```

## DecorView

Base View where the content should go
```java
public class DecorView extends FrameLayout implements RootViewSurfaceTaker, WindowCallbacks {
```
