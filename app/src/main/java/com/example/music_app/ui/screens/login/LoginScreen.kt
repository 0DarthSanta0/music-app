package com.example.music_app.ui.screens.login


import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.music_app.constants.AUTH_URL
import com.example.music_app.ui.utils.AppLoginWebViewClient
import com.example.music_app.ui.view_models.LoginViewModel
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import androidx.lifecycle.viewmodel.compose.viewModel



@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel()
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
                    code,
                    error
                )
            }
        }

    )
}
