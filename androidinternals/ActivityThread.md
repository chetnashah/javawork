## Represents the main thread

## Important messages are received on the handler

1. bindApplication
2. "Transactions" via `EXECUTE_TRANSACTION` message of handler i.e. Activity lifecycle events like `resume`, `pause`, `stop`, `start`, `destroy` are received as "ClientTransactionItems", each of which is implemented as ResumeActivityItem, PauseActivityItem, StopActivityItem, DestroyActivityItem, etc.
3. "ClientTransactionItem" is base class and useful communication link between AMS and activity thread.
4. Excution class is TransactionExecutor, which is a handler that processes the transactions.
5. ClientTransactionHandler is an abstract class holding methods like "handleLaunchActivity", "handleResumeActivity", "handlePauseActivity", "handleStopActivity", "handleDestroyActivity", etc. (https://cs.android.com/android/platform/superproject/main/+/main:frameworks/base/core/java/android/app/ClientTransactionHandler.java;l=47;drc=1b1eb1b13c701c39b380dcb8dc3205f6f99410a5;bpv=1;bpt=1)
6. ActivityThread is an instance implementation of abstract class "ClientTransactionHandler", which means it can handle transactions like "handlelaunchActivity", "handleresumeActivity", "handlepauseActivity", "handlestopActivity", "handledestroyActivity", etc. (https://cs.android.com/android/platform/superproject/main/+/main:frameworks/base/core/java/android/app/ActivityThread.java;drc=1b1eb1b13c701c39b380dcb8dc3205f6f99410a5;l=294)

