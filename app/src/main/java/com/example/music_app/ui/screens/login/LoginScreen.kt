package com.example.music_app.ui.screens.login


import android.view.ViewGroup
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.example.music_app.AUTH_URL
import com.example.music_app.ui.models.classes.AppLoginWebViewClient


@Composable
fun LoginScreen() {

    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            settings.javaScriptEnabled = true
            webViewClient = AppLoginWebViewClient()
            loadUrl(AUTH_URL)
        }
    })
}