package com.example.music_app.domain.repositories

import com.example.music_app.AppErrors
import com.example.music_app.data.models.ListOfPlaylists
import com.github.michaelbull.result.Result
import kotlinx.coroutines.flow.Flow

interface PlaylistsRepository {
    suspend fun createPlaylist(
        name: String,
        description: String?
    ): Flow<Result<Unit, AppErrors>>
    suspend fun requestListOfPlaylists(
        offset: Int,
        limit: Int
    ): Flow<Result<ListOfPlaylists, AppErrors>>
}
