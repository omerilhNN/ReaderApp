package com.omrilhn.readerapp.core.domain.states

data class PasswordTextFieldState(
    val text : String = "",
    val error : Error ?= null,
    val isPasswordVisible : Boolean = false
)
