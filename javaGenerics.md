
### Generic array creation not allowed

When creating new arrays, array needs to know the component types.

``` java
T[] items = new T[10]; // throws an error
// instead use below
T[] items = (T[]) new Object[10];
```

### Sub-typing

Sub-types are more specific, where as supertypes are more general.
 If S is a subtype of T, the subtyping relation is often written `S <: T` or `S extends T`, to mean that any term of type S can be safely used in a context where a term of type T is expected

### Arrays are co-variant, Lists are not.

Lets say Animal is base class, Dog is a derived class. so Dog is a sub-type of Animal.
Liskov Substitution principle: Wherever an Animal can be used, a dog can be used.

i.e Dog <: Animal


Then covariance: `Dog[] <: Animal[]`

List is invariant: i.e `List<Dog>` is not a subtype of `List<Animal>`

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

The primary need for bounded types is to work around the fact the collections etc are invariant, and cannot accept subtypes/supertypes, so inorder to make them flexible, types are given bounds. For example, a method that operates on numbers might only want to accept instances of Number or its subclasses. This is what bounded type parameters are for. e.g. use of super or extends in type parameter.


### When to use 'super' vs 'extends' in type bounds?



### What is a wildcard in Java?

The question mark "?" is known as wildcard.

It stands for **for all unknown types**.


### Use case for unbounded wildcard.

Unbounded wildcard is represented as `List<?> l`, i.e. no bounds added using super or extends.

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

## direct list of subtypes/types is invariant

Error: `List<Pet>` cannot be converted to `List<Animal>`
e.g.
```java
// "static void main" must be defined in a public class.
public class Main {
    public static void main(String[] args) {
    
            List<Animal> as = new ArrayList<>();
            List<Pet> ps = new ArrayList<>();
        
            methodTakesAnimals(ps);
    }
    
    public static void methodTakesAnimals(List<Animal> as) {
        
    }

}

class Animal {
    String name;
}

class Pet extends Animal {
    int age;
}
```

To fix this one must add wildcard bound to make `List` or generic types variant
i.e. `List<Pet> <: List<? extends Animal>`
```java
public class Main {
    public static void main(String[] args) {
    
            List<Animal> as = new ArrayList<>();
            List<Pet> ps = new ArrayList<>();
        
            methodTakesAnimals(ps);
    }
    
    public static void methodTakesAnimals(List<? extends Animal> as) {// now List<Pet> <: List<Animals>
        
    }

}

class Animal {
    String name;
}

class Pet extends Animal {
    int age;
}
```

## Producer Extends consumer super

https://howtodoinjava.com/java/generics/java-generics-what-is-pecs-producer-extends-consumer-super/

### Problem

This method is responsible for adding all members of collection `c` into another collection where this method is called.

```
boolean addAll(Collection<? extends E> c);
```
This method is called for adding “elements” to collection “c”.
```
public static <T> boolean addAll(Collection<? super T> c, T... elements);
```
Both seems to be doing simple thing, so why they both have different syntax