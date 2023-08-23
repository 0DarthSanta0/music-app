package com.example.music_app.domain.repositories

import com.example.music_app.AppErrors
import com.github.michaelbull.result.Result

interface UserRepository {
    suspend fun requestUserID(): Result<String, AppErrors>
}
