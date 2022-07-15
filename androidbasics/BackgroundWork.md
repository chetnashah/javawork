

## Definition

Code that executes when :
1. No activities shown
2. No foreground service running

## Types of background work

Background work falls into one of three primary categories:

* **Immediate**: Needs to execute right away and complete soon. You should use Kotlin coroutines (or java thread executors) for immediate impersistent work.
* **Long Running**: Work is long running if it is **likely to take more than 10 minutes to complete**
* **Deferrable**: Does not need to run right away.

Likewise, background work in each of these three categories can be either persistent or impersistent:

* **Persistent work**: Remains scheduled through app restarts and device reboots.
* **Impersistent work**: No longer scheduled after the process ends. 

## Approaches

![Background work approaches](images/backgroundworkapproaches.png)
