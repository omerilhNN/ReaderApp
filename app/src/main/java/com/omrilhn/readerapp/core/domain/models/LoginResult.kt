package com.omrilhn.readerapp.core.domain.models

import com.omrilhn.readerapp.utils.AuthError
import com.omrilhn.readerapp.utils.SimpleResource

data class LoginResult(
    val emailError: AuthError?=null,
    val passwordError:AuthError? =null,
    val result:SimpleResource? =null
    )
