
No matter what the content is, displaying a list of data is one of the most common UI tasks in Android

## Working

When a list item is scrolled off the screen, RecyclerView reuses that view for the next list item about to be displayed. That means, the item is filled with new content that scrolls onto the screen.

## ADapter

Preparing `data` source to be used by Recyclerview, whenever it wants to get data for particular index in the list

Adapter has callback methods to do the view inflation and data plumibing to views
```kotlin
class ItemAdapter(private val context: Context, private val dataset: List<Affirmation>): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
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

