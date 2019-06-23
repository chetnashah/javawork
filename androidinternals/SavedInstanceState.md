
What should be saved in instanceState?

1. user saved data in form.
2. scroll view positions
3. user selections

What should not be saved in bundle

1. files
2. images
3. videos
4. models (should be persisted to db)

Why savedInstanceState ?

There is one situation where using the savedInstanceState is almost mandatory: when the Activity gets destroyed during rotation. One way that Android handles rotation is to completely destroy and then re-create the current Activity. When the Activity is being destroyed, any state related information that is saved in onSaveInstanceState will be available when the Activity comes back online.

**Note**:  In the event that the OS kills your Activity without killing your Application, the OS will first save your outState Bundle so you can later return to your previous state.

### Serializable vs Parcelable

In Android we cannot just pass objects to activities. To do this the objects must either implement Serializable or Parcelable interface.

Parcelable process is much faster than Serializable. One of the reasons for this is that we are being explicit about the serialization process instead of using reflection to infer it.

