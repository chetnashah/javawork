## Important commands

List emulators:
```
emulator -list-avds
```

Run a specific emulator
```
emulator @Pixel_3a_API_31_arm64-v8a
```

Capture network data for emulator
```
emulator @Nexus_5X_API_23 -tcpdump /path/dumpfile.cap
```

## How adb works

https://developer.android.com/studio/command-line/adb

http://adbcommand.com/adbshell


## Running as root on emulator

https://stackoverflow.com/questions/43923996/adb-root-is-not-working-on-emulator-cannot-run-as-root-in-production-builds

root is not allowed in production builds, use an userdebug build instead.

emulator provided by Google. They are all user binary, not engineer binary. You can check it yourself by using "adb shell" command. If command line starts with "#", it's engineer build. If it starts with "$", it's user build.

**Pick an emulator system image that is NOT labelled "Google Play".**
official source - https://developer.android.com/studio/run/managing-avds#system-image


### adb daemon (adbd)

Runs on phone-device, as a background process

### Adb client

runs on laptop usually

### Adb server

runs on local laptop as a background process - serves as a bridge between adb client and adb daemon on device.

uses port 5037 on local laptop.

You can verify via : `netstat -aln | grep '5037'`

* Scans for physical devices and emulators


Note that each emulator uses a pair of sequential ports — an even-numbered port for console connections and an odd-numbered port for adb connections. For example:

Emulator 1, console: 5554
Emulator 1, adb: 5555
Emulator 2, console: 5556
Emulator 2, adb: 5557

## ddmlib and adb

ddmlib library is another frontend for the ADB server

https://malinskiy.medium.com/adam-a-second-birth-to-androids-ddmlib-c90fdde4c39d


## dadb

https://github.com/mobile-dev-inc/dadb

## Networking

Each instance of the emulator runs behind a virtual router/firewall service that **isolates it from your development machine network interfaces** and settings and from the internet.


**An emulated device can't see your development machine or other emulator instances on the network.** Instead, it sees only that it is connected through Ethernet to a router/firewall.

The virtual router for each instance manages the 10.0.2/24 network address space.

|:---:|:---:|
| 10.0.2.1	 | Router/gateway address |
| 10.0.2.2	| Special alias to your host loopback interface (i.e., 127.0.0.1 on your development machine) |
| 10.0.2.3	| First DNS server |
| 10.0.2.4 / 10.0.2.5 / 10.0.2.6	Optional second, third and fourth DNS server (if any) |
| 10.0.2.15	| The emulated device network/ethernet interface |
| 127.0.0.1	| The emulated device loopback interface |

## Redir (hostport -> guest port forwarding)

```
add <protocol>:<host-port>:<guest-port>
```

For example, the following command sets up a redirection that handles all incoming TCP connections to your host (development) machine on `127.0.0.1:5000` and will pass them through to the emulated system on `10.0.2.15:6000`:

```
redir add tcp:5000:6000
```


## adb forward and reverse port forwarding

You can use the forward command to set up arbitrary port forwarding, which forwards requests on a specific host port to a different port on a device. The following example sets up forwarding of host port 6100 to device port 7100
```
adb forward tcp:6100 tcp:7100
```
Like that, you can easily reverse the port using reverse command
```
adb reverse tcp:3000 tcp:3000
```
So the above example, When your device is trying to access local port 3000, that request will be routed to your laptop’s port 3000. Additionally, you can replace the ports with anything. If you want you could use
```
adb reverse tcp:80 tcp:3000
```
To redirect your phone’s port 80 to your computer’s port 3000


## Telnet with adb

```
telnet localhost 5554

Android Console: type 'auth <auth_token>' to authenticate
Android Console: you can find your <auth_token> in
'/Users/jayshah/.emulator_console_auth_token'

auth <authtoken>
```

After auth with auth-token,
Now you can use following commands:
```
    help|h|?
    help-verbose
    ping
    automation
    event
    geo
    gsm
    cdma
    crash
    crash-on-exit
    kill
    restart
    network
    grpc
    power
    quit|exit
    redir
    sms
    avd
    qemu
    sensor
    physics
    finger
    debug
    rotate
    screenrecord
    fold
    unfold
    posture
    multidisplay
    icebox
    nodraw
    resize-display
```
