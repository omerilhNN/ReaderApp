package com.omrilhn.readerapp.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.omrilhn.readerapp.R
import com.omrilhn.readerapp.presentation.components.StandardInputField
import com.omrilhn.readerapp.ui.theme.SpaceLarge
import com.omrilhn.readerapp.ui.theme.SpaceMedium

@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel:LoginViewModel = hiltViewModel(),
    onLoginClick: ()->Unit)
{
    val emailText = loginViewModel.emailTextState.collectAsState()
    val passwordText = loginViewModel.passwordText.collectAsState()

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(
            start = SpaceLarge,
            end = SpaceLarge,
            top = SpaceLarge,
            bottom = 50.dp
        )){
        Column (verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(SpaceMedium)
                .align(Alignment.Center)){
            //ROW - for logo of app
//            Row(modifier = Modifier.fillMaxSize(),
//                horizontalArrangement = Arrangement.Center) {
                Image(painter = painterResource(id = R.drawable.booklogo),
                    contentDescription = "Book logo",
                    modifier = Modifier
                        .height(150.dp)
                        .width(150.dp))
//            }

            Spacer(modifier = Modifier.height(SpaceMedium))

            StandardInputField(
                text = emailText.value ,
                hint = stringResource(id = R.string.email),
                 onValueChange ={
                     loginViewModel.setEmailText(it)
                 },
                keyboardType = KeyboardType.Email,
                error = loginViewModel.)


        }
    }


}