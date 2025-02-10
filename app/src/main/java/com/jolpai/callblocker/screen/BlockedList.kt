package com.jolpai.callblocker.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun BlockedList(onNavigate: (String) -> Unit){

    val context = LocalContext.current
    val contacts = remember { mutableStateListOf<String>() }
    RequestContactsPermission {
        val contactList = fetchContacts(context)
        contacts.addAll(contactList)
    }
    DisplayContactsList(contacts)

}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview(){
    val contacts : SnapshotStateList<String> = mutableStateListOf("John Doe:\n1234567890", "Jane Smith: \n9876543210")

    DisplayContactsList(contacts = contacts)
}