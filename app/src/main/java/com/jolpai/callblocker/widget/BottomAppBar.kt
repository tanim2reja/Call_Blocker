package com.jolpai.callblocker.widget

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.jolpai.callblocker.navigation.NavigationItem
import com.jolpai.callblocker.navigation.Routes

@Composable
fun BottomNavAppBar(navController: NavController) {
    val selectedItem  = rememberSaveable { mutableStateOf(Routes.Home.route) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomItemList = getNavigationItems()
    BottomAppBar (modifier = Modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colorScheme.background,

        ){
        bottomItemList.forEach{ navItem ->
            val isSelected = currentRoute == navItem.route
            val iconColor = if (isSelected) MaterialTheme.colorScheme.inversePrimary else Color.Gray

            BottomNavigationItem(
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        navController.navigate(navItem.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.label,
                        tint = iconColor,
                        modifier = Modifier.padding(4.dp)
                    )

                },
                label = {
                    Text(
                        text = navItem.label,
                        fontWeight = if(isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = iconColor
                    )
                },
                modifier = Modifier
                    //.clip(shape = RoundedCornerShape(24.dp))
                    //.size(50.dp)
                    .padding(start = 12.dp, top = 6.dp, bottom = 6.dp, end = 12.dp),
                alwaysShowLabel = true)

        }
    }
}

fun getNavigationItems(): List<NavigationItem> {
    return listOf(

        NavigationItem(
            label = "Contact",
            icon = Icons.Filled.List,
            route = Routes.ContactList.route
        ),
        NavigationItem(
            label = "Home",
            icon = Icons.Filled.Home,
            route = Routes.Home.route
        ),
        NavigationItem(
            label = "Blocked",
            icon = Icons.Filled.Clear,
            route = Routes.BlockedList.route
        )
    )
}

@Composable
fun CustomBottomNavBar(navController: NavController) {
    Box(modifier = Modifier) {
        Column(modifier = Modifier,
            verticalArrangement = Arrangement.Bottom) {
            BottomNavigationBar(navController  = navController)
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val currentRoute = rememberSaveable { mutableStateOf(Routes.Home.route) }

    Box(
        Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        // Navigation Items Row
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .align(Alignment.Center),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.clickable {
                currentRoute.value = Routes.ContactList.route
                navController.navigate(Routes.ContactList.route){
                    launchSingleTop = true
                }
                                                 },
                horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Filled.List,
                    contentDescription = "Contact",
                    tint = if (currentRoute.value == Routes.ContactList.route)
                        MaterialTheme.colorScheme.primary
                    else
                        Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
                Text(text = "Contact", fontSize = 12.sp, color = Color.Gray)
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Home",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(24.dp)
                )
                Text(text = "Block", fontSize = 12.sp, color = Color.Gray)
            }
            //Spacer(modifier = Modifier.width(80.dp)) // Space for Floating FAB

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "Block",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(24.dp)
                )
                Text(text = "Block", fontSize = 12.sp, color = Color.Gray)
            }
        }


        ExtendedFloatingActionButton(text = { Text(text ="Add",color= MaterialTheme.colorScheme.onPrimary) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .zIndex(1f)
                .offset(y = (-56).dp),
            icon = { Icon(Icons.Filled.AddCircle,
                tint = MaterialTheme.colorScheme.onPrimary,
                contentDescription = "Home") },
            onClick = {  },
            contentColor = MaterialTheme.colorScheme.onPrimary,
            backgroundColor = MaterialTheme.colorScheme.primary)
    }
}

@Composable
fun M3BottomAppBar(){
    androidx.compose.material3.BottomAppBar(actions = {})
}



@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview("light_mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private  fun Preview(){
    MaterialTheme {

        val context = LocalContext.current
        val navController = NavController(context)
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        Column {
            BottomNavAppBar(navController = navController)
            Spacer(modifier =Modifier.height(100.dp))


            CustomBottomNavBar(navController = navController)
        }
    }
}


