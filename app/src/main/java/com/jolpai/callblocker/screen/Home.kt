package com.jolpai.callblocker.screen

import android.app.role.RoleManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.telecom.TelecomManager
import android.util.Size
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.info.callblocker.R
import com.jolpai.callblocker.ui.theme.CallBlockerTheme
import com.jolpai.callblocker.widget.ColoredText
import com.jolpai.callblocker.widget.StatusBarBackground

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun Home(onNavigate: (String) -> Unit) {
    val isBlocking = remember { mutableStateOf(true) }
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }


    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = MaterialTheme.colorScheme.background),) {
        StatusBarBackground()

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .weight(.2f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Settings,
                    modifier = Modifier.size(30.dp),
                    contentDescription = "Settings",
                    tint = MaterialTheme.colorScheme.secondary)
            }

            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = ImageVector.vectorResource(id = R.drawable.refresh),
                    contentDescription = "Settings",
                    modifier = Modifier.size(30.dp),
                    tint = Color.Unspecified)
            }

        }
        
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .weight(.6f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center){
            Box(contentAlignment = Alignment.Center){
                //GifImage(R.drawable.emit)

                Image(painter = painterResource(id = if(isBlocking.value){ R.drawable.unblock} else{ R.drawable.block}),
                    modifier = Modifier.size(200.dp),
                    contentDescription ="" )
            }


            
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(.4f)){
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 48.dp, end = 48.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text(text = "Blocking",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary)
                    ColoredText(
                        modifier = Modifier,
                        firstText = "is",
                        firstColor = MaterialTheme.colorScheme.secondary,
                        secondText = if(isBlocking.value){" ON"}else{" OFF"},
                        secondColor = if(isBlocking.value){MaterialTheme.colorScheme.primary}else{MaterialTheme.colorScheme.error}
                    )
                }

                var checked by remember { mutableStateOf(true) }

                if(isBlocking.value){
                    Card(modifier = Modifier
                        .width(94.dp)
                        .height(44.dp)
                        /*.background(
                            color = Color.White,
                            shape = RoundedCornerShape(24.dp)
                        )*/
                        .clip(shape = RoundedCornerShape(24.dp))
                        .clickable {
                            isBlocking.value = false
                            //RequestDefaultDialer()
                        },
                        shape = RoundedCornerShape(24.dp),
                        /*colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface, // Set the card's background color to white
                            contentColor = MaterialTheme.colorScheme.surface // Set the content color (optional)
                        ),*/
                        elevation = CardDefaults.cardElevation(4.dp)){
                        Row (modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 2.dp, end = 2.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically){
                            Text(text = "ON",
                                modifier = Modifier.padding(start = 4.dp, end  = 4.dp),

                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Thin,
                                color = MaterialTheme.colorScheme.secondary)
                            Box(modifier = Modifier
                                .size(40.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = CircleShape
                                ))
                        }

                    }
                }else{
                    Card(modifier = Modifier
                        .width(94.dp)
                        .height(44.dp)
                        /*.background(
                            color = Color.White,
                            shape = RoundedCornerShape(24.dp)
                        )*/
                        .clip(shape = RoundedCornerShape(24.dp))
                        .clickable {
                            isBlocking.value = true
                        },
                        shape = RoundedCornerShape(24.dp),
                        /*colors = CardDefaults.cardColors(
                            containerColor = Color.White, // Set the card's background color to white
                            contentColor = Color.White // Set the content color (optional)
                        ),*/
                        elevation = CardDefaults.cardElevation(4.dp)){
                        Row (modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 2.dp, end = 2.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically){
                            Box(modifier = Modifier
                                .size(40.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.error,
                                    shape = CircleShape
                                ))

                            Text(text = "OFF",
                                modifier = Modifier.padding(start = 4.dp, end  = 4.dp),
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Thin,
                                color = MaterialTheme.colorScheme.secondary)

                        }

                    }
                }



            }

        }


        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
fun DefaultDialerDialog(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Set as Default Dialer") },
        text = { Text("To use this app as a call blocker, you need to set it as the default dialer.") },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("Set as Default")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun GifImage(url: Int) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .decoderFactory(GifDecoder.Factory())
            .build()
    )

    Image(
        painter = painter,
        contentDescription = "GIF Image",
        modifier = Modifier.size(300.dp)
    )
}

@RequiresApi(Build.VERSION_CODES.Q)
@Preview(showBackground = true)
@Composable
private fun Preview() {
    CallBlockerTheme {
        val context = LocalContext.current
        val navController = NavController(context)
        Home(onNavigate = {})
    }
}