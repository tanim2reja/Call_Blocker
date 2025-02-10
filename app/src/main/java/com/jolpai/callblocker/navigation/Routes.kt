package com.jolpai.callblocker.navigation

sealed class Routes(val route: String){
    object Home: Routes("home")
    object Profile: Routes("profile")
    object Search: Routes("search")
    object ContactList: Routes("contactList")
    object BlockedList: Routes("blockedList")


}
