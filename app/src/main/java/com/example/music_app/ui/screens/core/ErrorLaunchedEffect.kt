package com.example.music_app.ui.screens.core

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.music_app.AppErrors

@Composable
fun ErrorLaunchedEffect(
    error: AppErrors?,
    snackbarHostState: SnackbarHostState
) {
    LaunchedEffect(error) {
        if (error != null) {
            snackbarHostState.showSnackbar(
                message = "",
                duration = SnackbarDuration.Indefinite
            )
        }
    }
}
