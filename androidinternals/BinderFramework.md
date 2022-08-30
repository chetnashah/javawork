
## TransactionTooLargeException

During a remote procedure call (RPC), the arguments and the return value of the call are transferred as Parcel objects stored in the Binder transaction buffer. If the arguments or the return value are too large to fit in the transaction buffer, then the call will fail and `TransactionTooLargeException` will be thrown. The Binder transaction buffer has a limited fixed size, currently 1Mb, which is shared by all transactions in progress for the process. Consequently this exception can be thrown when there are many transactions in progress even when most of the individual transactions are of moderate size.

