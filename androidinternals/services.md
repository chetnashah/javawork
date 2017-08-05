
* Use IntentService for worker-queue-processor pattern, only one intent is processed at a time via onHandleIntent.

* Any given Service will have precisely 0 or 1 instances
at any given moment. Calling startService on the same service multiple times
will just result in onStartCommand of that service called multiple times.

* One interacts with a bound service via IBinder, one starts a bound service
using bindService, but it does not return IBinder immedeately but returns
it via onServiceConnected callback which we pass in bindService call.

* All framework services are bound services (AIDL based). They handle multiple simultaneous calls in parellel and handle synchronization/multi threading etc.

* User space bound services should use Messenger if no need of multi threaded simultaneous calls

When you need to perform IPC, using a Messenger for your interface i
s simpler than using AIDL, because Messenger queues all calls to the service. A pure AIDL interface sends simultaneous requests to the service, which must then handle multi-threading.

For most applications, the service doesn't need to perform multi-threading, so using a Messenger allows the service to handle one call at a time. If it's important that your service be multi-threaded, use AIDL to define your interface.

* Note: bindService canot be called from BroadcastReceiver

