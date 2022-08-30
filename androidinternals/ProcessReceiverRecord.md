* The state info of all broadcast receivers in the process.

## Imp members 

```java
    final ProcessRecord mApp;
    private final ActivityManagerService mService;

    /**
     * mReceivers currently running in the app.
     */
    private final ArraySet<BroadcastRecord> mCurReceivers = new ArraySet<BroadcastRecord>();

    /**
     * All IIntentReceivers that are registered from this process.
     */
    private final ArraySet<ReceiverList> mReceivers = new ArraySet<>();

```