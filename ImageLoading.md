

## Image loading is a hard problem due to following reasons

Displaying a photo from a web URL might sound straightforward, but there is quite a bit of engineering to make it work well. 

The image has to be downloaded, internally stored, and decoded from its compressed format to an image that Android can use. (network bandwidth) 

The image should be cached to an in-memory cache, a storage-based cache, or both. (latency)

All this has to happen in low-priority background threads so the UI remains responsive. (CPU/batter/responsiveness)

Also, for best network and CPU performance, you might want to fetch and decode more than one image at once. (parellelism)


