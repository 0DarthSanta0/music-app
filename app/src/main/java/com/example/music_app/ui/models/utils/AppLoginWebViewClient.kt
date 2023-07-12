package com.example.music_app.ui.models.utils

import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.music_app.constants.CODE
import com.example.music_app.constants.REDIRECT_URL
import com.example.music_app.constants.SPOTIFY_CLIENT_ID
import com.example.music_app.constants.SPOTIFY_CLIENT_SECRET
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.Base64

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
        val state = uri.getQueryParameter(STATE_QUERY_PARAM)
        val error = uri.getQueryParameter(ERROR_QUERY_PARAM)
        val code = uri.getQueryParameter(CODE_QUERY_PARAM)
        if (code != null && error == null) {
            val url = URL("https://accounts.spotify.com/api/token")
            val postData =
                "grant_type=authorization_code1&code=${CODE}&redirect_uri=${REDIRECT_URL}"

            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "POST"
            conn.doOutput = true
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
            conn.setRequestProperty("Authorization",
                "Basic " + Base64.getEncoder()
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