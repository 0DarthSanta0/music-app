package com.example.music_app.ui.models.utils

import android.net.Uri
import android.webkit.WebView
import com.example.music_app.constants.GRANT_TYPE
import com.example.music_app.constants.REDIRECT_URL
import com.example.music_app.constants.SPOTIFY_CLIENT_ID
import com.example.music_app.constants.SPOTIFY_CLIENT_SECRET
import com.example.music_app.ui.helpers.RetrofitAuthHelper
import com.google.accompanist.web.AccompanistWebViewClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.Base64


const val ERROR_QUERY_PARAM = "error"
const val CODE_QUERY_PARAM = "code"
private const val AUTH_HEADER_NAME = "Authorization"
private const val AUTH_PROPERTY = "Basic"
private const val TYPE_HEADER_NAME = "Content-Type"
private const val TYPE_HEADER = "application/x-www-form-urlencoded"


class AppLoginWebViewClient : AccompanistWebViewClient() {
    override fun onPageFinished(view: WebView, url: String?) {
        super.onPageFinished(view, url)
        parseResponse(url)
    }

    private fun parseResponse(url: String?) {
        val uri = Uri.parse(url)
        with(uri) {
            val error = getQueryParameter(ERROR_QUERY_PARAM)
            val code = getQueryParameter(CODE_QUERY_PARAM)
            if (code != null && error == null) {
                val auth = "$AUTH_PROPERTY " + Base64.getEncoder()
                    .encodeToString("$SPOTIFY_CLIENT_ID:$SPOTIFY_CLIENT_SECRET".toByteArray())
                val spotifyAPI =
                    RetrofitAuthHelper.getInstance().create(RetrofitAuthService::class.java)
                val headers = mapOf(AUTH_HEADER_NAME to auth, TYPE_HEADER_NAME to TYPE_HEADER)
                GlobalScope.launch {
                    try {
                        val result =
                            spotifyAPI.getToken(headers, GRANT_TYPE, code, REDIRECT_URL).execute()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}