
## Where are the system images stored?

macOS and Linux - 
`$ANDROID_SDK_HOME/system-images/android-apiLevel/variant/arch/` or `macOS and Linux - ~/Library/Android/sdk/system-images/android-apiLevel/variant/arch/` because `ANDROID_SDK_HOME` is usually `$HOME/Library/Android/sdk` on macOS.

e.g. `/Users/cshah/Library/Android/sdk/system-images/android-33/google_apis/arm64-v8a`

## AVD Data dir

The AVD data directory, also called the content directory, is specific to a single AVD instance and contains all modifiable data for the AVD.

The default location is the following, where name is the AVD name:

macOS and Linux - `~/.android/avd/name.avd/`



## List all emulators

```
emulator -list-avds
```

## Start an emulator

```
emulator -avd Nexus_5X_API_23
```

## Emulator disk images

The emulator needs several key image files to run appropriately.
Their exact location depends on whether you're using the emulator from the Android SDK, or not (more details below).

The minimal required image files are the following:

1. kernel-qemu      the emulator-specific Linux kernel image
2. ramdisk.img      the ramdisk image used to boot the system
3. system.img       the *initial* system image
4. vendor.img       the *initial* vendor image
5. userdata.img     the *initial* data partition image

It will also use the following writable image files:

1. userdata-qemu.img  the persistent data partition image
2. system-qemu.img    an *optional* persistent system image
3. vendor-qemu.img    an *optional* persistent vendor image
4. cache.img          an *optional* cache partition image
5. sdcard.img         an *optional* SD Card partition image
6. snapshots.img      an *optional* state snapshots image

### Where are these img files locateD?

They are located under `~/.android/avd/avd_name.avd` directory.

```
cshah@192 Pixel_6_API_33.avd % ls
AVD.conf                encryptionkey.img.qcow2 tmpAdbCmds
cache.img               hardware-qemu.ini       userdata-qemu.img
cache.img.qcow2         initrd                  userdata-qemu.img.qcow2
config.ini              modem_simulator         userdata.img
emu-launch-params.txt   multiinstance.lock      version_num.cache
emulator-user.ini       read-snapshot.txt
encryptionkey.img       sdcard.img

cshah@192 Pixel_6_API_33.avd % pwd
/Users/cshah/.android/avd/Pixel_6_API_33.avd

```

## Emulator creation and deletion

### Creation

```
avdmanager create avd -n test_emu -k "system-images;android-25;google_apis;x86"
```

### Deletion

```
avdmanager delete avd -n test_emu
```
```