package com.example.music_app.ui.screens.login


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.example.music_app.constants.AUTH_URL
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.music_app.ui.navigation.Screens


@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = viewModel(factory = LoginViewModel.Factory)
) {
    val state = rememberWebViewState(AUTH_URL)
    WebView(
        state,
        onCreated = { webView ->
            with(webView) {
                settings.javaScriptEnabled = true
            }
        },
        client = remember {
            AppLoginWebViewClient { code: String?, error: String? ->
                viewModel.requestToken(
                    code = code,
                    error = error
                )
            }
        }

    )

    val screen by viewModel.route.collectAsState()
    LaunchedEffect(screen) {
        if (screen != null) {
            val castedScreen = screen as Screens
            navController.navigate(castedScreen.route)
        }
    }

}
