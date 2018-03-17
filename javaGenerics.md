
### Generic array creation not allowed

When creating new arrays, array needs to know the component types.

``` java
T[] items = new T[10]; // throws an error
// instead use below
T[] items = (T[]) new Object[10];
```

### Sub-typing

Sub-types are more specific, where as supertypes are more general.
 If S is a subtype of T, the subtyping relation is often written S <: T, to mean that any term of type S can be safely used in a context where a term of type T is expected

### Arrays are co-variant, Lists are not.

Lets say Animal is base class, Dog is a derived class. so Dog is a sub-type of Animal.
Liskov Substitution principle: Wherever an Animal can be used, a dog can be used.

i.e Dog <: Animal


Then covariance: Dog[] <: Animal[]

List is invariant: i.e List<Dog> is not a subtype of List<Animal>

Instead the designer should Have used something like following
``` java
// bounding type parameter to be a sub-type of Animal
void <T extends Animal> dosomething(T item){
    // doing something
}

doSomething(listOfAnimals);
doSomething(listOfDogs); // works fine
```

`List<Dog>` is a subtype of `List<T extends Animal>` (Type bounds)

### What is bounded type parameter?

There may be times when you want to restrict the types that can be used as type arguments in a parameterized type. For example, a method that operates on numbers might only want to accept instances of Number or its subclasses. This is what bounded type parameters are for. e.g. use of super or extends in type parameter.

### When to use 'super' vs 'extends' in type bounds?



### What is a wildcard in Java?

The question mark "?" is known as wildcard.

It stands for **for all unknown types**.


### Use case for unbounded wildcard.

Unbounded wildcard is represented as `List<?> l`.

Let's say we want a method that works on all lists:
i.e.
``` java
public static void printList(List<Object> list) {
    for (Object elem : list)
        System.out.println(elem + " ");
    System.out.println();
}
```
But above method will only accept List of Objects because e.g.
`List<Car>` is not a subtype of `List<Object>`.

One can Either try `List<T extends Object>` or just `List<?>`.

There are many situations where you simply don't care what type you are referring to. In those cases, you may use ? without cluttering code with unused type parameter declarations.

