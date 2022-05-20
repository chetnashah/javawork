Built on standard `javax.inject` annotations,
each class is easy to test.

It is useful for modular code, it is easy
to create reusable interchangable modules.

Simple, traceable and performant.

`@Provides`: put on methods that provide the instances

`@Module`: put on classes that have methods with `@Provides` annotation.

`@Inject`: Use to get instance values supplied by the injector framework

`@Component`: Combines everything together. You need to specify a list of `@Module`s that you want to inject, a bridge between `@Inject` and `@Module`.

Annotates an `interface` or `abstract class` for which a fully-formed, dependency-injected implementation is to be generated from a set of `modules()`. The generated class will have the name of the type annotated with `@Component` prepended with Dagger. For example, `@Component interface MyComponent {...}` will produce an implementation named `DaggerMyComponent`.

**Note**: Usuallly the entity that you put `@Component` will be an interface.
Every type annotated with `@Component` must contain at least one abstract component method. Component methods may have any name, but must have signatures that conform to either provision or members-injection contracts.

The method named inside `@Component` interface will usually be something like:
```java
@Component(modules = {AppModule.class, UtilsModule.class, ReceiversModule.class})
@Singleton
public interface AppComponent {
    void inject(MainActivity mainActivity);// MainActivity is a consumer of this component
    void inject(SecondActivity secondActivity);// SecondActivity is a consumer of this component.
}
```

A good place to do `AppComponent` initialization is int the android `Application` class.

### injecting in fragments

The correct place to do injection in fragments is `onAttach`

```java
@Override
public void onAttach(Context context) {
    ((AppController) context.getApplicationContext()).getNetComponent().inject(this);
    super.onAttach(context);
}
```