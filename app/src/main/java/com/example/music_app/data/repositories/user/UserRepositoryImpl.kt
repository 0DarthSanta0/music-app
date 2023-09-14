package com.example.music_app.data.repositories.user

import com.example.music_app.AppErrors
import com.example.music_app.data.data_store.DataStoreManager
import com.example.music_app.data.repositories.toAppErrors
import com.example.music_app.domain.repositories.UserRepository
import com.example.music_app.network.PlaylistsService
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result

private const val USER_ID_KEY = "user_id"

class UserRepositoryImpl(
    private val dataStoreManager: DataStoreManager,
    private var spotifyAPI: PlaylistsService = PlaylistsService.getInstance()
) : UserRepository {
    override suspend fun requestUserID(): Result<String, AppErrors> {
        val (userIdResponse, error) = with(spotifyAPI.getCurrentUser()) {
            id to error
        }
        return when {
            userIdResponse != null -> {
                dataStoreManager.saveString(key = USER_ID_KEY, value = userIdResponse)
                Ok(userIdResponse)
            }
            error != null -> Err(error.toAppErrors())
            else -> Err(AppErrors.ResponseError)
        }
    }
}
