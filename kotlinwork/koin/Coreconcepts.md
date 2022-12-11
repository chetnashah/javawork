
## Application DSL

**A KoinApplication instance is a Koin container instance configuration. This will let your configure logging, properties loading and modules.**

To build a new KoinApplication, use the following functions:

* `koinApplication { }` - create a KoinApplication container configuration
* `startKoin { }` - create a KoinApplication container configuration and register it in the GlobalContext to allow the use of GlobalContext API


### startKoin

It need a list of Koin modules to run. 
Modules are loaded and definitions are ready to be resolved by the Koin container.

* When we start Koin, we create a KoinApplication instance that represents the Koin container configuration instance. 
* Once launched, it will produce a Koin instance resulting of your modules and options. 
* This Koin instance is then hold by the GlobalContext, to be used by any KoinComponent class.

## Module are used to declare resolution definitions

**A Koin module is a "space" to gather Koin resolution definitions. It's declared with the module function.**

```
val myModule = module {
    // Your definitions ...
}
```

A module is a logical space to help you organize your definitions, and can depend on definitions from other module. 

Definitions are lazy, and then are resolved only when a a component is requesting it.
```kt
// ComponentB <- ComponentA
class ComponentA()
class ComponentB(val componentA : ComponentA)

val moduleA = module {
    // Singleton ComponentA
    single { ComponentA() }
}

val moduleB = module {
    // Singleton ComponentB with linked instance ComponentA
    single { ComponentB(get()) }
}
```



