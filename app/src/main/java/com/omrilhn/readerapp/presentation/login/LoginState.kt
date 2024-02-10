package com.omrilhn.readerapp.presentation.login

import com.omrilhn.readerapp.utils.SimpleResource

data class LoginState(
    val isLoading:Boolean = false,
    val isPasswordVisible :Boolean  = false,
    val result: SimpleResource? = null
)
