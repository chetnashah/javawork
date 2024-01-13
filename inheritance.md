
## Constructors

**Superclass should always be initialized/constructed before derived class**

https://stackoverflow.com/a/527069/5130114

1. If you do not make a constructor, the default empty constructor is automatically created. - https://docs.oracle.com/javase/specs/jls/se7/html/jls-8.html#jls-8.8.9

2. **If any constructor does not explicitly call a super or this constructor as its first statement, a call to super() is automatically added.** - https://docs.oracle.com/javase/specs/jls/se7/html/jls-8.html#jls-8.8.7

3. **If there are no default constructors/no-arg constructors in base class (i.e a parametrized constructor is present in Base), you must init Base class in derived class constructor** -

```java
class Base {
    int m_id;
    Base(int id) {
        m_id = id;
    }
}

class Derived extends Base {
    String m_str;
    Derived(String str) { // COMPILER Error! - There is no default constructor available in 'Base', To fix: use super(1) to call parameterized Base constructor
        m_str = str;
    }
}
```

## Abstract classes

**Methods may or not be abstract, properties cannot be marked abstract.**

```java
abstract class GraphicObj {
    int prop;
    abstract void draw();
    int sum(int a, int b){
        return a + b;
    }
}
```

### `this` in inheritance is same

`this` is same in child class and parent class, when `super` is called.
This idea is useful in case of `synchronized` on inherited methods, since
intrinsic locks are re-entrant.

```java
package tryingoutokhttp;

class Bike {

    // the Bicycle class has three fields
    public int cadence;
    public int gear;
    public int speed;

    // the Bicycle class has one constructor
    public Bike(int startCadence, int startSpeed, int startGear) {
        gear = startGear;
        cadence = startCadence;
        speed = startSpeed;
        System.out.println("Bike constructor this = " + this);
    }

    // the Bicycle class has four methods
    public void setCadence(int newValue) {
        cadence = newValue;
    }

    public void setGear(int newValue) {
        gear = newValue;
    }

    public void applyBrake(int decrement) {
        speed -= decrement;
    }

    public void speedUp(int increment) {
        speed += increment;
    }

    public void doSomething(){
        System.out.println("Bike doing something " + this);
    }

}

public class MountainBike extends Bike {

    // the MountainBike subclass adds one field
    public int seatHeight;

    // the MountainBike subclass has one constructor
    public MountainBike(int startHeight,
                        int startCadence,
                        int startSpeed,
                        int startGear) {
        super(startCadence, startSpeed, startGear);
        seatHeight = startHeight;
        System.out.println("MountainBikeConstructor this = " + this);
    }

    // the MountainBike subclass adds one method
    public void setHeight(int newValue) {
        seatHeight = newValue;
    }

    @Override
    public void doSomething(){
        super.doSomething();
        System.out.println("MountainBIke doing something "+ this);
    }
}
```


### All methods are virtual

Method is invoked based on instance type, not the reference type.


### @Override
`@Override` annotation informs the compiler that the element is meant to override an element declared in a superclass. Overriding methods will be discussed in Interfaces and Inheritance. While it is not required to use this annotation when overriding a method, it helps to prevent errors

Why use it?
1. Do it so that you can take advantage of the compiler checking to make sure you actually are overriding a method when you think you are.
2. If the parent class changes, the compiler will make sure that the child classes have been updated as well

Is override keyword mandatory?
**No, Using Override is not mandatory, but recommended.**

Here is an example that runs fine:
```java
import java.util.*;
public class MyClass {
    public static void main(String args[]) {
        Main m = new Main();
        System.out.println(m.message());
    }
}

class MainClass {
    String message(){
        return "Hello!";
    }
}

class Main extends MainClass {
    String message(){
        return "World!";
    }
}
```

### is `@override` necessary?
It is not necessary, but it provides good benefits.

1. conveys explicitly to human reader that this is an overriding method, and
2. catches a bug at compile time that could take at least a few brain cycles to catch at run-time once you even know to look for it


### Auto super insertion in subclass constructor

If parent has a no-arg constructor, and we are missing call to super constructor in subclass constructor,
compielr with auto insert `super()` statement as first line of subclass constructor.

```java
// valid java code
class Animal {
    public Animal() {
        System.out.println("Animal constructor");
    }
}

class Cat extends Animal {
    public Cat(){
        // automatic insert call to super() by compiler
        System.out.println("I am cat constructor");
    }
}

public class ClassSuperTest {
    public static void main(String[] args) {
        Cat c = new Cat();
    }
}
//Animal constructor
//I am cat constructor
```

This case becomes invalid and auto-insertion of super constructor call is not done if 
parent class has nonzero arg constructor

```java
// COMPILER ERROR!
class Animal {
    public Animal(int k) {
        System.out.println("Animal constructor");
    }
}

class Cat extends Animal {
    public Cat(){
        // COMPLIER ERROR: There is no default constructor available in 'Animal'
        // super(10); // this will fix the compiler error: explicit super constructor initialization
        System.out.println("I am cat constructor");
    }
}

public class ClassSuperTest {
    public static void main(String[] args) {
        Cat c = new Cat();
    }
}

```


## Static and inheritance

Static methods in Java are inherited, but can not be overridden. 

If you declare the same method in a subclass, you hide the superclass method instead of overriding it.

compiler will decide which method to execute at the compile time, and not at the runtime, as it does with overridden instance methods.

Overriding simply means runtime polymorphism based on object type, but it does not make much sense for statics, since
you are always supposed to use the statics with class Type instead of object references.

