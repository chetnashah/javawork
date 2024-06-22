
## cmdline-tools is the main package

It is the main package (cmdline-tools) that contains the `sdkmanager` tool, which is used to install other SDK packages (like platform tools, build tools, and the emulator).

Install the cmdline-tools package using the following steps:
1. Download command line tools via https://developer.android.com/studio#downloads

### Install the Android SDK command-line tools

The Android SDK command-line tools package is called `cmdline-tools`. It is the main package that contains the `sdkmanager` tool, which is used to install other SDK packages (like platform tools, build tools, and the emulator).

### Install platform-tools and build-tools using `sdkmanager`

```sh
sdkmanager "platform-tools" "platforms;android-33" "build-tools;33.0.0"
```

### INstall emulator via sdkmanager

The emulator is not included in the command-line tools package by default because the command-line tools package is meant to provide a minimal set of tools needed for managing the Android SDK and building Android applications. To use the Android emulator, you need to install additional packages. Here’s how you can install and manage the Android emulator using the command-line tools on a Mac:

### Steps to Install and Manage the Android Emulator (via sdkmanager)

1. **Open Terminal:**
   - Ensure your environment variables are set up correctly as mentioned in the previous steps.

2. **Install the Emulator Package:**
   - Use the `sdkmanager` to install the emulator package:
     ```sh
     sdkmanager "emulator"
     ```

3. **Install a System Image:**
   - You need a system image to run the emulator. For example, to install a system image for Android 13 (API level 33), you can run:
     ```sh
     sdkmanager "system-images;android-33;google_apis;x86_64"
     ```

4. **Create an AVD (Android Virtual Device):**
   - After installing the emulator and a system image, create an AVD using the `avdmanager`:
     ```sh
     avdmanager create avd -n my_avd -k "system-images;android-33;google_apis;x86_64" -d pixel
     ```
   - This command creates an AVD named `my_avd` using the specified system image and device definition (`pixel`).

5. **List Available AVDs:**
   - To see a list of available AVDs, you can use:
     ```sh
     avdmanager list avd
     ```

6. **Start the Emulator:**
   - To start the emulator with a specific AVD, use the `emulator` command:
     ```sh
     emulator -avd my_avd
     ```

### Example Commands

Here’s a summary of the commands you might use:

1. **Install the emulator:**
   ```sh
   sdkmanager "emulator"
   ```

2. **Install a system image:**
   ```sh
   sdkmanager "system-images;android-33;google_apis;x86_64"
   ```

3. **Create an AVD:**
   ```sh
   avdmanager create avd -n my_avd -k "system-images;android-33;google_apis;x86_64" -d pixel
   ```

4. **Start the emulator:**
   ```sh
   emulator -avd my_avd
   ```

### Managing the Emulator

- **List installed packages:**
  ```sh
  sdkmanager --list
  ```

- **Update installed packages:**
  ```sh
  sdkmanager --update
  ```

- **Delete an AVD:**
  ```sh
  avdmanager delete avd -n my_avd
  ```

By following these steps, you can install and manage the Android emulator using the command-line tools on your Mac.