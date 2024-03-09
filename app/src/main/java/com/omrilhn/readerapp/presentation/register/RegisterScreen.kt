package com.omrilhn.readerapp.presentation.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.omrilhn.readerapp.R
import com.omrilhn.readerapp.presentation.components.StandardInputField
import com.omrilhn.readerapp.ui.theme.SpaceLarge
import com.omrilhn.readerapp.ui.theme.SpaceMedium

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel:RegisterViewModel = hiltViewModel(),
    onRegisterClick: () -> Unit
){
    val emailState = viewModel.emailState.collectAsState()
    val passwordState = viewModel.passwordState.collectAsState()
    val registerState = viewModel.registerState.collectAsState()
    val emailErrorState = viewModel.emailErrorState.collectAsState()
    val passwordErrorState = viewModel.passwordErrorState.collectAsState()
    val context = LocalContext.current

    val registerButtonEnabled by remember {
        derivedStateOf {
            //Only checks if the end result condition which is down below is changed -> when using only a remember(condition) it will
            //recompose everytime that value of the condition changes
            emailErrorState.value == null && passwordErrorState.value == null
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = SpaceMedium,
                end = SpaceMedium,
                top = SpaceLarge,
                bottom = 20.dp
            )
    ){
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(SpaceMedium)
                ) {
            Spacer(modifier = Modifier.height(45.dp))

            Image(painter = painterResource(id = R.drawable.booklogo),
                contentDescription = "Book logo",
                modifier = Modifier
                    .align(CenterHorizontally)
                    .height(100.dp)
                    .width(100.dp),
            )
            Spacer(modifier = Modifier.height(10.dp))

//            Row(horizontalArrangement = Arrangement.Center){
                Text(text = "Create an Account",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(4.dp),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            Text(text = stringResource(id = R.string.create_acct),
                modifier = Modifier.padding(4.dp),
                textAlign = TextAlign.Start)
//            }



            Spacer(modifier = Modifier.height(10.dp))

                StandardInputField(
                    text = emailState.value.text,
                    onValueChange = {
                        viewModel.setEmailText(it)
                        val emailValidate = viewModel.validateEmail(emailState.value.text)
                        viewModel.setEmailError(emailValidate.errorMessage)

                    },
                    keyboardType = KeyboardType.Email,
                    hint = stringResource(id = R.string.login_hint),
                    label = stringResource(id = R.string.email)

                )


            if (emailState.value.text.isNotEmpty() && emailErrorState.value != null) {
                Text(
                    text = emailErrorState.value!!, // !! güvenli olmayabilir, güvenli bir şekilde işlemek için kontrol edin
                    color = MaterialTheme.colors.error
                )
            }

                Spacer(modifier = Modifier.height(35.dp))

                StandardInputField(
                    text = passwordState.value.text,
                    onValueChange = {
                        viewModel.setPasswordText(it)
                        val passwordValidate = viewModel.validatePassword(passwordState.value.text)
                        viewModel.setPasswordError(passwordValidate.errorMessage)

                    },
                    hint = stringResource(id = R.string.password_hint),
                    label = stringResource(id = R.string.password),
                    keyboardType = KeyboardType.Password,
                    isPasswordVisible = passwordState.value.isPasswordVisible,
                    onPasswordToggleClick = {
                        viewModel.togglePassword()
                    }
                )


            if (passwordState.value.text.isNotEmpty() && passwordErrorState.value != null) {
                Text(
                    text = passwordErrorState.value!!, // !! güvenli olmayabilir, güvenli bir şekilde işlemek için kontrol edin
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }else{ Text(text = "")

            }

            Spacer(modifier = Modifier.height(25.dp))

            Button(modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(0.5f).height(50.dp),
                onClick = {
                    onRegisterClick()
                    viewModel.createWithEmailAndPassword(emailState.value.text,passwordState.value.text)

                },enabled = registerButtonEnabled
            ) {
                Text(
                    text = stringResource(id = R.string.signUp),
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
            if(registerState.value.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(CenterHorizontally)
                )
            }

            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier.fillMaxSize()){
                Text(
                text = buildAnnotatedString {
                    append(stringResource(id = R.string.already_have_an_account))
                    append(" ")
                    val loginText = stringResource(id = R.string.login)
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colors.primary
                        )
                    ) {
                        append(loginText)
                    }
                },
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .clickable {
                        navController.popBackStack()
                    }
            )

            }


        }
    }
}


