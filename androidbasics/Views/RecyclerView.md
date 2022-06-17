
No matter what the content is, displaying a list of data is one of the most common UI tasks in Android

## Working

When a list item is scrolled off the screen, RecyclerView reuses that view for the next list item about to be displayed. That means, the item is filled with new content that scrolls onto the screen.

## ADapter

Preparing `data` source to be used by Recyclerview, whenever it wants to get data for particular index in the list

Adapter has callback methods to do the view inflation and data plumibing to views
```kotlin
// get list data from outside via constructor
class ItemAdapter(private val context: Context, private val dataset: List<Affirmation>): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    // Inner Viewholder class: get view in holder from from outside via constructor
    class ItemViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.item_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.textView.text = context.resources.getString(dataset[position].stringResourceId)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}
```

## ViewHolders

A class that holds A pool of views for RecyclerView to use and reuse to display list items. This class constructor take a list level rootview, and holds public members that are references to useful items that we want to set in list view e.g. list title, list image tc. These public references are populated during viewholder creation via viewholder constructor which is given a fresh list-item-root-view by inflating from xml, 

View holders are update during viewholder updation. The pattern is known as viewholder pattern.

## LayoutManager

controls the layout of items: 
1. `LinearLayoutManager` - in vertical order
2. `GridLayoutManager` - in a grid order

## androidx.recyclerView.widget.ListAdapter

This is different from android framework Listadapter (`frameworks/base/core/java/android/widget/ListAdapter.java`)

`ListAdapter` is a abstract subclass of the `RecyclerView.Adapter` class for presenting List data in a `RecyclerView`, including computing diffs between Lists on a background thread.

This class is a convenience wrapper around `AsyncListDiffer` that implements Adapter common default behavior for item access and counting.

Using `ListAdapter`:
You specify the entity type (Whose list we want to adapt to UI), and the corresponding EntityViewHolder e.g.

Definition
```
// T is entity/model Type parameter, VH is viewholder type parameter
androidx.recyclerview.widget.ListAdapter<T, VH extends androidx.recyclerview.widget.RecyclerView.ViewHolder>
```

```java
 class UserAdapter extends ListAdapter<User, UserViewHolder> {
     // ...
}
```

`submitList(List<T> list)` - useful method for list data change to adapter. This is useful in cases when you get new/fresh list data from an async source like network/database etc.
```
void	submitList(List<T> list)
Submits a new list to be diffed, and displayed.
```

In constructor you must call super class with an instance of DiffUtil `ItemCallback`.
e.g.
```java
 class UserAdapter extends ListAdapter<User, UserViewHolder> {
     public UserAdapter() {
         super(User.DIFF_CALLBACK);
     }
     @Override
     public void onBindViewHolder(UserViewHolder holder, int position) {
         holder.bindTo(getItem(position));
     }
     public static final DiffUtil.ItemCallback<User> DIFF_CALLBACK =
             new DiffUtil.ItemCallback<User>() {
         @Override
         public boolean areItemsTheSame(
                 @NonNull User oldUser, @NonNull User newUser) {
             // User properties may have changed if reloaded from the DB, but ID is fixed
             return oldUser.getId() == newUser.getId();
         }
         @Override
         public boolean areContentsTheSame(
                 @NonNull User oldUser, @NonNull User newUser) {
             // NOTE: if you use equals, your object must properly override Object#equals()
             // Incorrectly returning false here will result in too many animations.
             return oldUser.equals(newUser);
         }
     }
 }
```

### submitList() usage

// submitList() is a call that accesses the database. To prevent the
// call from potentially locking the UI, you should use a
// coroutine scope to launch the function. Using GlobalScope is not
// best practice, and in the next step we'll see how to improve this.
```kt
GlobalScope.launch(Dispatchers.IO) {
   busStopAdapter.submitList(viewModel.fullSchedule())
}
```

## DiffUtil ItemCallback

```java
     public static final DiffUtil.ItemCallback<User> DIFF_CALLBACK =
             new DiffUtil.ItemCallback<User>() {
         @Override
         public boolean areItemsTheSame(
                 @NonNull User oldUser, @NonNull User newUser) {
             // User properties may have changed if reloaded from the DB, but ID is fixed
             return oldUser.getId() == newUser.getId();
         }
         @Override
         public boolean areContentsTheSame(
                 @NonNull User oldUser, @NonNull User newUser) {
             // NOTE: if you use equals, your object must properly override Object#equals()
             // Incorrectly returning false here will result in too many animations.
             return oldUser.equals(newUser);
         }
     }
```

## Another example of ListAdapter based Recyclerview adapter

```kt

class PhotoGridAdapter : ListAdapter<MarsPhoto, PhotoGridAdapter.MarsPhotoViewHolder>(DiffCallback) {
    companion object DiffCallback : DiffUtil.ItemCallback<MarsPhoto>() {
        override fun areItemsTheSame(oldItem: MarsPhoto, newItem: MarsPhoto): Boolean {
            return oldItem.imgSrc == newItem.imgSrc
        }

        override fun areContentsTheSame(oldItem: MarsPhoto, newItem: MarsPhoto): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class MarsPhotoViewHolder(private var binding: GridViewItemBinding): RecyclerView.ViewHolder(binding.root) {
        val imgView = binding.marsImage

        fun bind(MarsPhoto: MarsPhoto) {
            binding.photo = MarsPhoto
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarsPhotoViewHolder {
        val binding = GridViewItemBinding.inflate(LayoutInflater.from(parent.context))
        return MarsPhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MarsPhotoViewHolder, position: Int) {
        val marsPhoto = getItem(position)
        holder.bind(marsPhoto)
    }
}
```

## AsyncListDiffer

Helper for computing the difference between two lists via `DiffUtil` on a background thread.

It can be connected to a `RecyclerView.Adapter`, and will signal the adapter of changes between sumbitted lists.

For simplicity, the `ListAdapter` wrapper class can often be used instead of the `AsyncListDiffer` directly. This `AsyncListDiffer` can be used for complex cases, where overriding an adapter base class to support asynchronous List diffing isn't convenient.

Creation:
```java
class SomeAdapter extends RecyclerView.Adapter{
    //this passed in constructor is the Adapter
     private final AsyncListDiffer<User> mDiffer = new AsyncListDiffer(this, DIFF_CALLBACK);

     public static final DiffUtil.ItemCallback<User> DIFF_CALLBACK
             = new DiffUtil.ItemCallback<User>() {
         @Override
         public boolean areItemsTheSame(
                 @NonNull User oldUser, @NonNull User newUser) {
             // User properties may have changed if reloaded from the DB, but ID is fixed
             return oldUser.getId() == newUser.getId();
         }
         @Override
         public boolean areContentsTheSame(
                 @NonNull User oldUser, @NonNull User newUser) {
             // NOTE: if you use equals, your object must properly override Object#equals()
             // Incorrectly returning false here will result in too many animations.
             return oldUser.equals(newUser);
         }
     }
}
```


## ViewHolder subclass convention : usually as an inner class of Adapter class


## ViewHolder constructor convention: accept a ItemLayoutBinding in ViewHolder subclass constructor and make it as a property of the viewholder class as a private var

```kt
// notice constructor param is ItemyoutBinding
class BusStopViewHolder(private var binding: BusStopItemBinding): RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SimpleDateFormat")
    fun bind(schedule: Schedule) {
        binding.stopNameTextView.text = schedule.stopName
        binding.arrivalTimeTextView.text = SimpleDateFormat(
            "h:mm a").format(Date(schedule.arrivalTime.toLong() * 1000)
        )
    }
}
```

## ViewHolder convention: add a `bind` method in viewholder sub-class

`fun bind(entity: Entity)`: Job of this function is to setup all the UI fields in this viewholder accoridng to the entity data.

```kt
class BusStopViewHolder(private var binding: BusStopItemBinding): RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SimpleDateFormat")
    fun bind(schedule: Schedule) {
        binding.stopNameTextView.text = schedule.stopName
        binding.arrivalTimeTextView.text = SimpleDateFormat(
            "h:mm a").format(Date(schedule.arrivalTime.toLong() * 1000)
        )
    }
}
```

## Where/when to setup item listeners inside adapter?

`onCreateViewHolder`