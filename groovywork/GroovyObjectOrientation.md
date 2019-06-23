From http://groovy-lang.org/objectorientation.html

### Class properties

In groovy terminology, fields differ from property in the way that fields have an access modifier(private, public or protected), where as property do not have an access modifier.

If access modifier is **absent** for fields in a class, Groovy will automatically generate getters and setters. e.g.
```groovy
class Person {
    String name;// no access modifier means private
    Int age;// no access modifier means private
    // automatically generated setAge,getAge,setName,getName
}

Person p1 = new Person();
p1.name = "wow";// implicitly cals p1.setName
p1.setName("chet");// same as above
// write access to the property is done outside of the Person class so it will implicitly call setName
// read access to the property is done outside of the Person class so it will implicitly call getName
```

#### Classes and methods are public by default, properties are private by default

By default, Groovy considers classes and methods public. So you don’t have to use the public modifier everywhere something is public. Only if it’s not public, you should put a visibility modifier.

```groovy
class Server {
    String toString() { "a server" }
}
// is same as
public class Server {
    public String toString() { return "a server" }
}
```


### Traits

Traits can be seen as interfaces carrying both default implementations and state. (so like an abstract class??)

```groovy
trait FlyingAbility {
    String fly() { return "I cacn fly"; }
    private wingColor() { return "unknown"; }// not a part of interface
}

class Bird implements FlyingAbility{
}
def b = new Bird()
assert b.fly() == "I cacn fly"
```



