package com.example.composenavigation.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.composenavigation.navigation.BottomBarScreen
import com.example.composenavigation.screens.home.BottomHomeScreen
import com.example.composenavigation.screens.profile.ProfileScreen
import com.example.composenavigation.screens.setting.SettingsScreen

@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            BottomHomeScreen()
        }
        composable(route = BottomBarScreen.Profile.route) {
          ProfileScreen()
        }
        composable(route = BottomBarScreen.Settings.route) {
         SettingsScreen(navController = navController)
        }
        authNavGraph(navController = navController)
    }
}
