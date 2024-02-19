package com.omrilhn.readerapp.core.domain.use_case

import android.util.Patterns
import com.omrilhn.readerapp.utils.Constants.MIN_PASSWORD_LENGTH

class ValidatePassword {
    operator fun invoke(password:String):ValidationResult{
        if(password.length < MIN_PASSWORD_LENGTH){
            return ValidationResult(false,errorMessage = "The password needs to consist of at least 6 characters")
        }
        val containsLettersAndDigits = password.any {it.isDigit()} && password.any{it.isLetter()}
        if(!containsLettersAndDigits){
            return ValidationResult(false,"The password needs to contain at least one letter and one digit")
        }
        if(password.isBlank()) return ValidationResult(false,"The password can't be blank")

        return ValidationResult(true,null)
    }
}