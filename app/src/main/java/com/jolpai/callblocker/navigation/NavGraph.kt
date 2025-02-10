package com.jolpai.callblocker.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jolpai.callblocker.screen.BlockedList
import com.jolpai.callblocker.screen.ContactList
import com.jolpai.callblocker.screen.Home

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun NavGraph(navController: NavHostController, innerPadding: PaddingValues) {

    NavHost(
        navController = navController,
        startDestination = Routes.Home.route,
    ){

            composable(Routes.Home.route){
                //Home(navController::navigate)
                Home(onNavigate = { route -> navController.navigate(route) })
            }

            composable(Routes.ContactList.route){
                ContactList(onNavigate = {route -> navController.navigate(route)})
            }

            composable(Routes.BlockedList.route){
                BlockedList(onNavigate = {route -> navController.navigate(route)})
            }

    }

}