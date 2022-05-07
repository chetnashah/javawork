

A Bundle is a collection of data, stored as key/value pairs.

An Intent can include data on which to perform an action (as a URI) or additional information as Intent extras.
Intent extras are key/value pairs in a Bundle that are sent along with the Intent.

## Implicit intent

An implicit intent is a bit more abstract, where you tell the system the type of action, such as opening a link, composing an email, or making a phone call, and the system is responsible for figuring out how to fulfill the request.


### What can be passed inside intents?

1. simple primitives like string, int etc via `Intent.putExtra(...)`
2. via `Bundle` which can be added to intent via `Intent.putExtras` method
```kt
    val b = Bundle()
    b.putString("anotherextra", "hey")
    i.putExtras(b)
```
3. any parcelable data

### when to use intent extras?

Use the Intent extras:

1. If you want to pass more than one piece of information to the started Activity.
2. If any of the information you want to pass is not expressible by a URI.

### setting/getting URI on an intent

Uri data goes well with actions, and used as primary source of data/info passed in an intent.

Use the `intent.setData()` method with a Uri object to add that URI to the Intent
To get Uri data from intent use `intent.getData()` method:
```java
Uri locationUri = intent.getData();
```

### intent extras vs Uri

`Uri` data in intent should be treated as a primary action item to work with, and extras should be treated
as metadata hashmap to work with it.

### Explicit intents
Take class reference and a context


### Implicit intents 
only need action