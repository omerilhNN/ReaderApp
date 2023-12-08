package com.omrilhn.readerapp.presentation.login

import androidx.lifecycle.ViewModel
import com.omrilhn.readerapp.core.domain.states.StandardTextFieldState
import com.omrilhn.readerapp.data.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) : ViewModel() {
    private val _emailText = MutableStateFlow<String>("")
    val emailTextState: StateFlow<String> get() = _emailText

    private val _passwordText = MutableStateFlow<String>("")
    val passwordText : StateFlow<String> get() = _passwordText

    fun setEmailText(email:String){
        _emailText.value = email
    }
    fun setPasswordText(password:String){
        _passwordText.value = password
    }


}