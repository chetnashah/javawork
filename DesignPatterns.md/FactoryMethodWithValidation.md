


##

This is a fail fast pattern to achieve early failure and mitigation

##

```java
class Person {
    String name;
    int age;

    private Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // interface to create objects of this type
    public static of(String name, int age) {
        // valiation before construction
        assert(name.equals("") == false);
        assert(age > 0);
        // construction
        return new Person(name, age);
    }
}
```
