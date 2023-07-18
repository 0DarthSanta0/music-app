package com.example.music_app.data

import com.example.music_app.constants.GRANT_TYPE
import com.example.music_app.constants.REDIRECT_URL
import com.example.music_app.constants.SPOTIFY_CLIENT_ID
import com.example.music_app.constants.SPOTIFY_CLIENT_SECRET
import com.example.music_app.network.AuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.Base64

private const val AUTH_HEADER_NAME = "Authorization"
private const val AUTH_PROPERTY = "Basic"
private const val TYPE_HEADER_NAME = "Content-Type"
private const val TYPE_HEADER = "application/x-www-form-urlencoded"

class LoginRepository {

    suspend fun requestToken(code: String) = flow {
        val auth = "$AUTH_PROPERTY " + Base64.getEncoder()
            .encodeToString("$SPOTIFY_CLIENT_ID:$SPOTIFY_CLIENT_SECRET".toByteArray())
        val spotifyAPI =
            AuthService.getInstance()
        val headers = mapOf(AUTH_HEADER_NAME to auth, TYPE_HEADER_NAME to TYPE_HEADER)
        emit(spotifyAPI.getToken(headers, GRANT_TYPE, code, REDIRECT_URL))
    }.flowOn(Dispatchers.IO)

}