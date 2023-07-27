package com.example.music_app.data.repositories

import com.example.music_app.constants.GRANT_TYPE
import com.example.music_app.constants.REDIRECT_URL
import com.example.music_app.constants.SPOTIFY_CLIENT_ID
import com.example.music_app.constants.SPOTIFY_CLIENT_SECRET
import com.example.music_app.data.data_store.DataStoreManager
import com.example.music_app.data.models.ResponseError
import com.example.music_app.data.models.TokenResponse
import com.example.music_app.domain.repositories.LoginRepository
import com.example.music_app.network.AuthService
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import java.time.Instant
import java.util.Base64

private const val AUTH_HEADER_NAME = "Authorization"
private const val AUTH_PROPERTY = "Basic"
private const val TYPE_HEADER_NAME = "Content-Type"
private const val TYPE_HEADER = "application/x-www-form-urlencoded"

private const val TOKEN_KEY = "access_token"
private const val TIME_KEY = "token_receiving_time"
private const val REFRESH_TOKEN_KEY = "refresh_token"


class LoginRepositoryImpl(
    private val dataStoreManager: DataStoreManager
): LoginRepository {
    private val spotifyAPI = AuthService.getInstance()

    override suspend fun requestToken(code: String) = flow {
        val token = spotifyAPI.getToken(createHeaders(), GRANT_TYPE, code, REDIRECT_URL)
        emit(if (token.accessToken != null) {
            saveToken(token)
            Ok(token)
        } else Err(ResponseError()))
    }.flowOn(Dispatchers.IO)

    override suspend fun saveToken(token: TokenResponse) {
        with(dataStoreManager) {
            saveString(token.accessToken.toString(), TOKEN_KEY)
            saveString(Instant.now().toString(), TIME_KEY)
            saveString(token.refreshToken.toString(), REFRESH_TOKEN_KEY)
        }
    }

    override suspend fun requestRefreshToken(): Flow<Result<TokenResponse, ResponseError>> =
        dataStoreManager.getString(REFRESH_TOKEN_KEY).transform { refreshToken ->
            val token = spotifyAPI.getRefreshToken(createHeaders(), "refresh_token", refreshToken)
            emit(
                if (token.accessToken != null) {
                    saveToken(token)
                    Ok(token)
                } else Err(ResponseError())
            )
        }

    override fun isAuthorized(): Flow<Boolean> = dataStoreManager.getString(TOKEN_KEY).map(String::isNotEmpty)

    override fun isOutdated(): Flow<Boolean> =
        dataStoreManager.getString(TIME_KEY).transform { receivingTime ->
            val recTime = Instant.parse(receivingTime)
            val curTime = Instant.now()
            if (curTime.minusSeconds(recTime.epochSecond).epochSecond >= 3600) emit(true) else emit(false)
        }

    private fun createHeaders(): Map<String, String> {
        val auth = "$AUTH_PROPERTY " + Base64.getEncoder()
            .encodeToString("$SPOTIFY_CLIENT_ID:$SPOTIFY_CLIENT_SECRET".toByteArray())
        return mapOf(AUTH_HEADER_NAME to auth, TYPE_HEADER_NAME to TYPE_HEADER)
    }

}