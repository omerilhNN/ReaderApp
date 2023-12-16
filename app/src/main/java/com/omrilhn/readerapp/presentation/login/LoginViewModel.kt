package com.omrilhn.readerapp.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omrilhn.readerapp.core.domain.states.PasswordTextFieldState
import com.omrilhn.readerapp.core.domain.states.StandardTextFieldState
import com.omrilhn.readerapp.data.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) : ViewModel() {
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

    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState

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
    fun setIsEmailError(error:String){
        _emailError.value = error
    }
    fun setIsPasswordError(error:String){
        _passwordError.value = error
    }
    fun onEvent(event:LoginEvent){
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
                    
                }

            }
        }
    }

}