package com.omrilhn.readerapp.presentation.login

import androidx.lifecycle.ViewModel
import com.omrilhn.readerapp.core.domain.states.StandardTextFieldState
import com.omrilhn.readerapp.data.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) : ViewModel() {
    private val _emailTextState = MutableStateFlow(StandardTextFieldState())
    val emailTextState: StateFlow<StandardTextFieldState> = _emailTextState
    /* EMAIL TEXT before using State
     private val _emailText = MutableStateFlow<String>("")
    val emailTextState: StateFlow<String> get() = _emailText*/

    private val _passwordText = MutableStateFlow<String>("")
    val passwordText : StateFlow<String> get() = _passwordText

    fun setEmailText(email:String){
        //Setting email text for MutableStateFlow using State
        _emailTextState.update {currentState-> //Also use StateFlow's update method
            currentState.copy(text = email)
        }

        //  _emailText.value = email -> before using STATE for changing value
    }
    fun setPasswordText(password:String){
        _passwordText.value = password
    }


}