package com.example.music_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.music_app.ui.navigation.Navigation
import com.example.music_app.ui.screens.core.components.ErrorMessage
import com.example.music_app.ui.screens.core.toUIStringRes
import com.example.music_app.ui.theme.MusicAppTheme


class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModels { MainActivityViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { viewModel.isSplashScreenVisible.value }
        setContent {
            val screen by viewModel.route.collectAsStateWithLifecycle()
            val error by viewModel.error.collectAsStateWithLifecycle()
            MusicAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (error == null) {
                        Navigation(screen)
                    } else {
                        ErrorMessage(
                            errorId = error?.toUIStringRes() ?: 0,
                            onClick = { viewModel.checkStatus() },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}
