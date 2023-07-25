package com.example.music_app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.music_app.constants.LOGIN_SCREEN
import com.example.music_app.ui.login.LoginScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = LOGIN_SCREEN ) {
        composable(route = LOGIN_SCREEN) {
            LoginScreen(navController = navController)
        }
    }
}