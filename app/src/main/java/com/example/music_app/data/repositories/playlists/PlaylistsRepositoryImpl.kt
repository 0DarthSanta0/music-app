package com.example.music_app.data.repositories.playlists

import com.example.music_app.AppErrors
import com.example.music_app.data.models.ListOfPlaylists
import com.example.music_app.data.models.PlaylistItemResponse
import com.example.music_app.domain.repositories.PlaylistsRepository
import com.example.music_app.network.PlaylistsService
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlaylistsRepositoryImpl(
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

    override fun createPlaylist(name: String, description: String) {

    }
}
