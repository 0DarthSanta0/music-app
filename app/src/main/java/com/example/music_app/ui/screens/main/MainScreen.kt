package com.example.music_app.ui.screens.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.music_app.ui.navigation.Screens

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel(factory = MainViewModel.Factory)
) {
    val screen by viewModel.route.collectAsState()
    LaunchedEffect(screen) {
        if (screen != null) {
            val castedScreen = screen as Screens
            navController.navigate(castedScreen.route)
        }
    }
}