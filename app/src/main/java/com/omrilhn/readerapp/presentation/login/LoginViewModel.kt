package com.omrilhn.readerapp.presentation.login

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.omrilhn.readerapp.data.repository.AuthRepository
import com.omrilhn.readerapp.core.domain.states.PasswordTextFieldState
import com.omrilhn.readerapp.core.domain.states.StandardTextFieldState
import com.omrilhn.readerapp.core.domain.use_case.LoginUseCase
import com.omrilhn.readerapp.navigation.Screen
import com.omrilhn.readerapp.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authRepository: AuthRepository,
                                         private val loginUseCase: LoginUseCase) : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

    private val _emailTextState = MutableStateFlow(StandardTextFieldState())
    val emailTextState: StateFlow<StandardTextFieldState> = _emailTextState
    /* EMAIL TEXT before using State
     private val _emailText = MutableStateFlow<String>("")
    val emailTextState: StateFlow<String> get() = _emailText*/

    private val _passwordTextState = MutableStateFlow(PasswordTextFieldState())
    val passwordTextState:StateFlow<PasswordTextFieldState> = _passwordTextState
    //PASSWORD TEXT before using State
//    private val _passwordText = MutableStateFlow<String>("")
//    val passwordText : StateFlow<String> get() = _passwordText

    private val _emailError = mutableStateOf("")
    val emailError: State<String> = _emailError

    private val _passwordError = mutableStateOf("")
    val passwordError: State<String> = _passwordError

    private val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> = _loginState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _validInput = mutableStateOf(
        emailTextState.value.text.trim().isNotEmpty() && passwordTextState.value.text.trim().isNotEmpty())
    val validInput: State<Boolean> = _validInput

//    fun setEmailText(email:String){
//        //StateFlows -> update Property
//        //Setting email text for MutableStateFlow using State
//        _emailTextState.update {currentState-> //Also use StateFlow's update method
//            currentState.copy(text = email)
//        }
//
//        //  _emailText.value = email -> before using STATE for changing value
//    }
//    fun setPasswordText(password:String){
//        _passwordTextState.update {currentState->
//            currentState.copy(text = password)
//        }
//    }
    fun onEvent(event:LoginEvent,home: (() -> Unit)?=null){
        when(event){
            is LoginEvent.EnteredEmail -> {
                _emailTextState.update { currentState -> //Also use StateFlow's update method
                    currentState.copy(text = event.email)
                }
            }
            is LoginEvent.EnteredPassword->{
                _passwordTextState.update {currentState->
                    currentState.copy(text = event.password)
                }
            }
            is LoginEvent.TogglePasswordVisibility -> {
                _loginState.value = loginState.value.copy(
                    isPasswordVisible = !loginState.value.isPasswordVisible
                )
            }
            is LoginEvent.Login -> {
                viewModelScope.launch {
                    _loginState.value = loginState.value.copy(isLoading = true)
                    signInWithEmailAndPassword(
                        _emailTextState.value.text,
                        _passwordTextState.value.text,
                     ){
                       home?.invoke() //If it is not null then execute it .
                    }
//                    val loginResult = loginUseCase(
//                        email = emailTextState.value.text,
//                        password = passwordTextState.value.text
//                    )

                    _loginState.value = loginState.value.copy(isLoading = false)
//                    if(loginResult.emailError != null){
//                       _emailTextState.update {currentState->
//                           currentState.copy(error = loginResult.emailError)
//                       }
//                    }
//                    if(loginResult.passwordError != null){
//                        _passwordTextState.update {currentState->
//                            currentState.copy(error = loginResult.passwordError)
//                        }
//                    }
                }
            }
            is LoginEvent.Register -> {
                viewModelScope.launch {
                    createWithEmailAndPassword(
                        _emailTextState.value.text,
                        _passwordTextState.value.text
                    ){
                        // isRegistration correct -> navigate to HomeScreen
                        home?.invoke()
                    }
                }
            }

            else -> { return}
        }
    }
    fun signInWithEmailAndPassword(email:String,password:String,home: (() -> Unit)?)
    =viewModelScope.launch{
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        Log.d("FB", "signInWithEmailAndPassword: Yayayay! ${task.result.toString()}")
                        //Todo: take them home
                        home?.invoke() // when Home function is not null -> execute

                    }else {
                        Log.d("FB", "signInWithEmailAndPassword: ${task.result.toString()}")
                    }


                }

        }catch (ex: Exception){
            Log.d("FB", "signInWithEmailAndPassword: ${ex.message}")
        }
    }
    fun createWithEmailAndPassword(
        email:String,
        password:String,
        home:(() -> Unit)?
    ){
        if(!_loginState.value.isLoading){
            //when LOADING is false update isLoading property by changing it to true
            _loginState.update {currentState->
                currentState.copy(isLoading = true)
            }
            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener { task->
                    if(task.isSuccessful){
                        val displayName = task.result?.user?.email?.split('@')?.get(0)
                        authRepository.createUser(displayName)
                        home?.invoke()
                    }else{
                        Log.d(TAG,"createUserWithEmailAndPassword: ${task.result.toString()}")
                    }
                    _loginState.update{currentState->
                        currentState.copy(isLoading = false)
                    }
                }
        }
    }

}