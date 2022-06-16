

## object based lazy singleton

```kt
class Singleton private constructor() {
    companion object {
        val instance: Singleton by lazy { Singleton() }
    }
}
```

More on lazy pattern: https://medium.com/til-kotlin/how-kotlins-delegated-properties-and-lazy-initialization-work-552cbad8be60

## Double checked locking

```kt
class SingletonObj {
    private constructor(context: Context)

    companion object {
        @Volatile private var mInstance: SingletonObj? = null

        public  fun get(context: Context): SingletonObj =
            mInstance ?: synchronized(this) {
                val newInstance = mInstance ?: SingletonObj(context).also { mInstance = it }
                newInstance
            }
    }
}
```