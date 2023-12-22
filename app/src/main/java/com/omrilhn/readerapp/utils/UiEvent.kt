package com.omrilhn.readerapp.utils


sealed class UiEvent : Event() {
    data class ShowSnackbar(val uiText : String) :UiEvent()
    data class Navigate(val route :String) :UiEvent()
    object NavigateUp : UiEvent()
    object OnLogin : UiEvent()

}
