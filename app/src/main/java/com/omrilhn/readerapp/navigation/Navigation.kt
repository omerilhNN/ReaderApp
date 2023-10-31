package com.omrilhn.readerapp.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import coil.ImageLoader
import com.omrilhn.readerapp.presentation.home.HomeScreen
import com.omrilhn.readerapp.presentation.login.LoginScreen
import com.omrilhn.readerapp.presentation.search.SearchScreen
import com.omrilhn.readerapp.presentation.splash.SplashScreen
import com.omrilhn.readerapp.presentation.update.UpdateScreen


@Composable
fun Navigation(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState
//    imageLoader: ImageLoader
){
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route,
        modifier = Modifier.fillMaxSize())
    {
        composable(Screen.SplashScreen.route){
            SplashScreen(navController = navController)
        }
        composable(Screen.UpdateScreen.route){
            UpdateScreen()
        }
        composable(Screen.HomeScreen.route){
            HomeScreen(navController = navController)
        }
        composable(Screen.LoginScreen.route){
            LoginScreen()
        }
        composable(Screen.SearchScreen.route){
            SearchScreen()
        }

    }
}