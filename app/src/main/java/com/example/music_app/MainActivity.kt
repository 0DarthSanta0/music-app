package com.example.music_app


import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val webView = findViewById<View>(R.id.webview) as WebView
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("https://accounts.spotify.com/authorize?client_id=${SPOTIFY_CLIENT_ID}&response_type=${CODE}&scope=${SCOPE}&redirect_uri=${REDIRECT_URL}")
        webView.webViewClient = AppWebViewClient()
    }

    private inner class AppWebViewClient: WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            val uri = Uri.parse(url)
            val state = uri.getQueryParameter("state")
            val error = uri.getQueryParameter("error")
            val code = uri.getQueryParameter("code")
            if (error == null && code != null) {

            }
        }
    }

}

