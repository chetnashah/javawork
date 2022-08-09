

## Class 

Instance of this type represent classes and interfaces in a running Java application.

**Class has no public constructor. Instead Class objects are constructed automatically by the Java Virtual Machine as classes are loaded and by calls to the defineClass method in the class loader**

## Class for primitive int and others

https://stackoverflow.com/questions/22470985/integer-class-vs-int-class

`Integer.class` is, as you say, a reference to the `Class` object for the `Integer` type.

`int.class` is, similarity, a reference to the `Class` object for the `int` type. You're right that this doesn't sound right; **the primitives all have a Class object as a special case**. It's useful for reflection, if you want to tell the difference between foo(Integer value) and foo(int value).

`int.class` its of type `Class<Integer>` but its different to Integer.class obviously

Integer.TYPE (not Integer.type, mind you) is just a shortcut for `int.class`.


### Getting class reference from an object instantiation of that class

```java
     void printClassName(Object obj) {
         System.out.println("The class of " + obj +
                            " is " + obj.getClass().getName());
     }
```

### Getting class reference using Class literal syntax way (by appending `.class` to type name)

class literal, formed by taking a type name and appending `.class`; for example, `String.class`.

```java
Class<String> c = String.class;
```


### Gettig class reference using `Class.forName(typeNameString)`

```java
Class<String> c = Class.forName("String");
```


### Create class Instance using `class.newInstance()`

`newInstance(): T` = Creates a new instance of the class represented by this Class object.

## Constructor

`Constructor` provides information about, and access to, a single constructor for a class.

`constructor.newInstance(Object... initargs)`

Uses the constructor represented by this Constructor object to create and initialize a new instance of the constructor's declaring class, with the specified initialization parameters.



## Method

A `Method` provides information about, and access to, a single method on a class or interface. The reflected method may be a class method or an instance method (including an abstract method).

## Type

Type is the common superinterface for all types in the Java programming language. 

These include raw types, parameterized types, array types, type variables and primitive types.

Has only one method: `default String	getTypeName()`
Returns a string describing this type, including information about any type parameters.

## Field

A `Field` provides information about, and dynamic access to, a single field of a class or an interface. The reflected field may be a class (static) field or an instance field.

### field.get(obj) - return field value on given obj

`Object	get(Object obj)`
Returns the value of the field represented by this Field, on the specified object.

### Set field value

`void	set(Object obj, Object value)`
Sets the field represented by this Field object on the specified object argument to the specified new value.

## Member

Member is an interface that reflects identifying information about a single member (a field or a method) or a constructor.

