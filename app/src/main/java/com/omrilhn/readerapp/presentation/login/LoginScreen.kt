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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
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
import com.omrilhn.readerapp.ui.theme.SpaceSmall

@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel:LoginViewModel = hiltViewModel(),
    onDone: (String, String) -> Unit = { email, pwd ->},
    isCreateAccount:Boolean = false,
    onLoginClick: ()->Unit)
{
    val emailText = loginViewModel.emailTextState.collectAsState()
    val passwordText = loginViewModel.passwordTextState.collectAsState()
    val valid = remember(emailText.value.text, passwordText.value.text) {
        emailText.value.text.trim().isNotEmpty() && passwordText.value.text.trim().isNotEmpty()}

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
            if (isCreateAccount) Text(text = stringResource(id = R.string.create_acct),
                modifier = Modifier.padding(4.dp)) else Text("")
            Spacer(modifier = Modifier.height(SpaceMedium))

            StandardInputField(
                text = emailText.value.text ,
                hint = stringResource(id = R.string.email),
                 onValueChange ={
                     loginViewModel.onEvent(LoginEvent.EnteredEmail(it))
                 },
                keyboardType = KeyboardType.Email,
                error = loginViewModel.emailError.value)

            Spacer(modifier = Modifier.height(SpaceSmall))

            StandardInputField(
                text = passwordText.value.text,
                hint = stringResource(id = R.string.password),
                onValueChange = {
                    loginViewModel.onEvent(LoginEvent.EnteredPassword(it))
                },
                keyboardType = KeyboardType.Password,
                error = loginViewModel.passwordError.value,
                onAction = KeyboardActions{
                    if(!valid) return@KeyboardActions
                    onDone( emailText.value.text.trim(),passwordText.value.text.trim())
                }
            )


        }
    }


}