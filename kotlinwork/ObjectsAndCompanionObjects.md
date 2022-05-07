
## resources
https://www.youtube.com/watch?v=KUk6k865Vgg
https://kotlinlang.org/docs/object-declarations.html#using-anonymous-objects-as-return-and-value-types

An `object declaration` inside a class can be marked with the companion keyword, known as `companion object`.
Syntactically it's similar to the static methods in Java: you call object members using its class name as a qualifier.
Note it will be different from `object expression` which can appear on right hand side of an assignment.

## object expression

anonymous class

## object declaration

class with collection of static members.
You simply get a singleton without any extra effort.

```kotlin
object ForeverAlone {
    var playCount: Int = 0
    fun singLonelySong() {
        println("Lonely, I'm so lonely!")
        playCount++
    }
}
```
decompiles to 
```java
public final class ForeverAlone {
   private static int playCount;
   @NotNull
   public static final ForeverAlone INSTANCE;

   public final int getPlayCount() {
      return playCount;
   }

   public final void setPlayCount(int var1) {
      playCount = var1;
   }

   public final void singLonelySong() {
      String var1 = "Lonely, I'm so lonely!";
      System.out.println(var1);
      int var10000 = playCount++;
   }

   private ForeverAlone() {
   }

   static {
      ForeverAlone var0 = new ForeverAlone();
      INSTANCE = var0;
   }
}
```

## companion object - Companion is an inner class that can extend/implement interfaces

`public static final class Companion` or `public static final class NamedComapnion` is generated for
companion object declaration.
Its constructor is private and only accessible/via internals.

## name of class is treated as companion object reference, if companion object exists in class

The name of a class used by itself (not as a qualifier to another name) acts as a reference to the companion object of the class.

```kt
class MyClass {
    companion object Something { // named
        fun doSomething() {
            println("doing something")
        }
    }
}

fun main() {    
    val m = MyClass // returns reference to companion object if present
    m.doSomething() // doing something
}
```


## Using Companion objects (named and un-named)

Can be named or un-named
e.g.
Named companion object:
`Static inner class` with name `SomeCreator` is made.
```kt
class MyClass {
    companion object Something { // named
        fun doSomething() {
            println("doing something")
        }
    }
}

fun main() {    
    MyClass.doSomething() // doing something 
    MyClass.Something.doSomething() // doing something
}
```
1. Note: you can skip referencing the name of companion and directly use those properties/methods on class since they are static.

`Un-named companion object`:
Static inner class with name `Companion` is made.

```kt
class MyAnotherClass {
    companion object { // unnamed
        fun doSomething(){
            println("doing something2")
        }
    }
}

fun main() {        
    MyAnotherClass.doSomething() // doing something2
    MyAnotherClass.Companion.doSomething() // doing something2
}
```
Note: you can skip referencing the `Companion` and directly use those properties/methods on class since they are static.


### @JvmStatic annotation

on the JVM you can have members of companion objects generated as real static methods and fields if you use the `@JvmStatic` annotation.
Use kotlin to java decompiler to get a deeper dive. 
Menu bar > Tools > Kotlin > SHow kotlin bytecode > Decompile

This will cause the companion members to be declared as static members on outer enclosing class (which does not happen by default in all cases )