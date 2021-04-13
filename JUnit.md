

### JUNit test

An annotation for method that has to be tested.
All test annotated methods are wrapped into an interface known as `Statement`.

### JUNit Statement
Interface with one method `evaluate`.
wraps a JUnit Test.
Statement represents our test at runtime. when `evaluate` of the Statement is called,
our test code is run.

### JUnit TestRule

Affects all tests!
A `TestRule` is an change in how a test method/methods is run and reported.
It is an interface with a single method `apply : Statement -> Statement`.
A `TestRule` may add additional checks that cause a test to either fail/pass, or perform necessary setup/cleanup or observe execution to report elsewhere.
The `Statement` that executes the method or suite is passed to each annotated `Rule` in turn, and each may return a substitute or modified `Statement` which is passed to
the next `Rule` if any.
Think of it like a middleware but for tests/statements. e.g rules can modify tests,
rules can be chained.

An example rule:
```java
public class NullRule implements TestRule {
    public Statement apply(final Statement s, Description d) {
        return new Statement() {
            public void evaluate() throws Throwable {
                s.evaluate();
            }
        }
    }
}
```

Common TestRules: `ErrorCollector`: collect multiple error in one test method
`ExpectedException`: make flexible assertions about thrown exceptions
`ExternalResource`: start and stop a server for example
`TemporaryFolder`: create fresh files, and delete after test
`TestWatcher`: add logic at events during method execution
`Timeout`: cause test to fail after a set time.
`Verifier`: fail test if object state ends up incorrect.
`TestName`: remeber the test name used for execution.

`RuleChain`: Takes a list of rules and takes care of their execution order.

