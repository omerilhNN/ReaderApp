package com.omrilhn.readerapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.omrilhn.readerapp.R
import com.omrilhn.readerapp.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderAppBar(
    title:String,
    showProfile:Boolean = true,
    navController:NavController
){
    TopAppBar(
        title = {
                Row(verticalAlignment = Alignment.CenterVertically ){
                    if(showProfile){
                        Image(painter = painterResource(id = R.drawable.booklogo),
                            contentDescription = "Logo icon",
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .scale(0.9f))
                    }
                    Text(text = title,
                        color = Color.Red.copy(alpha = 0.7f),
                        style = TextStyle(fontWeight = FontWeight.Bold,fontSize = 20.sp )
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }

        },
        actions = {
                  IconButton(onClick = {
                      FirebaseAuth.getInstance().signOut().run{//execute run block every time
                      //  even if signOut process didn't finish.
                        navController.navigate(Screen.LoginScreen.route)
                      }
                  }){
                      Icon(imageVector = Icons.Filled.Logout, contentDescription = "Logout",
                          tint = Color.Green.copy(alpha = 0.4f))

                  }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.Transparent
        )
    )

}

