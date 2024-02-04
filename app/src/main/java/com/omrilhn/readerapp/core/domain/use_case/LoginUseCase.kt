package com.omrilhn.readerapp.core.domain.use_case

import com.omrilhn.readerapp.data.model.LoginResult
import com.omrilhn.readerapp.data.repository.AuthRepository
import com.omrilhn.readerapp.utils.AuthError
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authRepository: AuthRepository) {
    //TODO: Implement more specific Email-Password input checks by creating LoginResult,AuthError classes.
    //todo: "NOT IMPLEMENTED YET"
    suspend operator fun invoke(email:String,password:String): com.omrilhn.readerapp.data.model.LoginResult {
        val emailError = if(email.isBlank()) AuthError.FieldEmpty else null
        val passwordError = if(password.isBlank()) AuthError.FieldEmpty else null

        if(emailError != null || passwordError != null){
            return com.omrilhn.readerapp.data.model.LoginResult(emailError, passwordError)
        }
        return com.omrilhn.readerapp.data.model.LoginResult(
//            result = authRepository.login(email,password)
        )
    }
}