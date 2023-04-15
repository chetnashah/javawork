
Read: http://www.bracha.org/mirrors.pdf

### Restrictions on Java Generics


https://docs.oracle.com/javase/tutorial/java/generics/restrictions.html

#### Cannot instantiate generic types with primitive types

The error that we get is: `Type argument cannot be of primitive type`.

```java
Pair<int, char> p = new Pair<>(8, 'a');  // compile-time error
```

But `int[]` is a object type not primitive type so following is possible:
```java
LinkedList<int[]> merged = new LinkedList<>();
```

#### Cannot create instance of type parameter T

Following is not allowed

```java
public static <E> void append(List<E> list) {
    E elem = new E();  // compile-time error
    list.add(elem);
}
```

Why?

Java generics are implemented by erasure which means there will be no parameter type information at runtime. You won't know the T class at runtime (there is not such T.class).

Suggested alternative: - Using type tokens.
A strategy for circumventing some problems with type erasure and Java generics is to use type tokens. This strategy is implemented in the following generic method that creates a new T object:

```java
public <T> T newInstance(Class<T> cls) {
    T myObject = cls.newInstance();
    return myObject;
}
```

For e.g. for above given problem we can use `Class<E>`:
```java
public static <E> void append(List<E> list, Class<E> cls) throws Exception {
    E elem = cls.newInstance();   // OK
    list.add(elem);
}
```

Class literals as runtime type tokens - https://docs.oracle.com/javase/tutorial/extra/generics/literals.html

#### Static fields cannot have a generic type T

```java
public class MobileDevice<T> {
    private static T os; // not allowed

    // ...
}
```

Why?

Because type would be confusing, given different type parameters but single reference.
```java
// whoat will be type of MobileDevice.os ?
MobileDevice<Smartphone> phone = new MobileDevice<>();
MobileDevice<Pager> pager = new MobileDevice<>();
MobileDevice<TabletPC> pc = new MobileDevice<>();
```

#### Cannot create arrays of parametrized types

**You cannot create arrays of parametrized types**. Create lists instead.

```java
List<Integer>[] arrayOfLists = new List<Integer>[2];  // compile-time error
```

Why ?

if we allowed array of lists (of integers) to be created, we cannot guarantee at runtime, 
that if someone is trying to add a non-integer List to our array. (Note ArrayStore exception is a runtime exception and its semantics should be preserved here as well).

The following code illustrates what happens when different types are inserted into an array:
```java
Object[] strings = new String[2];
strings[0] = "hi";   // OK
strings[1] = 100;    // An ArrayStoreException is thrown.
```

If you try the same thing with a generic list, there would be a problem:
```java
Object[] stringLists = new List<String>[2];  // compiler error, but pretend it's allowed
stringLists[0] = new ArrayList<String>();   // OK
stringLists[1] = new ArrayList<Integer>();  // An ArrayStoreException should be thrown,
                                            // but the runtime can't detect it.
```

#### Generic Array creation

Following is not allowed:
```java
public class GenSet<E> {
    private E a[];

    public GenSet() {
        a = new E[INITIAL_ARRAY_LENGTH]; // error: generic array creation
    }
}
```

Alternative like earlier is to use type token instead:
```java
import java.lang.reflect.Array;

class Stack<T> {
    public Stack(Class<T> clazz, int capacity) {
        array = (T[])Array.newInstance(clazz, capacity);
    }

    private final T[] array;
}
```

#### Cannot overload where raw type after erasure is same

e.g.
```java
public class Example {
    public void print(Set<String> strSet) { }
    public void print(Set<Integer> intSet) { }
}
```



### Type erasure of generics

During the type erasure process, the Java compiler erases all type parameters and replaces each with its first bound if the type parameter is bounded, or Object if the type parameter is unbounded.

Consider the following generic class that represents a node in a singly linked list:
```java
public class Node<T> {

    private T data;
    private Node<T> next;

    public Node(T data, Node<T> next) {
        this.data = data;
        this.next = next;
    }

    public T getData() { return data; }
    // ...
}
```
Because the type parameter `T` is unbounded, the Java compiler replaces it with Object:
```java
public class Node {

    private Object data;
    private Node next;

    public Node(Object data, Node next) {
        this.data = data;
        this.next = next;
    }

    public Object getData() { return data; }
    // ...
}
```

### Reifiable types
**A reifiable type is a type whose type information is fully available at runtime. This includes `primitives`, `non-generic types`, `raw types`, and `invocations of unbound wildcards`.**

### Non-reifiable types

Non-reifiable types are types where information has been removed at compile-time by type erasure — invocations of generic types that are not defined as unbounded wildcards. A non-reifiable type does not have all of its information available at runtime. Examples of non-reifiable types are `List<String>` and `List<Number>`; the JVM cannot tell the difference between these types at runtime. As shown in Restrictions on Generics, there are certain situations where non-reifiable types cannot be used: in an instanceof expression, for example, or as an element in an array.

### Effect of type erasure and bridge methods

Sometimes type erasure causes a situation that you may not have anticipated. The following example shows how this can occur. The following example shows how a compiler sometimes creates a synthetic method, which is called a bridge method, as part of the type erasure process.

Given the following two classes:
```java
public class Node<T> {

    public T data;

    public Node(T data) { this.data = data; }

    public void setData(T data) {
        System.out.println("Node.setData");
        this.data = data;
    }
}

public class MyNode extends Node<Integer> {
    public MyNode(Integer data) { super(data); }

    public void setData(Integer data) {
        System.out.println("MyNode.setData");
        super.setData(data);
    }
}
```
Consider the following code:
```java
MyNode mn = new MyNode(5);
Node n = mn;            // A raw type - compiler throws an unchecked warning
n.setData("Hello");     // Causes a ClassCastException to be thrown.
Integer x = mn.data;    
```
After type erasure, this code becomes:
```java
MyNode mn = new MyNode(5);
Node n = mn;            // A raw type - compiler throws an unchecked warning
                        // Note: This statement could instead be the following:
                        //     Node n = (Node)mn;
                        // However, the compiler doesn't generate a cast because
                        // it isn't required.
n.setData("Hello");     // Causes a ClassCastException to be thrown.
Integer x = (Integer)mn.data; 
The next section explains why a ClassCastException is thrown at the n.setData("Hello"); statement.
```

### Bridge methods

When compiling a class or interface that extends a parameterized class or implements a parameterized interface, the compiler may need to create a synthetic method, which is called a bridge method, as part of the type erasure process. You normally don't need to worry about bridge methods, but you might be puzzled if one appears in a stack trace.

After type erasure, the Node and MyNode classes become:
```java
public class Node {

    public Object data;

    public Node(Object data) { this.data = data; }

    public void setData(Object data) {
        System.out.println("Node.setData");
        this.data = data;
    }
}

public class MyNode extends Node {

    public MyNode(Integer data) { super(data); }

    public void setData(Integer data) {
        System.out.println("MyNode.setData");
        super.setData(data);
    }
}
```
After type erasure, the method signatures do not match; the `Node.setData(T)` method becomes `Node.setData(Object)`. As a result, the `MyNode.setData(Integer)` method does not override the `Node.setData(Object)` method.

To solve this problem and preserve the polymorphism of generic types after type erasure, the Java compiler generates a bridge method to ensure that subtyping works as expected.

For the MyNode class, the compiler generates the following bridge method for setData:
```java
class MyNode extends Node {

    // Bridge method generated by the compiler
    //
    public void setData(Object data) {
        setData((Integer) data);
    }

    public void setData(Integer data) {
        System.out.println("MyNode.setData");
        super.setData(data);
    }

    // ...
}
```
The bridge method `MyNode.setData(object)` delegates to the original `MyNode.setData(Integer)` method. As a result, the `n.setData("Hello");` statement calls the method `MyNode.setData(Object)`, and a `ClassCastException` is thrown because "Hello" can't be cast to Integer.

### Void wrapper type - `java.lang.Void`

Only one value allowed for reference type `java.lang.Void`, it is `null`.

https://stackoverflow.com/questions/10839042/what-is-the-difference-between-java-lang-void-and-void

Typically it is useful in generics.
