

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