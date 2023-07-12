package com.example.music_app.ui.models.utils

import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient

private const val STATE_QUERY_PARAM = "state"
private const val ERROR_QUERY_PARAM = "error"
private const val CODE_QUERY_PARAM = "code"

class AppLoginWebViewClient : WebViewClient() {
    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        parseResponse(url)
    }

    private fun parseResponse(url: String?) {
        val uri = Uri.parse(url)
        with(uri) {
            val state = getQueryParameter(STATE_QUERY_PARAM)
            val error = getQueryParameter(ERROR_QUERY_PARAM)
            val code = getQueryParameter(CODE_QUERY_PARAM)
        }
    }
}