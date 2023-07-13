package com.example.music_app.ui.models.utils

import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.music_app.constants.BASE_URL
import com.example.music_app.constants.CODE
import com.example.music_app.constants.CODE_QUERY_PARAM
import com.example.music_app.constants.ERROR_QUERY_PARAM
import com.example.music_app.constants.GRANT_QUERY_PARAM
import com.example.music_app.constants.GRANT_TYPE
import com.example.music_app.constants.POST_URL
import com.example.music_app.constants.REDIRECT_QUERY_PARAM
import com.example.music_app.constants.REDIRECT_URL
import com.example.music_app.constants.SPOTIFY_CLIENT_ID
import com.example.music_app.constants.SPOTIFY_CLIENT_SECRET
import com.example.music_app.constants.STATE_QUERY_PARAM
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.Base64

private const val POST_METHOD = "POST"
private const val AUTH_HEADER_NAME = "Authorization"
private const val TYPE_HEADER_NAME = "Content-Type"
private const val TYPE_HEADER = "application/x-www-form-urlencoded"
private const val AUTH_PROPERTY = "Basic"


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
            if (code != null && error == null) {
                val url = URL(POST_URL)
                val postData =
                    "$GRANT_QUERY_PARAM=${GRANT_TYPE}&$CODE_QUERY_PARAM=${CODE}&$REDIRECT_QUERY_PARAM=${REDIRECT_URL}"

                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = POST_METHOD
                conn.doOutput = true
                conn.setRequestProperty(TYPE_HEADER_NAME, TYPE_HEADER)
                conn.setRequestProperty(
                    AUTH_HEADER_NAME,
                    "$AUTH_PROPERTY " + Base64.getEncoder()
                        .encodeToString("$SPOTIFY_CLIENT_ID:$SPOTIFY_CLIENT_SECRET".toByteArray())
                )
                conn.useCaches = false

                DataOutputStream(conn.outputStream).use { it.writeBytes(postData) }
                BufferedReader(InputStreamReader(conn.inputStream)).use { br ->
                    var line: String?
                    while (br.readLine().also { line = it } != null) {
                        println(line)
                    }
                }
            }
        }
    }
}