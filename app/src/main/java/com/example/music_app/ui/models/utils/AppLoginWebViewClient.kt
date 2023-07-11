package com.example.music_app.ui.models.utils

import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.music_app.constants.CODE
import com.example.music_app.constants.ERROR
import com.example.music_app.constants.STATE

class AppLoginWebViewClient : WebViewClient() {
    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        parseResponse(url)
    }

    private fun parseResponse(url: String?) {
        val uri = Uri.parse(url)
        with(uri) {
            val state = getQueryParameter(STATE)
            val error = getQueryParameter(ERROR)
            val code = getQueryParameter(CODE)
        }
    }

}