package com.example.music_app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.music_app.ui.screens.login.LoginScreen
import com.example.music_app.ui.screens.main.MainScreen
import com.example.music_app.ui.screens.playlists.PlaylistsScreen


@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.MainScreen.route ) {
        composable(route = Screens.MainScreen.route) {
            MainScreen(navController = navController)
        }
        composable(route = Screens.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(route = Screens.PlaylistsScreen.route) {
            PlaylistsScreen(navController = navController)
        }
    }
}