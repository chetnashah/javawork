

## 

1. `requestPermission` - request permission from user
```kt
activity.requestPermissions(
    arrayOf(
        android.Manifest.permission.POST_NOTIFICATIONS,
    ),
    1
)
```
2. `onPermissionrequestresult` - callback for permission request result
```kt
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
     // ...
    }
```
3. check self permission
```kt
            val activity = context as MainActivity
            Log.d("Mainactivity"," permission = " +
                activity.checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS));
```
4. Should show permission rationale - initially false, true after single denial, and false after double denial
```kt
            val activity = context as MainActivity
            Log.d("Mainactivity"," shouldshow permission rationale = " +
                    activity.shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS));

```

## Example

```kt
package com.example.samplepermissions

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.samplepermissions.ui.theme.SamplePermissionsTheme
import java.util.Arrays

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SamplePermissionsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 1) {
            Toast.makeText(
                this,
                "on Permission result = "+Arrays.toString(grantResults),
                Toast.LENGTH_SHORT
            ).show()

            Log.d("MainActivity", "Permission are = "+ Arrays.toString(grantResults));
        }
    }
}



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column() {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
        Button(onClick = {
            Log.d("MainActivity", "Button clicked")
            val activity = context as MainActivity
            activity.requestPermissions(
                arrayOf(
                    android.Manifest.permission.POST_NOTIFICATIONS,
                ),
                1
            )
        }) {
            Text(text = "Request Permission")
        }
        Button(onClick = {
            val activity = context as MainActivity
            Log.d("Mainactivity"," permission = " +
                activity.checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS));
            Toast.makeText(
                activity, "check self permission = "+activity.checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS),Toast.LENGTH_SHORT
            ).show();

        }) {
            Text(text = "Check self permission")
        }
        Button(onClick = {
            val activity = context as MainActivity
            Log.d("Mainactivity"," shouldshow permission rationale = " +
                    activity.shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS));
            Toast.makeText(
                activity,
                "shouldShowRequestPermissionRationale = "+activity.shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS),
                Toast.LENGTH_SHORT
            ).show()

        }) {
            Text(text = "ShouldShow permission rationale?")
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SamplePermissionsTheme {
        Greeting("Android")
    }
}
```