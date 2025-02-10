package com.jolpai.callblocker.screen

import android.content.Context
import android.content.pm.PackageManager
import android.provider.ContactsContract
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.jolpai.callblocker.navigation.Routes
import com.jolpai.callblocker.viewModel.CallBlockerViewModel

@Composable
fun ContactList(onNavigate: (String) -> Unit){


    val context = LocalContext.current
    val contacts = remember { mutableStateListOf<String>() }
    RequestContactsPermission {
        val contactList = fetchContacts(context)
        contacts.addAll(contactList)
    }
    DisplayContactsList(contacts)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DisplayContactsList(contacts: SnapshotStateList<String>,
                        viewModel: CallBlockerViewModel = hiltViewModel()) {
    val colors = listOf(Color.Red, Color.Magenta, Color.Gray,Color.Cyan)
    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }
    val selectedContact = remember { mutableStateOf("") }

    val blockedNumbers by viewModel.blockedNumbers.collectAsState()
    LazyColumn {
        items(contacts) { contact ->
            val randomColor = colors.random()
            // Swipe-to-Block Gesture Modifier
            val swipeableState = rememberSwipeableState(0)
            val anchors = mapOf(0f to 0, 300f to 1) // Anchors for swipe states
            val isBlocked = blockedNumbers.any { it.number == contact }
            Row(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .swipeable(
                    state = swipeableState,
                    anchors = anchors,
                    thresholds = { _, _ -> FractionalThreshold(0.3f) },
                    orientation = Orientation.Horizontal
                )
                .clickable {
                    selectedContact.value = contact
                    showDialog.value = true
                }
                .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp),

                horizontalArrangement = Arrangement.Start){

                Box(modifier = Modifier
                    .size(44.dp)
                    .background(
                        color = randomColor.copy(alpha = .5f),
                        shape = CircleShape
                    ),
                    contentAlignment = Alignment.Center
                ){
                    Text(text = contact.first().uppercase(),
                        color = MaterialTheme.colorScheme.onSecondary)
                }

                Text(text = contact, modifier = Modifier.padding(8.dp))
            }
            // Trigger dialog if swipe state is complete
            LaunchedEffect(swipeableState.currentValue) {
                if (swipeableState.currentValue == 1) {
                    selectedContact.value = contact
                    showDialog.value = true
                }
            }

        }

    }


    // Dialog for confirming block action
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Block Contact") },
            text = { Text("Do you want to block ${selectedContact.value}?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.blockNumber(selectedContact.value)
                    showDialog.value = false
                    Toast.makeText(context, "${selectedContact.value} blocked", Toast.LENGTH_SHORT).show()
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog.value = false }) {
                    Text("No")
                }
            }
        )
    }
}


fun fetchContacts(context: Context): List<String>{
    val contactList = mutableListOf<String>()
    val contentResolver = context.contentResolver
    val cursor = contentResolver.query(
        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        arrayOf(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        ),
        null, null, null
    )

    cursor?.use {
        while (it.moveToNext()){
            val name = it.getString(
                it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            )
            val number = it.getString(
                it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)
            )
            contactList.add("$name: $number")
        }
    }

    return contactList
}

@Composable
fun RequestContactsPermission(onPermissionGranted: () -> Unit) {
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            Toast.makeText(context, "Contacts Permission Granted", Toast.LENGTH_SHORT).show()
            onPermissionGranted()
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            permissionLauncher.launch(android.Manifest.permission.READ_CONTACTS)
        } else {
            onPermissionGranted()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview(){
    val contacts : SnapshotStateList<String> = mutableStateListOf("John Doe:\n1234567890", "Jane Smith: \n9876543210")

    DisplayContactsList(contacts = contacts)
}
