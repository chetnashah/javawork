
See overview at Jenkov vertx.
Vertx is composed of verticles,
think of each verticle as an android looper, like a
message driven thread(with message queue)

There is an Event bus that moves messages around.
Verticles are event driven and do not run unless they receive a 
message.

Messages can be POJOs, strings, CSV, JSON, binary data or 
whatever else you need.

Addresss: verticles can send and listen to addresses.
An address is like a named channel.

When a message is sent to given address, all verticles
that listen on that address receive the message.

You make your verticle type by extending AbstractVerticle
and implementing start and stop methods


