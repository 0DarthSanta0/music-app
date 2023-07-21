package com.example.music_app.data.repositories

import com.example.music_app.constants.GRANT_TYPE
import com.example.music_app.constants.REDIRECT_URL
import com.example.music_app.constants.SPOTIFY_CLIENT_ID
import com.example.music_app.constants.SPOTIFY_CLIENT_SECRET
import com.example.music_app.data.LoginStoreManager
import com.example.music_app.data.models.ResponseError
import com.example.music_app.domain.repositories.LoginRepository
import com.example.music_app.network.AuthService
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.Base64

private const val AUTH_HEADER_NAME = "Authorization"
private const val AUTH_PROPERTY = "Basic"
private const val TYPE_HEADER_NAME = "Content-Type"
private const val TYPE_HEADER = "application/x-www-form-urlencoded"

class LoginRepositoryImpl(
    private val loginStoreManager: LoginStoreManager
): LoginRepository {
    private val spotifyAPI = AuthService.getInstance()

    override suspend fun requestToken(code: String) = flow {
        val token = spotifyAPI.getToken(createHeaders(), GRANT_TYPE, code, REDIRECT_URL)
        emit(if (token.accessToken != null) {
            saveToken(token.accessToken)
            Ok(token)
        } else Err(ResponseError()))
    }.flowOn(Dispatchers.IO)

    override suspend fun saveToken(token: String) {
        loginStoreManager.saveToken(token)
    }

    override fun isAuthorized(): Boolean {
        return loginStoreManager.getToken() != null
    }

    private fun createHeaders(): Map<String, String> {
        val auth = "$AUTH_PROPERTY " + Base64.getEncoder()
            .encodeToString("$SPOTIFY_CLIENT_ID:$SPOTIFY_CLIENT_SECRET".toByteArray())
        return mapOf(AUTH_HEADER_NAME to auth, TYPE_HEADER_NAME to TYPE_HEADER)
    }

}