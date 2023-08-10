package com.example.music_app.data.repositories.playlists

import com.example.music_app.AppErrors
import com.example.music_app.data.data_store.DataStoreManager
import com.example.music_app.data.models.ListOfPlaylists
import com.example.music_app.domain.repositories.PlaylistsRepository
import com.example.music_app.network.AuthInterceptor
import com.example.music_app.network.PlaylistsService
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.OkHttpClient

private const val TOKEN_KEY = "access_token"

class PlaylistsRepositoryImpl(
    private val dataStoreManager: DataStoreManager,
) : PlaylistsRepository {

    override suspend fun requestListOfPlaylists(
        offset: Int,
        limit: Int
    ): Flow<Result<ListOfPlaylists, AppErrors>> = flow {
        val token = dataStoreManager.getString(TOKEN_KEY)
        val authInterceptor = AuthInterceptor(token)
        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
        val spotifyAPI = PlaylistsService.getInstance(client)
        val playlistsResponse = spotifyAPI.getListOfPlaylists(
            offset = offset.toString(),
            limit = limit.toString()
        )
        val totalSize = playlistsResponse.total
        val playlists = playlistsResponse.toPlaylistList()
        emit(
            if (playlists != null) {
                Ok(ListOfPlaylists(playlists = playlists, totalSize = totalSize))
            } else {
                Err(AppErrors.ResponseError)
            }
        )
    }
}