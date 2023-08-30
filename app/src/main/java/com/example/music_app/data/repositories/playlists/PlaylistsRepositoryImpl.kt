package com.example.music_app.data.repositories.playlists

import com.example.music_app.AppErrors
import com.example.music_app.data.data_store.DataStoreManager
import com.example.music_app.data.models.CreatePlaylistBody
import com.example.music_app.data.models.ListOfPlaylists
import com.example.music_app.data.models.PlaylistItemResponse
import com.example.music_app.data.repositories.catchErrors
import com.example.music_app.domain.repositories.PlaylistsRepository
import com.example.music_app.network.PlaylistsService
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

private const val USER_ID_KEY = "user_id"

class PlaylistsRepositoryImpl(
    private val dataStoreManager: DataStoreManager,
    private var spotifyAPI: PlaylistsService = PlaylistsService.getInstance()
) : PlaylistsRepository {
    override suspend fun requestListOfPlaylists(
        offset: Int,
        limit: Int
    ): Flow<Result<ListOfPlaylists, AppErrors>> = flow {
        val playlistsResponse = spotifyAPI.getListOfPlaylists(
            offset = offset.toString(),
            limit = limit.toString()
        )
        val totalSize = playlistsResponse.total
        val playlists = playlistsResponse.items?.map(PlaylistItemResponse::toPlaylist)
        emit(
            if (playlists != null && totalSize != null) {
                Ok(ListOfPlaylists(playlists = playlists, totalSize = totalSize))
            } else {
                Err(AppErrors.ResponseError)
            }
        )
    }

    override suspend fun createPlaylist(
        name: String,
        description: String?
    ): Flow<Result<Unit, AppErrors>> = flow {
        catchErrors {
            val userIdResponse = dataStoreManager.getString(USER_ID_KEY)
            if (userIdResponse.isEmpty()) emit(Err(AppErrors.EmptyUserID))
            val postResponse = spotifyAPI.createPlaylist(
                user = userIdResponse,
                body = CreatePlaylistBody(
                    name = name,
                    description = description
                )
            )
            emit(
                if (postResponse.name != null) Ok(Unit) else Err(AppErrors.ResponseError)
            )
        }
    }

    override suspend fun requestPlaylistsForSearch(
        offset: Int,
        limit: Int,
        prefix: String,
        type: String
    ): Flow<Result<ListOfPlaylists, AppErrors>> = flow {
        val playlistsForSearchResponse = spotifyAPI.getPlaylistsForSearch(
            prefix = prefix,
            type = type,
            offset = offset,
            limit = limit
        )
        val (totalSize, playlists) = with(playlistsForSearchResponse.playlists) {
            total to items?.map(PlaylistItemResponse::toPlaylist)
        }
        emit(
            if (playlists != null && totalSize != null) {
                Ok(ListOfPlaylists(playlists = playlists, totalSize = totalSize))
            } else {
                Err(AppErrors.ResponseError)
            }
        )
    }
}
