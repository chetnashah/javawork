import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 *
 * good intro on https://www.youtube.com/watch?v=8RGhT-YySDY
 */
public class MyApp {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector();
        Greeter greeter = injector.getInstance(Greeter.class);

        greeter.sayHello();
        greeter.sayHelloWithShower();
        greeter.sayHelloWithWriter();
    }
}

class Greeter {
    @Inject
    Displayer mDisplayer;// field injection
    Writer mWriter; // setter injection
    Shower mShower;// Constructor injection most preferred
    // only annotate a single constructor with inject
    @Inject Greeter(Shower shower) {
        mShower = shower;
    }


    @Inject void setWriter(Writer writer) {
        mWriter = writer;
    }

    void sayHello()
    {
        mDisplayer.display("hello world! from displayer");
    }

    void sayHelloWithShower() {
        mShower.show("Hi from show");
    }

    void sayHelloWithWriter() {
        mWriter.write("Hi from writer");
    }
}

class Displayer {
    void display(String s){
        System.out.println(s);
    }
}

class Shower {
    void show(String s) {
        System.out.println(s);
    }
}

class Writer {
    void write(String s) {
        System.out.println(s);
    }
}
