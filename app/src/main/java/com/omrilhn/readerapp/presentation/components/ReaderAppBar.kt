package com.omrilhn.readerapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
    icon: ImageVector ?=null,
    showProfile:Boolean = true,
    navController:NavController,
    onBackArrowClicked:() -> Unit = {}
){
    TopAppBar(
        title = {
                Row(verticalAlignment = Alignment.CenterVertically ){
                    if(showProfile){
                        Image(imageVector = Icons.Filled.Book,
                            contentDescription = "Logo icon",
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .scale(0.9f)
                                )
                    }
                    if(icon != null){//If there is an icon show clickable BACK icon
                        Icon(imageVector = icon,contentDescription = "Arrow back",
                            tint = Color.Red.copy(0.7f),
                            modifier = Modifier.clickable { onBackArrowClicked.invoke() })
                    }
                    Spacer(modifier = Modifier.width(40.dp))

                    //Text part which comes after BACK ARROW Icon.
                    Text(text = title,
                        color = Color.Red.copy(alpha = 0.7f),
                        style = TextStyle(fontWeight = FontWeight.Bold,fontSize = 20.sp ),

                        modifier = Modifier.padding(4.dp)

                    )
                }

        },
        actions = {
                  IconButton(onClick = {
                      FirebaseAuth.getInstance().signOut().run{//execute run block every time
                      //  even if signOut process didn't finish.
                        navController.navigate(Screen.LoginScreen.route)
                      }
                  }){
                      if(showProfile) Row(){
                          Icon(imageVector = Icons.Filled.Logout, contentDescription = "Logout",
                              tint = Color.Green.copy(alpha = 0.8f))

                      }
                      else{
                          Box( ){

                          }
                      }

                  }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.Transparent
        )
    )

}

