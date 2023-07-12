package com.example.music_app.ui.screens.login


import androidx.compose.runtime.Composable
import com.example.music_app.constants.AUTH_URL
import com.example.music_app.ui.models.utils.AppLoginWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState


@Composable
fun LoginScreen() {
    val state = rememberWebViewState(AUTH_URL)
    WebView(
        state,
        onCreated = { webView ->
            with(webView) {
                settings.javaScriptEnabled = true
                webViewClient = AppLoginWebViewClient()
            }
        }
    )
}