package com.example.music_app.ui.models.classes

import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient

class AppLoginWebViewClient : WebViewClient() {
    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        val uri = Uri.parse(url)
        val state = uri.getQueryParameter("state")
        val error = uri.getQueryParameter("error")
        val code = uri.getQueryParameter("code")
    }
}