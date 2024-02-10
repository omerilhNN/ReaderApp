package com.omrilhn.readerapp.presentation.register

import com.omrilhn.readerapp.utils.SimpleResource

data class RegisterState(
    val isLoading:Boolean = false,
    val isPasswordVisible :Boolean  = false,
    val result:SimpleResource ?= null
)