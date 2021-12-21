By default kernel will never allow one process to reach out directly into address space of other process.
As a result one has to go through OS given's mecahnisms (ipc/binder/aidl) framework for 
inter-process communication (communication between two processes).

IPC in android does marshalling and unmarshilling of byte streams across processes.

AIDL proxy and stubs are generated for marshalling demarshalling, via `Parcel`

Classes implementing `Parcelable` can be marshalled.
https://www.youtube.com/watch?v=sttlq7i0EhI
##  android binder framework

apps for inter process comm prefer aidl, where as intra process (i.e within a single process), binder mechanism can be opted.

Binder framework is internally implemented using shared memory for efficiency purposes.
Binder runs client requests in a pool of threads concurrently, so binder serving side in an interprocess setup should handle thread safety

By default caller blocks synchronously on the AIDL call.

`oneway` keyword : caller does not block waiting for the results, callee executes method asynchronously, oneway means nonblocking

`in` -> direction of data copy from caller to the callee's address space.
`out` -> data copied from callee's address space to caller's address space.

implementer of interface extends stub which extend Binder

Proxy is used by clients (interface type) to act as caller