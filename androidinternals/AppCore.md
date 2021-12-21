

ActivityThread extends `ClientTransactionHandler`
```java
/**
 * Base interface for individual requests from server to client.
 * Each of them can be prepared before scheduling and, eventually, executed.
 * @hide
 */
public interface BaseClientRequest extends ObjectPoolItem {

    /**
     * Prepare the client request before scheduling.
     * An example of this might be informing about pending updates for some values.
     *
     * @param client Target client handler.
     * @param token  Target activity token.
     */
    default void preExecute(ClientTransactionHandler client, IBinder token) {
    }

    /**
     * Execute the request.
     * @param client Target client handler.
     * @param token Target activity token.
     * @param pendingActions Container that may have data pending to be used.
     */
    void execute(ClientTransactionHandler client, IBinder token,
            PendingTransactionActions pendingActions);

    /**
     * Perform all actions that need to happen after execution, e.g. report the result to server.
     * @param client Target client handler.
     * @param token Target activity token.
     * @param pendingActions Container that may have data pending to be used.
     */
    default void postExecute(ClientTransactionHandler client, IBinder token,
            PendingTransactionActions pendingActions) {
    }
}

/*
 * A callback message to a client that can be scheduled and executed.
 * Examples of these might be activity configuration change, multi-window mode change, activity
 * result delivery etc.
 */
public abstract class ClientTransactionItem implements BaseClientRequest, Parcelable {

    /** Get the state that must follow this callback. */
    @LifecycleState
    public int getPostExecutionState() {
        return UNDEFINED;
    }


    // Parcelable

    @Override
    public int describeContents() {
        return 0;
    }
}

```

### Clienttransaction

 * A container that holds a sequence of messages, which may be sent to a client.
 * This includes a list of callbacks and a final lifecycle state.
