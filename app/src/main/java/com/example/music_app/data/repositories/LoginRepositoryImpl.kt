package com.example.music_app.data.repositories

import com.example.music_app.constants.REDIRECT_URL
import com.example.music_app.constants.SPOTIFY_CLIENT_ID
import com.example.music_app.constants.SPOTIFY_CLIENT_SECRET
import com.example.music_app.data.data_store.DataStoreManager
import com.example.music_app.AppErrors
import com.example.music_app.data.models.TokenRefreshResponse
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
import java.time.Instant
import java.util.Base64

private const val AUTH_HEADER_NAME = "Authorization"
private const val AUTH_PROPERTY = "Basic"
private const val TYPE_HEADER_NAME = "Content-Type"
private const val TYPE_HEADER = "application/x-www-form-urlencoded"

private const val TOKEN_KEY = "access_token"
private const val TIME_KEY = "token_receiving_time"
private const val REFRESH_TOKEN_KEY = "refresh_token"

private const val AUTH_GRANT_TYPE = "authorization_code"
private const val REFRESH_GRANT_TYPE = "refresh_token"

private const val TOKEN_LIFETIME = 3600

class LoginRepositoryImpl(
    private val dataStoreManager: DataStoreManager,
    private val spotifyAPI: AuthService = AuthService.getInstance()
) : LoginRepository {

    override suspend fun requestToken(code: String) = flow {
        val token = spotifyAPI.getToken(
            headers = createHeaders(),
            grantType = AUTH_GRANT_TYPE,
            code = code,
            redirectUri = REDIRECT_URL
        )
        emit(
            if (token.accessToken != null) {
                saveToken(token)
                Ok(token)
            } else {
                Err(AppErrors.ResponseError)
            }
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun requestRefreshToken(): Flow<Result<TokenRefreshResponse, AppErrors>> =
        flow {
            val refreshToken = dataStoreManager.getString(REFRESH_TOKEN_KEY)
            val token =
                spotifyAPI.getRefreshToken(
                    headers = createHeaders(),
                    grantType = REFRESH_GRANT_TYPE,
                    refreshToken = refreshToken
                )
            emit(
                if (token.accessToken != null) {
                    saveRefreshToken(token)
                    Ok(token)
                } else {
                    Err(AppErrors.ResponseError)
                }
            )
        }.flowOn(Dispatchers.IO)

    override suspend fun isAuthorized(): Result<Boolean, AppErrors> =
        catchErrors {
            dataStoreManager.getString(TOKEN_KEY).isNotEmpty()
        }


    override suspend fun isOutdated(): Result<Boolean, AppErrors> =
        catchErrors {
            val recTimeString = dataStoreManager.getString(TIME_KEY)
            if (recTimeString.isEmpty()) return Err(AppErrors.RecTime)
            val recTime = Instant.parse(dataStoreManager.getString(TIME_KEY))
            val currentTime = Instant.now()
            if (currentTime.epochSecond < recTime.epochSecond) return Err(AppErrors.WrongTimeInterval)
            return Ok(currentTime.minusSeconds(recTime.epochSecond).epochSecond >= TOKEN_LIFETIME)
        }


    private suspend fun saveToken(token: TokenResponse) {
        if (token.accessToken != null) {
            saveBaseToken(
                accessToken = token.accessToken,
                time = Instant.now().toString()
            )
        }
        if (token.refreshToken != null) {
            dataStoreManager.saveString(
                key = REFRESH_TOKEN_KEY,
                value = token.refreshToken
            )
        }
    }

    private suspend fun saveRefreshToken(token: TokenRefreshResponse) {
        if (token.accessToken != null) {
            saveBaseToken(
                accessToken = token.accessToken,
                time = Instant.now().toString()
            )
        }
    }

    private suspend fun saveBaseToken(accessToken: String, time: String) = with(dataStoreManager) {
        saveString(key = TOKEN_KEY, value = accessToken)
        saveString(key = TIME_KEY, value = time)
    }

    private fun createHeaders(): Map<String, String> {
        val auth = "$AUTH_PROPERTY " + Base64.getEncoder()
            .encodeToString("$SPOTIFY_CLIENT_ID:$SPOTIFY_CLIENT_SECRET".toByteArray())
        return mapOf(AUTH_HEADER_NAME to auth, TYPE_HEADER_NAME to TYPE_HEADER)
    }
}