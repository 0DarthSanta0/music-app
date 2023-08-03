package com.example.music_app.data.repositories

import com.example.music_app.data.data_store.DataStoreManager
import com.example.music_app.data.models.PlaylistsResponse
import com.example.music_app.data.models.ResponseError
import com.example.music_app.domain.repositories.PlaylistsRepository
import com.example.music_app.network.PlaylistsService
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

private const val TOKEN_KEY = "access_token"

class PlaylistsRepositoryImpl(
    private val dataStoreManager: DataStoreManager,
    private val spotifyAPI: PlaylistsService = PlaylistsService.getInstance()
) : PlaylistsRepository {

    override suspend fun requestListOfPlaylists(): Flow<Result<PlaylistsResponse, ResponseError>> {
        val token = dataStoreManager.getString(TOKEN_KEY)
        val header = "Bearer $token"
        return flow {  Err(ResponseError())}
    }

}