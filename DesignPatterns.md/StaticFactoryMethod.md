

When a static creation method returns new objects it becomes an alternative constructor.

## Useful when:

1. have several different constructors that have different purposes but their signatures match.
2. caching/reuse of instances instead of always creating new ones via constructor.

## Entity itself holds a static method to create an object of that type.

Usually a static method named `static Entity from(paramsToConstructoObject)`.

Since it is a static method, it is common to Entity class/instances. Creators of this object need only the class, not the object instances.

### How is this different from using a constructor?

Here the `new()` keyword/call is in control of the static factory method,
instead of the clients who want to instantiate an object.


### Need for subclasses ? (No, that is factory method pattern, not static factory method)

**Factory method pattern is different from static factory method.**

**If you make factory method static, you can no longer extend it in subclasses**



