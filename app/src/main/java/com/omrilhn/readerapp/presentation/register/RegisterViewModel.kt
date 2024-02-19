package com.omrilhn.readerapp.presentation.register

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.omrilhn.readerapp.core.domain.states.PasswordTextFieldState
import com.omrilhn.readerapp.core.domain.states.RegistrationFormEvent
import com.omrilhn.readerapp.core.domain.states.StandardTextFieldState
import com.omrilhn.readerapp.core.domain.use_case.ValidateEmail
import com.omrilhn.readerapp.core.domain.use_case.ValidatePassword
import com.omrilhn.readerapp.data.repository.AuthRepository
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
      private val validateEmail: ValidateEmail = ValidateEmail(),
      private val validatePassword: ValidatePassword = ValidatePassword()
):ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val _emailState = MutableStateFlow(StandardTextFieldState())
    val emailState : StateFlow<StandardTextFieldState> get() = _emailState.asStateFlow()

    private val _passwordState = MutableStateFlow(PasswordTextFieldState())
    val passwordState : StateFlow<PasswordTextFieldState> get() = _passwordState.asStateFlow()

    private val _registerState = MutableStateFlow(RegisterState())
    val registerState: StateFlow<RegisterState> get() = _registerState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _onRegister = MutableSharedFlow<Unit>(replay = 1)
    val onRegister = _onRegister.asSharedFlow()

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
                        _registerState.update {currentState-> currentState.copy(result = Resource.Success(Unit)) }
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
    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()
    fun onEvent(event: RegistrationFormEvent) {
        when(event) {
            is RegistrationFormEvent.EmailChanged -> {
                setEmailText(event.email)
            }
            is RegistrationFormEvent.PasswordChanged -> {
                setPasswordText(event.password)
            }

            is RegistrationFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val emailResult = validateEmail.invoke(_emailState.value.text)
        val passwordResult = validatePassword.invoke(_passwordState.value.text)

        val hasError = listOf(
            emailResult,
            passwordResult
        ).any { !it.successful }

        if(hasError) {
            _emailState.update {
                it.copy(error = emailResult.errorMessage )
            }
            _passwordState.update {
                it.copy(error = passwordResult.errorMessage)
            }
            return
        }
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    sealed class ValidationEvent {
        object Success: ValidationEvent()
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
    fun togglePassword( ){
        _passwordState.update {currentState->
            currentState.copy(isPasswordVisible = !_passwordState.value.isPasswordVisible)
        }
    }


}