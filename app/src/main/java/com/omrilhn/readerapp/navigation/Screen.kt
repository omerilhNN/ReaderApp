package com.omrilhn.readerapp.navigation

sealed class Screen(val route:String){
    object SplashScreen:Screen("splash_screen")
    object LoginScreen: Screen("login_screen")
    object HomeScreen:Screen("home_screen")
    object RegisterScreen:Screen("register_screen")
    object SearchScreen:Screen("search_screen")
    object UpdateScreen:Screen("update_screen")
    object StatsScreen:Screen("stats_screen")
    object BookDetailsScreen:Screen("book_details_screen")
}
