package com.example.music_app.ui.screens.login

import android.net.Uri
import android.webkit.WebView
import com.example.music_app.AppErrors
import com.google.accompanist.web.AccompanistWebViewClient


const val ERROR_QUERY_PARAM = "error"
const val CODE_QUERY_PARAM = "code"


class AppLoginWebViewClient(
    private val onRequest: (String?, AppErrors?) -> Unit
) : AccompanistWebViewClient() {
    override fun onPageFinished(view: WebView, url: String?) {
        super.onPageFinished(view, url)
        parseResponse(url)
    }

    private fun parseResponse(url: String?) {
        val uri = Uri.parse(url)
        with(uri) {
            val error = if (getQueryParameter(ERROR_QUERY_PARAM) != null) AppErrors.ResponseError else null
            val code = getQueryParameter(CODE_QUERY_PARAM)
            if (code != null || error != null) {
                onRequest(code, error)
            }
        }
    }
}
