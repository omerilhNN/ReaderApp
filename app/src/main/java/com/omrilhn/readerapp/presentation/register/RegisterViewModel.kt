package com.omrilhn.readerapp.presentation.register

import android.content.ContentValues
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.omrilhn.readerapp.core.domain.states.PasswordTextFieldState
import com.omrilhn.readerapp.core.domain.states.StandardTextFieldState
import com.omrilhn.readerapp.data.repository.AuthRepository
import com.omrilhn.readerapp.presentation.components.StandardInputField
import com.omrilhn.readerapp.utils.Resource
import com.omrilhn.readerapp.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
      private val  authRepository: AuthRepository
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
        if(_registerState.value.isLoading == false){
            //when LOADING is false update isLoading property by changing it to true
            _registerState.update {currentState->
                currentState.copy(isLoading = true)
            }
            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener { task->
                    if(task.isSuccessful){
                        _registerState.update {currentState-> currentState.copy(result = Resource.Success(Unit)) }
                        val displayName = task.result?.user?.email?.split('@')?.get(0)
                        authRepository.createUser(displayName)
                        home?.invoke()
                    }else{
                        _registerState.update {currentState-> currentState.copy(result = Resource.Error("ERROR registering")) }
                        Log.d(ContentValues.TAG,"createUserWithEmailAndPassword: ${task.result.toString()}")
                    }
                    _registerState.update{currentState->
                        currentState.copy(isLoading = false)
                    }
                }.addOnFailureListener {
                    Log.d("ERROR",it.toString())
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
    fun togglePassword( ){
        _registerState.update {currentState->
            currentState.copy(isPasswordVisible = !registerState.value.isPasswordVisible)
        }
    }
    //TODO: COMMAND DESIGN PATTERN WILL BE IMPLEMENTED
//    fun onEvent(event: RegisterEvent,navigate: () -> Unit = {}) {
//        when(event) {
////            is RegisterEvent.EnteredUsername -> {
////                _usernameState.value = _usernameState.value.copy(
////                    text = event.value
////                )
////            }
//            is RegisterEvent.EnteredEmail -> {
//                _emailState.update {currentState->
//                    currentState.copy(text = event.value)
//                }
//            }
//            is RegisterEvent.EnteredPassword -> {
//               _passwordState.update {currentState->
//                   currentState.copy(text = event.value)
//               }
//            }
//            is RegisterEvent.TogglePasswordVisibility -> {
//                _passwordState.value = _passwordState.value.copy(
//                    isPasswordVisible = !passwordState.value.isPasswordVisible
//                )
//            }
//            is RegisterEvent.Register -> {
//                register(navigate)
//            }
//        }
//
//    }
}