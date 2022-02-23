
A primary constructor is the one that constructs an object and encapsulates other objects inside it. 

A secondary one is simply a preparation step before calling a primary constructor and is not really a constructor but rather an introductory layer in front of a real constructing mechanism

Java has known to have this pattern,
because Java is known to have no support for default parameters in constructors

```java
final class Cash {
  private final int cents;
  private final String currency;
  public Cash() { // secondary
    this(0);
  }
  public Cash(int cts) { // secondary
    this(cts, "USD");
  }
  public Cash(int cts, String crn) { // primary constructor, maximum arguments
    this.cents = cts;
    this.currency = crn;
  }
  // methods here
}
```

There are three constructors in the class—only one is primary and the other two are secondary. 
My definition of a secondary constructor is simple: It doesn’t do anything besides calling a primary constructor, through `this(..)`.



