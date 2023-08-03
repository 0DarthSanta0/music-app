package com.example.music_app.domain.repositories

import com.example.music_app.data.models.PlaylistsResponse
import com.example.music_app.data.models.ResponseError
import com.github.michaelbull.result.Result
import kotlinx.coroutines.flow.Flow

interface PlaylistsRepository {

    suspend fun requestListOfPlaylists(): Flow<Result<PlaylistsResponse, ResponseError>>

}