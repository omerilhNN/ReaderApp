package com.omrilhn.readerapp.core.domain.states

data class StandardTextFieldState(
    val text : String ="",
    val error:Error ?= null
)
