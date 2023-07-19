package com.example.music_app.data.models

import com.example.music_app.models.TokenResponse
import com.example.music_app.network.ResponseError
import com.github.michaelbull.result.Result
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun requestToken(code: String): Flow<Result<TokenResponse, ResponseError>>
}