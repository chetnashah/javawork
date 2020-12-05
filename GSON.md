
### toJson

`toJson`: `Object` -> `String`, takes an object and returns a string json representation of the same.

By default all fields of object are included for serialization.

```java
class BagOfPrims {
    private int val1 = 1;
    private String val2 = "abc";
    BagOfPrims(){
        // no args constructor
    }
}
```

#### null handling in toJson

1. If any fields within the object have null value for a key, they are ignored in the serialization.

2. while deserialization, missing entry in JSON results in setting corresponding 
field in object to its default value: 
    a. null for object types
    b. zero for numeric types
    c. false for booleans

### fromJson

Takes a string and a class, and returns a deserialized instance of string according
to the class.
e.g.
```java
BagOfPrims obj2 = gson.fromJson(jsonStr, BagOfPrims.class);
```

If the json-string itself contains `null` as a value for a field,
Gson will override default value in the POJO class and set it to `null`.

if json-string has a field missing that is present POJO class it will be given default values: null for Object type, 0 for numeric types, false for booleans.

### GsonBuilder

In order to configure gson, you must create Gson instance using GsonBuilder 
e.g.
```java
Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .serializeNulls()
        .create();
```

### Deserialization of parametric types

```java
class Foo<T> { // Foo is a parametric type e.g. say List
    T value;
}
Gson gson = new Gson();
Foo<Bar> foo = new Foo<Bar>();// foo.value type is Bar
gson.toJson(foo); // may not serialize foo.value correctly

gson.fromJson(json, Foo.getClass()); // fails to deserialize foo.value as Bar
```

To solve this problem, you must use `TypeToken`.
```java
Type fooType = new TypeToken<Foo<Bar>>(){}.getType();
gson.fromJson(jsonStr, fooType);
```

e.g. for deserializing a list of users
```java
TypeToken userListType = new TypeToken<List<User>>(){}.getType();
List<User> allUsers = new Gson().fromJson(jsonStr, userListType);
```

This TypeToken trick is only needed when toplevel JSON is an array of values,
not needed when top level is an object value, where a simple class will suffice.

### SerializedName

It is a gson annotation, that should be used like `@SerializedName` on a field
when you want different name in JSON compared to your POJOs.

e.g.
```java
class User {
    @SerializedName("name")
    String fullName;

    @SerializedName("id")
    String mId;
}
```

### Custom Serialization and Deserialization

