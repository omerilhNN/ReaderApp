package com.omrilhn.readerapp.core.domain.states

data class PasswordTextFieldState(
    val text : String = "",
    val error : String ?= null,
    val isPasswordVisible : Boolean = false
)
