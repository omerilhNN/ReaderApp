package com.omrilhn.readerapp.core.domain.use_case

import android.util.Patterns
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

class ValidateEmail {
    operator fun invoke(email:String):ValidationResult{
        if(email.isBlank()){
            return ValidationResult(false,errorMessage = "The email can't be blank")
        }

        try {
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            return ValidationResult(false, "The email address is badly formatted.")
        }
        return ValidationResult(true,null)
    }
}