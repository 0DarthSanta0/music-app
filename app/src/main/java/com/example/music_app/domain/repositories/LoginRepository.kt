package com.example.music_app.domain.repositories

import com.example.music_app.data.models.ResponseError
import com.example.music_app.data.models.TokenRefreshResponse
import com.example.music_app.data.models.TokenResponse
import com.github.michaelbull.result.Result
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun requestToken(code: String): Flow<Result<TokenResponse, ResponseError>>
    suspend fun requestRefreshToken(): Flow<Result<TokenRefreshResponse, ResponseError>>
    suspend fun isAuthorized(): Boolean
    suspend fun isOutdated(): Boolean
}