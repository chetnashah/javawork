
No matter what the content is, displaying a list of data is one of the most common UI tasks in Android

## Working

When a list item is scrolled off the screen, RecyclerView reuses that view for the next list item about to be displayed. That means, the item is filled with new content that scrolls onto the screen.

## ADapter

Preparing `data` source to be used by Recyclerview, whenever it wants to get data for particular index in the list

## ViewHolders

A pool of views for RecyclerView to use and reuse to display list items.

## LayoutManager

controls the layout of items: 
1. `LinearLayoutManager` - in vertical order
2. `GridLayoutManager` - in a grid order

