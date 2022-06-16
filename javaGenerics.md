
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


## understanding upper and lower bounds

**Assume type hierarchy is written such that the most general type is on the top, and the most specific types are at the bottom of the hierarchy.**

`? extends A` represents `upper bound` of `A` on all types. meaning any given type must be lower than the upper bound i.e. a speicif/cub type of `A`

`? super A` represents `lower bound` of `A` on all types, meaning any given type must be above or equal to level of `A`.

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
i.e. `List<Pet> <: List<? extends Animal>` for more info check:
http://www.angelikalanger.com/GenericsFAQ/FAQSections/TechnicalDetails.html#Which%20super-subtype%20relationships%20exist%20among%20instantiations%20of%20parameterized%20types?


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

## Declaration with Extends wildcard `? extends Type` is read-only 

that is **`extends` wildcard variable is read-only**

example:
```java
public class Main {
    public static void main(String[] args) {
            List<? extends Animal> animals = new ArrayList<>();
            animals.add(new Animal());// Compile r error! Animal cannot be converted to type CAP1 where CAP1 is fresh type variable, CAP1 extends Animal from capture of ? extends Animal
            animals.add(new Pet());// compiler error: Pet cannot be converted to CAP#1

    }
    
    public static void methodTakesAnimals(List<? extends Animal> as) {   
    }
}

class Animal {
    String name;
}

class Pet extends Animal {
    int age;
}
```

Even when used with methods, same issue is present, 

You can't add any object to `List<? extends T>` because you can't guarantee what kind of List it is really pointing to, so you can't guarantee that the object is allowed in that List. The only "guarantee" is that you can only read from it and you'll get a T or subclass of T.

```java
public class Main {
    public static void main(String[] args) {
            List<Animal> animals = new ArrayList<>();
            methodTakesAnimals(animals);
    }
    
    public static void methodTakesAnimals(List<? extends Animal> as) {   
        as.add(new Animal());// Compiler Error!: Animal cannot be converted to CAP1
    }
}

class Animal {
    String name;
}

class Pet extends Animal {
    int age;
}
```

**Why is it read only?** - because if we allowed adding/writing, somebody could add a `Dog` to a list of `Cats`, thus breaking type safety.

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

## super wildcard bound (lets you modify variable) - super means supertypes are allowed

`List< ? super X >` allows to add anything that is-a X (X or its subtype), or null.

`Assignability` - 

```java
public class Main {
    public static void main(String[] args) {   
            List<? super Animal> as = new ArrayList<Animal>(); // Ok
            List<? super Animal> aas = new ArrayList<Object>(); // Ok
            
            // Compiler error!
            List<? super Animal> aaas = new ArrayList<Pet>(); // Compiler error! ArrayList<Pet> cannot be converted to List<? super Animal> 
    }
}

class Animal {
    String name;
}

class Pet extends Animal {
    int age;
}
```

Another example of assignability: supertypes allowed:
```java
public class Main {
    public static void main(String[] args) {
            List<Creature> csa = new ArrayList<>();
            methodTakesAnimals(csa);
    
            List<Animal> asa = new ArrayList<>();
            methodTakesAnimals(asa);
    
            List<Pet> ps = new ArrayList<>(); 
            methodTakesAnimals(ps);// Compiler error: List<Pet> cannot be converted to List<? super Animal>
    }
    
    public static void methodTakesAnimals(List<? super Animal> animals) {}
}

class Creature {}

class Animal extends Creature {
    String name;
}

class Pet extends Animal {
    int age;
}
```

`Read`: The only guarantee is that you will get an instance of an Object. Not much help with read (very restrictive)

`Boxed Write`: You can write/put only classes that are subtypes of given lower bound.
```java
public class Main {
    public static void main(String[] args) {
            List<? super Animal> asa = new ArrayList<>();
            asa.add(new Pet());// ok
            asa.add(new Animal()); // ok
            asa.add(new Object()); // error! Object cannot be converted to CAP#1 
    }
}

class Animal {
    String name;
}

class Pet extends Animal {
    int age;
}
```


Following are legal
```java
List<? super Integer> foo3 = new ArrayList<Integer>();  // Integer is a "superclass" of Integer (in this context)
List<? super Integer> foo3 = new ArrayList<Number>();   // Number is a superclass of Integer
List<? super Integer> foo3 = new ArrayList<Object>();   // Object is a superclass of Integer
```

## What is capture of wildcard (?) ?


An anonymous type variable that represents the particular unknown type that the wildcard stands for.

usually you will see anonymous names like `CAP1` etc 

## Capture of unbounded wildcard is only assignment compatible to itself

```java
private static void method( List<?> list) {
  List <String>            ll1 = list;     // error
  List <? extends String> ll2 = list;     // error
  List <? super String>    ll3 = list;     // error
  List <?>                 ll4 = list;     // fine
}
error: incompatible types
found   : java.util.List <capture of ? >
required: java.util.List<java.lang.String>
        List<String> ll1 = list;
                           ^
error : incompatible types
found   : java.util.List <capture of ?
required: java.util.List<? extends java.lang.String>
        List<? extends String> ll2 = list;
                                     ^
error : incompatible types
found   : java.util.List <capture of ?
required: java.util.List<? super java.lang.String>
        List<? super String> ll3 = list;
                                   ^
```