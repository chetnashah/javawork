
https://netty.io/wiki/user-guide-for-4.x.html

## event-driven, non-blocking and asynchronous frameworks designed for high-load I/O

vert.x is based on netty 

Netty is an NIO client server framework that enables quick and easy development of network applications such as protocol servers and clients. It greatly simplifies and streamlines network programming such as TCP and UDP socket server development.


## Channel

Abstraction on top of socket.

ChannelPipeline is doubly linked list of Channel Handlers. 
**The core idea is interceptor pattern**.


### Channel Handlers

Channel handlers can process inbound and outbound events.

### Inbound Events

e.g.
1. some data appeared in our n/w buffer
2. we just accepted some connection

Write `ChannelInBoundHandler` for InBound events.

### Outbound Events

Write `ChannelOutboundHandler` for processing outbound events.
