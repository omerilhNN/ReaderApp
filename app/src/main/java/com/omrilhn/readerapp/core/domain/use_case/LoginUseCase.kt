package com.omrilhn.readerapp.core.domain.use_case

import com.omrilhn.readerapp.core.domain.models.LoginResult
import com.omrilhn.readerapp.data.repository.AuthRepository
import com.omrilhn.readerapp.utils.AuthError
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authRepository: AuthRepository) {
    //TODO: Implement more specific Email-Password input checks by creating LoginResult,AuthError classes.
    //todo: "NOT IMPLEMENTED YET"
    suspend operator fun invoke(email:String,password:String): LoginResult{
        val emailError = if(email.isBlank()) AuthError.FieldEmpty else null
        val passwordError = if(password.isBlank()) AuthError.FieldEmpty else null

        if(emailError != null || passwordError != null){
            return LoginResult(emailError,passwordError)
        }
        return LoginResult(
//            result = authRepository.login(email,password)
        )
    }
}