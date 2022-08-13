


## Create a NetworkRequest object which specifies options

## Create a NetworkCallback object which will get callbacks from the OS

## Register NetworkRequest+NetworkCallback using ConnectivityManager API: `connManager.requestNetwork(networkRequest, networkCallback)`

Add following to xml: (normal permission)
```xml
<uses-permission android:name="android.permission.CHANGE_NETWORK_STATE" />
```

Allows applications to change network connectivity state.

## Security Exception: was not granted  either of these permissions: android.permission.CHANGE_NETWORK_STATE, android.permission.WRITE_SETTINGS


## Important methods on network callback are `onLost`, `onAvailable` and `onCapabilitiesChanged`

```kt
        val networkCallback = object : ConnectivityManager.NetworkCallback() {

            override fun onLosing(network: Network, maxMsToLive: Int) {
                super.onLosing(network, maxMsToLive)
                Log.d(TAG, "onLosing: " + network)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                Log.d(TAG, "onLost: " +network)
            }

            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                Log.d(TAG, "onAvailable: " + network)
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                super.onCapabilitiesChanged(network, networkCapabilities)
                Log.d(TAG, "onCapabilitiesChanged: " + networkCapabilities)
            }
        }

```