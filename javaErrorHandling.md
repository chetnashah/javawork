Main class is `Throwable`.

`Throwable` has two subclasses: `Error` and `Exception`.

Applications will tend to throw some instance of `Exception` or `Error` subclasses.

### Throwable

A throwable contains a `snapshot of the execution stack` of its thread at the time it was created. It can also contain a `message string` that gives more information about the error. Over time, a throwable can suppress other throwables from being propagated. Finally, the throwable can also contain a cause: another throwable that caused this throwable to be constructed. The recording of this causal information is referred to as the chained exception facility, as the cause can, itself, have a cause, and so on, leading to a "chain" of exceptions, each caused by another.

Main methods:
1. `getStackTrace(): StackTraceElement[]`: get programmatic access to the stack trace.
2. `printStackTrace()`: directly print stack trace contained for this throwable to stdout.
3. `getMessage()`: string message description of this throwable.
4. `getCause()`: Returns the cause of this throwable or null if the cause is nonexistent or unknown.
5. `fillInStackTrace()`: Fills in the execution stack trace.
6. `initCause(Throwable cause)`: Initializes the cause of this throwable to the specified value.
7. `setStackTrace(StackTraceElement[] stackTrace)`: manipulate currently held stacktrace directly.

### StackTraceElement

A single line/frame in a stack trace.

All stack frames except for the one at the top of the stack represent a method invocation. The frame at the top of the stack represents the execution point at which the stack trace was generated. Typically, this is the point at which the throwable corresponding to the stack trace was created.


Main fields:
1. `getLineNumber()`: Line:column
2. `getFileName()`: source file
3. `getMethodName()`: method name
4. `getClassName()`: class containing the execution point
