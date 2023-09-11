
## Definition

```kt
public interface LifeCycleOwner {
    LifeCycle getLifeCycle();
}
```

## Lifecycleowner Implemented by Fragment, Component/AppCompatActivity/SupportActivity

ViewController/Activity/Fragment objects are drivers of lifecycle and hence considered lifecycle owners.

## ProcessLifecycleOwner

* Composite lifecycle of all activities
* Stays alive for the whole process
