
## Cheatsheet

https://blog.kotzilla.io/koin-3-2-annotations-cheat-sheets/

## Usage

Add dependency
```groovy
dependencies {
    
    // Koin for Kotlin apps
    compile "io.insert-koin:koin-core:$koin_version"
}
```

## Declaring modules using `module` keyword

```
val appModule = module {
     single<UserRepository> { UserRepositoryImpl() }
     single { UserService(get()) }
}
```


## Types of injection : `single` and `factory`

`single` will resolve to a singleton.
`factory` will resolve to a new instance.

```kt
val appModule = module {
     single<UserRepository> { UserRepositoryImpl() }
     factory { MyPresenter(get()) }
}
```

Anndroid specific resolution:
1. `viewModel` for viewmodel scope.

```kt
val appModule = module {
     single<UserRepository> { UserRepositoryImpl() }
     viewModel { MyViewModel(get()) }
}
```

## Starting koin

**We need to start koin from main entry point.**
call the `startKoin()` function in the application's main entry point.

Use `modules(moduleName)` to bootstrap given list of modules

```kt
fun main() {
    startKoin {
        modules(appModule)
    }
}
```


## `by inject()`

The `by inject()` function allows us to retrieve Koin instances, in any class that extends `KoinComponent`.

```kt
class UserApplication : KoinComponent {

    private val userService : UserService by inject()

    // display our data
    fun sayHello(){
        val user = userService.getDefaultUser()
        val message = "Hello '$user'!"
        println(message)
    }
}
```

