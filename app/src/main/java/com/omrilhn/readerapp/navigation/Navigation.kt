package com.omrilhn.readerapp.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import coil.ImageLoader
import com.omrilhn.readerapp.presentation.home.HomeScreen
import com.omrilhn.readerapp.presentation.login.LoginScreen
import com.omrilhn.readerapp.presentation.login.LoginViewModel
import com.omrilhn.readerapp.presentation.search.SearchScreen
import com.omrilhn.readerapp.presentation.splash.SplashScreen
import com.omrilhn.readerapp.presentation.stats.StatsScreen
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
            LoginScreen(
                onNavigate = navController::navigate,
                onLoginClick = {
                    navController.popBackStack(
                        route = Screen.LoginScreen.route,
                        inclusive = true
                    )
                    navController.navigate(route = Screen.HomeScreen.route)
                },
            )
        }
        composable(Screen.StatsScreen.route){
            StatsScreen(navController = navController)
        }
        composable(Screen.SearchScreen.route){
            SearchScreen()
        }

    }
}