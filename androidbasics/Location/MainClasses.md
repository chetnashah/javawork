
Package `com.google.android.gms.location`

## FusedLocationProviderClient
This is the central component of the location framework. Once created, you use it to request location updates and get the last known location.

## LocationRequest
This is a data object that contains quality-of-service parameters for requests (intervals for updates, priorities, and accuracy). This is passed to the FusedLocationProviderClient when you request location updates.

## LocationCallback
This is used for receiving notifications when the device location has changed or can no longer be determined. This is passed a LocationResult where you can get the Location to save in your database.
