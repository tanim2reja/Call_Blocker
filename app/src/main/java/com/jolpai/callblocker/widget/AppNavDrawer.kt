package com.jolpai.callblocker.widget

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.jolpai.callblocker.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavDrawer(navController: NavController, onClose: () -> Unit) {
    Column (modifier = Modifier
        .fillMaxHeight()
        .padding(end = 64.dp)
        .background(color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(0.dp))

        ){

            Box(modifier = Modifier.height(200.dp).fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.Center) {
                Text(
                    text = "Call Blocker",
                    modifier = Modifier.padding(16.dp),
                    style = TextStyle(fontSize = 18.sp, color = Color.Black)
                )
            }

            ModalDrawerSheet(modifier = Modifier,
                drawerShape = RoundedCornerShape(0.dp),
                drawerContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = .5f),) {
                Text(
                    text = "Menu 1",
                    modifier = Modifier.padding(16.dp),
                    style = TextStyle(fontSize = 18.sp, color = Color.Black)
                )
                Divider()
                Text(
                    text = "Menu 2",
                    modifier = Modifier.padding(16.dp),
                    style = TextStyle(fontSize = 18.sp, color = Color.Black)
                )
            }

    }
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
            AppNavDrawer(navController = navController, onClose = {})
        }
    }
}