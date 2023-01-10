package com.example.composenavigation.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.composenavigation.screens.SplashScreen
import com.example.composenavigation.screens.auth.forgotpassword.ForgotPasswordScreen
import com.example.composenavigation.screens.auth.login.LoginScreen
import com.example.composenavigation.screens.auth.signup.SignUpScreen

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.Splash.route
    ) {
        composable(route = AuthScreen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(route = AuthScreen.Forgot.route) {
            ForgotPasswordScreen()
        }
        composable(route = AuthScreen.Login.route) {
            LoginScreen(navController = navController)
            /*LoginContent(
                onClick = {
                    navController.popBackStack()
                    navController.navigate(Graph.HOME)
                },
                onSignUpClick = {
                    navController.navigate(AuthScreen.SignUp.route)
                },
                onForgotClick = {
                    navController.navigate(AuthScreen.Forgot.route)
                }
            )*/
        }
        composable(route = AuthScreen.SignUp.route) {
            SignUpScreen(navController = navController)
        }
    }
}

sealed class AuthScreen(val route: String) {
    object Login : AuthScreen(route = "LOGIN")
    object SignUp : AuthScreen(route = "SIGN_UP")
    object Forgot : AuthScreen(route = "FORGOT")
    object Splash : AuthScreen(route = "SPLASH")
}