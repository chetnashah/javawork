

A decent overview is JavaOne talk :
https://www.youtube.com/watch?v=_cFwDnKvgfw

1. It is possible to call native code from Java (Mostly used case):

On java side :
  declare the methode to be called wit modifier native.
  Use System.LoadLibrary("libXyz.so")
    How will System.loadlibrary look for my xyz.so ?
      1. default search path
      2. Environment LD_LIBRARY_PATH=`dir with xyz.so`
      3. CMD line -Djava.library.path=`dir with xyz.so`
      
  Next step is to compile your java code using javac
  javac MyApp.java
  javah -jni myApp // generates headers to be used in native proj
  
  



2. It is possible to call Java code from native (rarely used):

