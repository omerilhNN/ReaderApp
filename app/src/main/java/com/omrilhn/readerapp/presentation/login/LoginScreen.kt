package com.omrilhn.readerapp.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.omrilhn.readerapp.R
import com.omrilhn.readerapp.navigation.Screen
import com.omrilhn.readerapp.presentation.components.StandardInputField
import com.omrilhn.readerapp.presentation.components.UserForm
import com.omrilhn.readerapp.ui.theme.SpaceLarge
import com.omrilhn.readerapp.ui.theme.SpaceMedium
import com.omrilhn.readerapp.ui.theme.SpaceSmall
import com.omrilhn.readerapp.utils.AuthError

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
//    navController: NavController,
    loginViewModel:LoginViewModel = hiltViewModel(),
    onNavigate:(String) -> Unit = {},
    isCreateAccount:Boolean = false,
    onLoginClick: ()->Unit = {})
{
    val emailText = loginViewModel.emailTextState.collectAsState()
    val passwordText = loginViewModel.passwordTextState.collectAsState()
    val isValid = loginViewModel.validInput.value //Email-Password inputs isValid?
    val state = loginViewModel.loginState.collectAsState()
    val context = LocalContext.current

    val showLoginForm = rememberSaveable{ mutableStateOf(true)}

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(
            start = SpaceLarge,
            end = SpaceLarge,
            top = SpaceLarge,
            bottom = 20.dp
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
                        .align(Alignment.CenterHorizontally)
                        .height(100.dp)
                        .width(100.dp),
                    )
//            }
            Spacer(modifier = Modifier.height(SpaceMedium))

                Text(text = stringResource(id = R.string.create_acct),
                    modifier = Modifier.padding(4.dp))
            StandardInputField(
                text = emailText.value.text,
                onValueChange = {
                    loginViewModel.onEvent(LoginEvent.EnteredEmail(it))
                },
                keyboardType = KeyboardType.Email,
                error = when (emailText.value.error) {
                    is AuthError.FieldEmpty -> stringResource(id = R.string.error_field_empty)
                    else -> ""
                },
                hint = stringResource(id = R.string.login_hint)
            )


            Spacer(modifier = Modifier.height(15.dp))

            StandardInputField(
                text = passwordText.value.text,
                onValueChange = {
                    loginViewModel.onEvent(LoginEvent.EnteredPassword(it))
                },
                hint = stringResource(id = R.string.password_hint),
                keyboardType = KeyboardType.Password,
                error = when (passwordText.value.error) {
                    is AuthError.FieldEmpty -> stringResource(id = R.string.error_field_empty)
                    else -> ""
                },
                isPasswordVisible = state.value.isPasswordVisible,
                onPasswordToggleClick = {
                    loginViewModel.onEvent(LoginEvent.TogglePasswordVisibility)
                }
            )

            Spacer(modifier = Modifier.height(SpaceMedium))
            Button(
                onClick = onLoginClick
//                {
//                    loginViewModel.onEvent(LoginEvent.Login){
//                    }
                ,
                modifier = Modifier
                    .align(Alignment.End)
            ) {
                Text(
                    text = stringResource(id = R.string.login),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            if (state.value.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(CenterHorizontally))
            }
        }
        Text(
            text = buildAnnotatedString {
                append(stringResource(id = R.string.dont_have_an_account_yet))
                append(" ")
                val signUpText = stringResource(id = R.string.signUp)
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary
                    )
                ) {
                    append(signUpText)
                }
            },
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .clickable {
                    onNavigate(
                        Screen.RegisterScreen.route
                    )
                }
        )
    }

}
