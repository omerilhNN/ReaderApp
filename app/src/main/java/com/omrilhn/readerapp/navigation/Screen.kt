package com.omrilhn.readerapp.navigation

sealed class Screen(val route:String){
    object SplashScreen:Screen("splash_screen")
    object LoginScreen: Screen("login_screen")
    object HomeScreen:Screen("home_screen")
    object SearchScreen:Screen("search_screen")
    object UpdateScreen:Screen("update_screen")
}
