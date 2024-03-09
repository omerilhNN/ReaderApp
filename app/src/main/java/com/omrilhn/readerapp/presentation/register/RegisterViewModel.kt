package com.omrilhn.readerapp.presentation.register

import android.content.ContentValues
import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.derivedStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.omrilhn.readerapp.core.domain.states.PasswordTextFieldState
import com.omrilhn.readerapp.core.domain.states.RegistrationFormEvent
import com.omrilhn.readerapp.core.domain.states.StandardTextFieldState
import com.omrilhn.readerapp.core.domain.use_case.ValidateEmail
import com.omrilhn.readerapp.core.domain.use_case.ValidatePassword
import com.omrilhn.readerapp.core.domain.use_case.ValidationResult
import com.omrilhn.readerapp.data.repository.AuthRepository
import com.omrilhn.readerapp.utils.Constants
import com.omrilhn.readerapp.utils.Resource
import com.omrilhn.readerapp.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
      private val  authRepository: AuthRepository,
      private val validateEmail:ValidateEmail,
      private val validatePassword: ValidatePassword

):ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val _emailState = MutableStateFlow(StandardTextFieldState())
    val emailState : StateFlow<StandardTextFieldState> get() = _emailState.asStateFlow()

    private val _passwordState = MutableStateFlow(PasswordTextFieldState())
    val passwordState : StateFlow<PasswordTextFieldState> get() = _passwordState.asStateFlow()

    //Error handling of InputFields
    private val _emailErrorState = MutableStateFlow<String?>(null)
    val emailErrorState: StateFlow<String?> get() = _emailErrorState.asStateFlow()
    private val _passwordErrorState = MutableStateFlow<String?>(null)
    val passwordErrorState: StateFlow<String?> get() = _passwordErrorState.asStateFlow()
    //***
    private val _registerState = MutableStateFlow(RegisterState())
    val registerState: StateFlow<RegisterState> get() = _registerState.asStateFlow()



    fun createWithEmailAndPassword(
        email:String,
        password:String,
        home:(() -> Unit)? = null
    ) = viewModelScope.launch{
        if(!_registerState.value.isLoading){
            //when LOADING is false update isLoading property by changing it to true
            _registerState.update {currentState->
                currentState.copy(isLoading = true)
            }
            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener { task->
                    if(task.isSuccessful){
                        _registerState.update {
                                currentState-> currentState.copy(result = Resource.Success(Unit))
                        }
                        val displayName = task.result?.user?.email?.split('@')?.get(0)
                        Log.d("ERROR","authRepo createUser ERROR")
                        authRepository.createUser(displayName)
                        home?.invoke()
                    }else{
                        _registerState.update {currentState-> currentState.copy(result = Resource.Error("ERROR registering")) }
                        Log.d("ERROR","createUserWithEmailAndPassword: ${task.result.toString()}")
                    }
                }.addOnFailureListener {
                    Log.d("ERROR",it.toString())
                }
            _registerState.update{currentState->
                currentState.copy(isLoading = false)
            }
        }
    }




    fun setEmailText(email:String){
        _emailState.update{currentState->
            currentState.copy(text = email)
        }
    }
    fun setPasswordText(password:String){
        _passwordState.update{currentState->
            currentState.copy(text = password)
        }
    }
    fun setEmailError(error: String?) {
        _emailErrorState.value = error
    }

    fun setPasswordError(error: String?) {
        _passwordErrorState.value = error
    }
    fun validateEmail(email:String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(false, "The email can't be blank")
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(false, "The email address is badly formatted.")
        }

        return ValidationResult(true, null)
    }
    fun validatePassword(password:String):ValidationResult{
        if(password.length < Constants.MIN_PASSWORD_LENGTH){
            return ValidationResult(false,errorMessage = "The password needs to consist of at least 6 characters")
        }
        val containsLettersAndDigits = password.any {it.isDigit()} && password.any{it.isLetter()}
        if(!containsLettersAndDigits){
            return ValidationResult(false,"The password needs to contain at least one letter and one digit")
        }
        if(password.isBlank()) return ValidationResult(false,"The password can't be blank")

        return ValidationResult(true,null)
    }
    fun togglePassword( ){
        _passwordState.update {currentState->
            currentState.copy(isPasswordVisible = !_passwordState.value.isPasswordVisible)
        }
    }

}