
## TransactionTooLargeException

During a remote procedure call (RPC), the arguments and the return value of the call are transferred as Parcel objects stored in the Binder transaction buffer. If the arguments or the return value are too large to fit in the transaction buffer, then the call will fail and `TransactionTooLargeException` will be thrown. The Binder transaction buffer has a limited fixed size, currently 1Mb, which is shared by all transactions in progress for the process. Consequently this exception can be thrown when there are many transactions in progress even when most of the individual transactions are of moderate size.

## From linux kernel mailing list

Hi, sorry I have been slow to respond.  I can give a summary of how
binder is used in the Android platform and the associated feature set.
 I won't try to address other options, especially D-Bus, because
honestly I haven't been following it for the last 3 or so years so
don't really know its current state of art.

In the Android platform, the binder is used for nearly everything that
happens across processes in the core platform.  Some examples of this,
illustrating key features are:

- The window manager and clients talk with each other through Binder.
When a client starts up, it does a binder IPC into the window manager
to create a new binder connection dedicated to that client.  This is a
common use of the capability model of the binder, where secure
connections are given to clients which they can use for communication
with the system.

- The window manager and lower-level surface compositor talk with each
other through Binder.  There is as simple binder-based API that is
used to allocate a surface for a window.  This takes advantage of the
Binder's fd passing and object identity facilities to allow the
surface compositor to allocate area in a shared heap it manages: the
window manager makes this request on behalf of a client application,
and then passes that binder object over to the client process (it will
retrieve the associated fd and map it for each unique heap it
receives) for it to draw directly into the associated surface memory.
The binder's object identity rules (an object has a single identity as
it travels across processes, no matter how many times it does so or
where it goes) are very convenient for managing this.

- Separate components, like the window manager or surface flinger, may
be switched between running in the same process or different processes
with no change to their code.  For example, in the current android
platform these two components run in the same process, but we also
have had run them in other processes and would like to do so on
higher-end systems where there is more memory.  This is not strictly a
feature of the kernel part of the binder, but the IPC semantics it
provides greatly ease its implementation: dispatching transactions to
thread pools, synchronous calls with recursion across processes, etc.

- The activity (or really application/process) manager also uses the
binder for launching and managing components in a process.  For
applications, it creates a simple binder object for use as a "token"
for the application.  It gives this token to both the application and
the window manager, and the application gives its token to the window
manager when it adds windows.  Because the binder maintains object
identity, this model is used extensively in the system for security:
you can hand someone a token, and then can hand that token to others,
and you can always check whenever you get a token exactly who it was
originally given to without any way for clients to spoof it.  So the
activity manager can say to the window manager, "all of this token's
windows should be hidden," and the window manager can absolutely
identify which windows came from that application through the token
the app supplied with them.

- The fundamentals of Android's security are a combination of
uid-based permissions and binder capabilities.  Some capabilities are
direct (I give you access to my interface that you can call on), some
are indirect (I give you a binder object as a token that you can
compare against other tokens you receive to validate who it is).  For
permissions, every incoming binder transaction has associated with it
the uid of the initator, which is used in numerous places where we
want to only allow specific uids to access specific features.  For
example, there are APIs on the window manager to inject high-level
input events into the system, and the implementation of those methods
checks the calling uid to see if it is an application that has been
granted the permission to do this.

- The binder natively supports one-way and two-way calls.  Its two-way
calls are used extensively by all of the system services for incoming
IPCs for better multi-threading: they are dispatched directly from a
thread pool and the services acquire specific locks as needed to
protect their state (rather than serializing all calls through one
thread).  More traditional one-way/async calls are used for
communicating back with applications (or really for any service to
send commands to a higher-level part of the system).

- Many of the system services of course want to clean up state they
have associated with a client process.  For example, if an application
process goes away, all of its windows should be removed.  This is made
easy by the binder's "link to death" facility, which allows a process
to get a callback when another process hosting a binder object goes
away.  For example, the window manager links to the death of a
window's callback interface, and other services have clients send a
binder object token just to be able to find out when its process dies.
 The driver provides this facility by telling a process about the
death of any objects it is watching.

- The Input Method Manager is probably one of the better
representative examples of how the binder facilities are used in the
system: it is a relatively small component, but makes extensive use of
binder object identities, capabilities, death links, and other
features to arbitrate between N applications and M IMEs securely
interacting with each other in a controlled way.  A taste of this can
be seen in the "Security" section of
http://developer.android.com/reference/android/view/inputmethod/InputMethodManager.html
.  One particular feature it relies on is allowing an application to
hand it a binder object for an interface (here an InputConnection),
which it can then send to an IME running in another process.  That IME
can now make direct calls on the InputConnection for just that
application (it has been granted that capability) without having to go
through the Input Method Manager intermediary process.

One part of the binder protocol that is really nice but doesn't yet
have a user space implementation is weak references.  This allows a
process to maintain knowledge of a remote object, without forcing it
to stay around.  At any point it can try to promote that to a strong
reference (to actively call on the object), which will either succeed
or fail based on whether the original object is still around or is not
around because all of the strong references (either in-proc or remote)
are gone.  We never re-implemented the user space code for this
because we didn't do weak references in the Java layer, but for native
C and C++ code it is a very nice facility for managing object
lifetimes.

For a rough idea of the scope of the binder's use in Android, here is
a list of the basic system services that are implemented on top of it:
package manager, telephony manager, app widgets, audio services,
search manager, location manager, notification manager, accessibility
manager, connectivity manager, wifi manager, input method manager,
clipboard, status bar, window manager, sensor service, alarm manager,
content service, activity manager, power manager, surface compositor.

```
> If for instance the main reason for Google using this interface is cause
> a large number of android people once worked at Palm or BeOS, that's not
> reason enough for it to go into the kernel. Or if this binder interface
> really fits well with Java or C++ people and they just love it, that's
> not really acceptable either..
```
It is true that a lot of the ideas of the binder came from previous
work on BeOS and Palm's Cobalt.  However, that is mostly inspiration:
we started with the Open Binder code for very intial bringup, but
entirely rewrote both the user space and driver code to address our
needs for Android and to better fit with the Linux-centric design of
the platform.

I'm not sure what the relevance is of Java or C++ people liking it.
Does this mean that the important thing is that C people love it and
other languages don't matter? :)  Anyway whether or not you "love" it
I don't think is a matter of programming language but just design
style, personal preference, and who knows what else.  It has been
extremely useful in our implementation of Android, as can be seen in
just how much of the system sits on top of it, but that's all.

Finally as far as someone else's comment of Open Binder being dead --
well it's an interesting situation.  That particular code is no longer
being developed, but basically the active development switched over to
the fork/rewrite of it we have now in Android.  You could maybe say
that Open Binder was a research project, and Android is the shipping
implementation.  Though really, the main difference between them is
that Android has a much simpler user-space implementation (because we
didn't need the full features of Open Binder); there isn't any reason
the full Open Binder environment couldn't be put back on top of the
current binder.  The binder shell is certainly a fun toy. :)  See
http://www.open-binder.org/docs/html/BinderShellTutorial.html for
example.  But a lot of the stuff there is just not hugely interesting
for Linux/Android.

## Death receipients

https://www.androiddesignpatterns.com/2013/08/binders-death-recipients.html

When an application dies, its state will be spread over dozens of system services (the Activity Manager, Window Manager, Power Manager, etc.) and several different processes. These system services need to be notified immediately when an application dies so that they can clean up its state and maintain an accurate snapshot of the system. Enter death recipients.

