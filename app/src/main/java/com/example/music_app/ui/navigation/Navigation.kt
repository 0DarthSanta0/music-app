package com.example.music_app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.music_app.ui.screens.login.LoginScreen
import com.example.music_app.ui.screens.playlists.PlaylistsScreen


@Composable
fun Navigation(startScreen: Screens?) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startScreen?.route ?: Screens.LoginScreen.route
    ) {
        composable(route = Screens.LoginScreen.route) {
            LoginScreen(onLoginSuccess = {
                navController.navigate(Screens.PlaylistsScreen.route)
            })
        }
        composable(route = Screens.PlaylistsScreen.route) {
            PlaylistsScreen()
        }
    }
}