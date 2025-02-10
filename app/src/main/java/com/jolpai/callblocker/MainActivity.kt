package com.jolpai.callblocker

import android.app.role.RoleManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jolpai.callblocker.navigation.NavGraph
import com.jolpai.callblocker.navigation.NavigationItem
import com.jolpai.callblocker.ui.theme.CallBlockerTheme
import com.jolpai.callblocker.widget.BottomNavAppBar
import dagger.hilt.android.AndroidEntryPoint


//private lateinit var context: Context
private val DEFAULT_DIALER_REQUEST_ID: Int = 1
private val PHONE_SCREENING_REQUEST_ID: Int = 2

@AndroidEntryPoint
class MainActivity : ComponentActivity() {



    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestDefaultRole()
        requsetScreeningRole()
        setContent {
            CallBlockerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen("Call Blocker")
                    RequestPermissionsScreen()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun requsetScreeningRole() {
        val roleManager = getSystemService(ROLE_SERVICE) as RoleManager
        val intent = roleManager.createRequestRoleIntent(RoleManager.ROLE_CALL_SCREENING)
        startActivityForResult(intent, DEFAULT_DIALER_REQUEST_ID)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun requestDefaultRole() {
        val roleManager = getSystemService(ROLE_SERVICE) as RoleManager
        val intent = roleManager.createRequestRoleIntent(RoleManager.ROLE_DIALER )
        startActivityForResult(intent, DEFAULT_DIALER_REQUEST_ID)
        Log.e("TAG", "Checking role.")
    }


    open override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == DEFAULT_DIALER_REQUEST_ID) {
            if (resultCode == RESULT_OK) {
                // Your app is now the default dialer app
                Log.e("TAG", "Default dialer app")
            } else {
                // Your app is not the default dialer app
                Log.e("TAG", "Not default dialer app")
            }
        }
        if (requestCode == PHONE_SCREENING_REQUEST_ID) {
            if (resultCode == RESULT_OK) {
                // Your app is now the default dialer app
                Log.e("TAG", "Screening app ")
            } else {
                // Your app is not the default dialer app
                Log.e("TAG", "Not screening app")
            }
        }
    }
}



@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(appName: String, modifier: Modifier = Modifier) {
    val navController = rememberNavController() // Single NavController
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    Scaffold(
        bottomBar = {
            BottomNavAppBar(navController = navController)
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(text = { Text(text ="Add",color= MaterialTheme.colorScheme.onPrimary) },
                modifier = Modifier
                    .zIndex(1f),
                icon = { Icon(
                    Icons.Filled.AddCircle,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = "Home") },
                onClick = { /*TODO*/ },
                contentColor = MaterialTheme.colorScheme.onPrimary,
                backgroundColor = MaterialTheme.colorScheme.primary)
        }

    ){innerPadding ->

        NavGraph(innerPadding  = innerPadding,navController = navController)
        StatusBarColor()
    }
}

@Composable
fun StatusBarColor(){
    val statusBarColor = MaterialTheme.colorScheme.background//colorResource(id = R.color.amber_500)
    var appBarColor by remember { mutableStateOf(statusBarColor) }
    val disposedColor = MaterialTheme.colorScheme.background
    //val backgroundColor = MaterialTheme.colorScheme.background
    val navigationBarColor = MaterialTheme.colorScheme.background
    val isItDarkTheme = isSystemInDarkTheme()
    val systemUiController = rememberSystemUiController()

    DisposableEffect(appBarColor) {
        // Set the status bar color when the composable is first composed

        systemUiController.setStatusBarColor(statusBarColor,
            darkIcons = !isItDarkTheme)
        //systemUiController.setSystemBarsColor(appBarColor)
        systemUiController.setNavigationBarColor(navigationBarColor)

        // Cleanup when the composable is disposed
        onDispose {
            // Restore the default status bar color
            systemUiController.setSystemBarsColor(disposedColor)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RequestPermissionsScreen() {
    val context = LocalContext.current

    // Activity result launcher for permissions
    val permissionsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val phoneState = permissions[android.Manifest.permission.READ_PHONE_STATE] == true
        val callLog = permissions[android.Manifest.permission.READ_CALL_LOG] == true
        val contact = permissions[android.Manifest.permission.READ_CONTACTS] == true

        if (phoneState && callLog && contact) {
            Toast.makeText(context, "Permissions Granted", Toast.LENGTH_SHORT).show()
            Log.e("TAG", "All permissions granted")
        } else {
            Toast.makeText(context, "Permissions Denied", Toast.LENGTH_SHORT).show()
        }
    }

    // Check permissions and launch the request if needed
    LaunchedEffect(Unit) {
        val phoneStatePermission = ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_PHONE_STATE)
        val callLogPermission = ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_CALL_LOG)
        val contactPermission = ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_CONTACTS)
        val manageOwnCall = ContextCompat.checkSelfPermission(context, android.Manifest.permission.MANAGE_OWN_CALLS)
        val callPhone = ContextCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE)
        val answerPhoneCall = ContextCompat.checkSelfPermission(context, android.Manifest.permission.ANSWER_PHONE_CALLS)

        val allPermissionsGranted = phoneStatePermission == PackageManager.PERMISSION_GRANTED &&
                callLogPermission == PackageManager.PERMISSION_GRANTED &&
                contactPermission == PackageManager.PERMISSION_GRANTED &&
                manageOwnCall == PackageManager.PERMISSION_GRANTED &&
                callPhone == PackageManager.PERMISSION_GRANTED &&
                answerPhoneCall == PackageManager.PERMISSION_GRANTED

        if (!allPermissionsGranted) {
            // Launch the permissions request
            permissionsLauncher.launch(
                arrayOf(
                    android.Manifest.permission.READ_PHONE_STATE,
                    android.Manifest.permission.READ_CALL_LOG,
                    android.Manifest.permission.READ_CONTACTS,
                    android.Manifest.permission.MANAGE_OWN_CALLS,
                    android.Manifest.permission.CALL_PHONE,
                    android.Manifest.permission.ANSWER_PHONE_CALLS,

                )
            )
        } else {
            Toast.makeText(context, "Permissions Already Granted", Toast.LENGTH_SHORT).show()
            Log.e("TAG", "Permissions already granted")
        }
    }
}




@RequiresApi(Build.VERSION_CODES.Q)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CallBlockerTheme {
        MainScreen("Android")
    }
}