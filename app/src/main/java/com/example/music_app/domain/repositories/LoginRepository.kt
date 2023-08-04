package com.example.music_app.domain.repositories

import com.example.music_app.AppErrors
import com.example.music_app.data.models.TokenRefreshResponse
import com.example.music_app.data.models.TokenResponse
import com.github.michaelbull.result.Result
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun requestToken(code: String): Flow<Result<TokenResponse, AppErrors>>
    suspend fun requestRefreshToken(): Flow<Result<TokenRefreshResponse, AppErrors>>
    suspend fun isAuthorized(): Result<Boolean, AppErrors>
    suspend fun isOutdated(): Result<Boolean, AppErrors>
}