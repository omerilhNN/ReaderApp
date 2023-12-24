package com.omrilhn.readerapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.omrilhn.readerapp.R
import com.omrilhn.readerapp.presentation.login.LoginEvent
import com.omrilhn.readerapp.presentation.login.LoginViewModel
import com.omrilhn.readerapp.ui.theme.SpaceMedium

@ExperimentalComposeUiApi
@Composable
fun UserForm(
    loading: Boolean = false,
    isCreateAccount: Boolean = false,
    viewModel: LoginViewModel = hiltViewModel(),
    onDone: (String, String) -> Unit? = { email, pwd ->}
) {
    val emailTextState = viewModel.emailTextState.collectAsState()
    val passwordTextState = viewModel.passwordTextState.collectAsState()
    val state = viewModel.loginState.collectAsState()

    val passwordFocusRequest = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current
//    val passwordVisibility = rememberSaveable { mutableStateOf(false) }
    val modifier = Modifier
        .height(250.dp)
        .background(MaterialTheme.colorScheme.background)
        .verticalScroll(rememberScrollState())


    Column(modifier,
        horizontalAlignment = Alignment.CenterHorizontally) {
        if (isCreateAccount) Text(text = stringResource(id = R.string.signUp),
            modifier = Modifier.padding(4.dp)) else Text("")

        StandardInputField(
            text = emailTextState.value.text ,
            label = "E-mail",
            hint = stringResource(id = R.string.email),
            enabled = !loading,
            onValueChange ={
                viewModel.onEvent(LoginEvent.EnteredEmail(it))
            },
            onAction = KeyboardActions{
                passwordFocusRequest.requestFocus()
                keyboardController?.hide()
                },
            //imeAction = ImeAction.Next, //imeAction has been initialized in StandardInputField Cmpsbl.
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next,
            error = viewModel.emailError.value)

        Spacer(modifier = Modifier.height(SpaceMedium))
        
        StandardInputField(

            enabled = !loading,
            text = passwordTextState.value.text,
            label = "Password",
            hint = stringResource(id = R.string.password),
            onValueChange ={
                viewModel.onEvent(LoginEvent.EnteredPassword(it))},
            keyboardType = KeyboardType.Password,
            error = viewModel.passwordError.value,
            onAction = KeyboardActions{
                if(!viewModel.validInput.value) return@KeyboardActions
                onDone(emailTextState.value.text.trim(),passwordTextState.value.text.trim())

                keyboardController?.hide()

            },
            imeAction = ImeAction.Done,
            isSingleLine = true,
            isPasswordVisible = state.value.isPasswordVisible,
            onPasswordToggleClick = {
                viewModel.onEvent(LoginEvent.TogglePasswordVisibility)},
            style = TextStyle(fontSize = 18.sp,color = MaterialTheme.colorScheme.onBackground),


            )
        
        SubmitButton(
            textId =if(isCreateAccount) "Create account" else "Login",
            loading = loading,
            validInputs = viewModel.validInput.value)
        {
            onDone(emailTextState.value.text.trim(),passwordTextState.value.text.trim())
            keyboardController?.hide() //If keyboardController is not null do
        }

    }


}