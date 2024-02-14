package com.omrilhn.readerapp.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.omrilhn.readerapp.presentation.details.BookDetailsScreen
import com.omrilhn.readerapp.presentation.home.HomeScreen
import com.omrilhn.readerapp.presentation.home.HomeViewModel
import com.omrilhn.readerapp.presentation.login.LoginScreen
import com.omrilhn.readerapp.presentation.login.LoginViewModel
import com.omrilhn.readerapp.presentation.register.RegisterScreen
import com.omrilhn.readerapp.presentation.search.SearchScreen
import com.omrilhn.readerapp.presentation.search.SearchViewModel
import com.omrilhn.readerapp.presentation.splash.SplashScreen
import com.omrilhn.readerapp.presentation.stats.StatsScreen
import com.omrilhn.readerapp.presentation.update.UpdateScreen


@Composable
fun Navigation(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState
//    imageLoader: ImageLoader
){
    val loginViewModel = hiltViewModel<LoginViewModel>()
    val searchViewModel = hiltViewModel<SearchViewModel>()
    val homeViewModel = hiltViewModel<HomeViewModel>()
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route,
        modifier = Modifier.fillMaxSize())
    {
        composable(Screen.SplashScreen.route){
            SplashScreen(navController = navController)
        }

        composable(Screen.HomeScreen.route){
            HomeScreen(navController = navController,homeViewModel = homeViewModel)
        }
        composable(Screen.RegisterScreen.route){
            RegisterScreen(navController = navController){
                navController.popBackStack(
                    route = Screen.RegisterScreen.route,
                    inclusive = true
                )
                navController.navigate(Screen.HomeScreen.route)
            }
        }
        composable(Screen.LoginScreen.route){
            LoginScreen(
                onNavigate = navController::navigate,
                onLoginClick = {
//                    loginViewModel.onEvent(LoginEvent.Login)
                    navController.popBackStack(
                        route = Screen.LoginScreen.route,
                        inclusive = true
                    )
                    navController.navigate(route = Screen.HomeScreen.route)

                }
            )
        }
        composable(Screen.StatsScreen.route){
            StatsScreen(navController = navController,viewModel = homeViewModel )
        }
        composable(Screen.SearchScreen.route){
            SearchScreen(navController = navController,searchViewModel)
        }
        val updateName = Screen.UpdateScreen.route
        composable("$updateName/{bookItemId}",
            arguments = listOf(navArgument("bookItemId") {
                type = NavType.StringType
            })) { navBackStackEntry ->

            navBackStackEntry.arguments?.getString("bookItemId").let {
                UpdateScreen(navController = navController, bookItemId = it.toString())
            }

        }

        //once you declare a path like "/{bookId}" you need to specify it inside navArgument both must have the same name.
        //backStackEntry -> means ROUTE's CURRENT STATE!!
        val detailScreen = Screen.BookDetailsScreen.route
        composable("$detailScreen/{bookId}",arguments = listOf(navArgument("bookId"){
            type = NavType.StringType
        })){ backStackEntry->
            //let block will be executed if bookId isNotNull().
            backStackEntry.arguments?.getString("bookId").let {
                BookDetailsScreen(navController = navController, bookId = it.toString())

            }
        }

    }
}