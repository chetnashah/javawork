

## isAssignableFrom(Class<?> cls)

Determines if the class or interface represented by `this Class object is either the same as, or is a superclass or superinterface of, the class or interface represented by the specified Class parameter`. 

It returns true if so; otherwise it returns false. If this Class object represents a primitive type, this method returns true if the specified Class parameter is exactly this Class object; otherwise it returns false.

## isInstance(obj)

Determines if the specified Object is assignment-compatible with the object represented by this Class. i.e. `obj is subtype instance of this class.`

This method is the dynamic equivalent of the Java language instanceof operator. 

The method returns true if the specified Object argument is non-null and can be cast to the reference type represented by this Class object without raising a ClassCastException. It returns false otherwise.

```java
public class Main {
    public static void main(String[] args) throws ClassNotFoundException{
        System.out.println("Hello World!");
        
    // Pet <: Animal
    Pet p = new Pet();
    System.out.println("" +Class.forName("Animal").isInstance(p)); // true
    System.out.println(""+Class.forName("Animal").isInstance(p));  // true
        
    Animal a = new Animal();
    System.out.println(""+Class.forName("Pet").isInstance(a));     // false
    System.out.println(""+Class.forName("Pet").isInstance(p));     // true
    }
}

class Animal {
    String name;
}

class Pet extends Animal{
    int age;
}
```
