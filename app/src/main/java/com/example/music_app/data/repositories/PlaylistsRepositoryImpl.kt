package com.example.music_app.data.repositories

import com.example.music_app.AppErrors
import com.example.music_app.data.data_store.DataStoreManager
import com.example.music_app.data.models.Playlist
import com.example.music_app.domain.repositories.PlaylistsRepository
import com.example.music_app.network.PlaylistsService
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

private const val TOKEN_KEY = "access_token"

private const val BEARER = "Bearer"

class PlaylistsRepositoryImpl(
    private val dataStoreManager: DataStoreManager,
    private val spotifyAPI: PlaylistsService = PlaylistsService.getInstance()
) : PlaylistsRepository {

    override suspend fun requestListOfPlaylists(
        offset: Int,
        limit: Int
    ): Flow<Result<List<Playlist>, AppErrors>> = flow {
        val token = dataStoreManager.getString(TOKEN_KEY)
        val header = "$BEARER $token"
        val playlistsResponse = spotifyAPI.getListOfPlaylists(
            authorization = header,
            offset = offset.toString(),
            limit = limit.toString()
        )
        emit(
            if (playlistsResponse.items != null) {
                val playlists = playlistsResponse.items.map { playlistItem ->
                    Playlist(
                        name = playlistItem.name,
                        description = playlistItem.description,
                        images = playlistItem.images
                    )
                }
                Ok(playlists)
            } else {
                Err(AppErrors.ResponseError)
            }
        )
    }.flowOn(Dispatchers.IO)
}