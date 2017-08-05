
In Java, there are two types of exceptions: checked and unchecked.
Checked exceptions extend java.lang.Exception . We have to wrap meth-
ods that might throw an exception in a try/catch block. For example, the
FileReader constructor will throw a FileNotFoundException if you pass in a
filename that doesn’t exist. Unchecked exceptions extend java.lang.Error
or java.lang.RuntimeException . Exceptions such as NullPointerException ,
ClassCastException , and IndexOutOfBoundsException might be thrown by
a method, but the compiler doesn’t require you to wrap them in a
try/catch block. The Javadoc for java.lang.Error says that we don’t need to
catch these sorts of exceptions “since these errors are abnormal condi-
tions that should never occur.”
