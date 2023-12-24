package com.omrilhn.readerapp.presentation.login

sealed class LoginEvent{
    data class EnteredEmail(val email:String):LoginEvent()
    data class EnteredPassword(val password:String) :LoginEvent()
    object Login: LoginEvent()

    //It is Contained in LoginEvent because when user clicks SignUp it only changes Texts in the same Screen
    object Register:LoginEvent()
    object TogglePasswordVisibility:LoginEvent()
}
