package com.example.music_app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.music_app.ui.screens.login.LoginScreen
import com.example.music_app.ui.screens.new_playlist.NewPlaylistScreen
import com.example.music_app.ui.screens.playlists.PlaylistsScreen
import com.example.music_app.ui.screens.search.SearchScreen


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
            }, onLoginError = {
                navController.navigate(Screens.LoginScreen.route)
            })
        }
        composable(route = Screens.PlaylistsScreen.route) {
            PlaylistsScreen(onAddNewPlaylist = {
                navController.navigate(Screens.NewPlaylistScreen.route)
            }, onSearch = {
                navController.navigate(Screens.SearchScreen.route)
            }, onError = {
                navController.navigate(Screens.LoginScreen.route)
            })
        }
        composable(route = Screens.SearchScreen.route) {
            SearchScreen(onBackToPlaylists = {
                navController.popBackStack()
            }, onError = {
                navController.navigate(Screens.LoginScreen.route)
            })
        }
        composable(route = Screens.NewPlaylistScreen.route) {
            NewPlaylistScreen(onCreatePlaylistSuccess = {
                navController.navigate(Screens.PlaylistsScreen.route)
            }, onError = {
                navController.navigate(Screens.LoginScreen.route)
            })
        }
    }
}
