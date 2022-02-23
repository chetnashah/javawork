
This also has state and management methods related to Activity Stack/Tree.

```java
/**
 * Defines common functionality for classes that can hold windows directly or through their
 * children in a hierarchy form.
 * The test class is {@link WindowContainerTests} which must be kept up-to-date and ran anytime
 * changes are made to this class.
 */
class WindowContainer<E extends WindowContainer> extends ConfigurationContainer<E>
        implements Comparable<WindowContainer>, Animatable, SurfaceFreezer.Freezable {
    /**
     * The parent of this window container.
     * For removing or setting new parent {@link #setParent} should be used, because it also
     * performs configuration updates based on new parent's settings.
     */
    private WindowContainer<WindowContainer> mParent = null;

    // List of children for this window container. List is in z-order as the children appear on
    // screen with the top-most window container at the tail of the list.
    protected final WindowList<E> mChildren = new WindowList<E>();
    /**
     * Windows that clients are waiting to have drawn.
     */
    final ArrayList<WindowState> mWaitingForDrawn = new ArrayList<>();
    /**
     * Used as a unique, cross-process identifier for this Container. It also serves a minimal
     * interface to other processes.
     */
    RemoteToken mRemoteToken = null;
}
```

### Method to find an activity

```java
    /**
     * Gets an activity in a branch of the tree.
     *
     * @param callback called to test if this is the activity that should be returned.
     * @param boundary We don't return activities via {@param callback} until we get to this node in
     *                 the tree.
     * @param includeBoundary If the boundary from be processed to return activities.
     * @param traverseTopToBottom direction to traverse the tree.
     * @return The activity if found or null.
     */
    final ActivityRecord getActivity(Predicate<ActivityRecord> callback,
            WindowContainer boundary, boolean includeBoundary, boolean traverseTopToBottom) {
        return getActivity(
                callback, boundary, includeBoundary, traverseTopToBottom, new boolean[1]);
    }

```