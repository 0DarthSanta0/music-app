package com.example.music_app.data.repositories.playlists

import androidx.room.Room
import com.example.music_app.AppErrors
import com.example.music_app.MusicAppApplication
import com.example.music_app.data.data_store.DataStoreManager
import com.example.music_app.data.database.playlists.PlaylistDatabase
import com.example.music_app.data.database.playlists.PlaylistEntity
import com.example.music_app.data.models.CreatePlaylistBody
import com.example.music_app.data.models.ListOfPlaylists
import com.example.music_app.data.models.PlaylistItemResponse
import com.example.music_app.data.repositories.catchAPIErrors
import com.example.music_app.data.repositories.catchDataBaseErrors
import com.example.music_app.domain.repositories.PlaylistsRepository
import com.example.music_app.network.PlaylistsService
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

private const val USER_ID_KEY = "user_id"
private const val DATABASE_NAME = "playlist-database"

class PlaylistsRepositoryImpl(
    private val dataStoreManager: DataStoreManager,
    private var spotifyAPI: PlaylistsService = PlaylistsService.getInstance(),
    private val database: PlaylistDatabase = Room.databaseBuilder(
        MusicAppApplication.applicationContext(),
        PlaylistDatabase::class.java, DATABASE_NAME
    ).build()
) : PlaylistsRepository {


    override suspend fun requestListOfPlaylists(
        offset: Int,
        limit: Int
    ): Flow<Result<ListOfPlaylists, AppErrors>> = flow {
        val playlistsResponse = spotifyAPI.getListOfPlaylists(
            offset = offset.toString(),
            limit = limit.toString()
        )
        with(playlistsResponse) {
            val totalSize = total
            val (playlistsEntities, playlists) =
                items?.map(PlaylistItemResponse::toPlaylistEntity) to items?.map(PlaylistItemResponse::toPlaylist)
            emit(
                catchAPIErrors(
                    isSuccess = playlists != null && totalSize != null,
                    error = error
                ) {
                    database.dao.upsertListOfPlaylists(playlistsEntities ?: listOf())
                    Ok(ListOfPlaylists(playlists = database.dao.getAllPlaylistsSortedByPinDate(limit = limit + offset).map(PlaylistEntity::toPlaylist), totalSize = totalSize ?: 0))
                }
            )
        }
    }

    override suspend fun createPlaylist(
        playlistName: String,
        description: String?
    ): Flow<Result<Unit, AppErrors>> = flow {
        catchDataBaseErrors {
            val userIdResponse = dataStoreManager.getString(USER_ID_KEY)
            if (userIdResponse.isEmpty()) emit(Err(AppErrors.EmptyUserID))
            val postResponse = spotifyAPI.createPlaylist(
                user = userIdResponse,
                body = CreatePlaylistBody(
                    name = playlistName,
                    description = description
                )
            )
            emit(
                with(postResponse) {
                    catchAPIErrors(
                        isSuccess = name != null,
                        error = error
                    ) {
                        Ok(Unit)
                    }
                }
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
        with(playlistsForSearchResponse) {
            emit(
                catchAPIErrors(
                    isSuccess = playlists != null,
                    error = error
                ) {
                    val (totalSize, playlists) =
                        playlists?.total to playlists?.items?.map(PlaylistItemResponse::toPlaylist)
                    if (playlists != null && totalSize != null) {
                        Ok(ListOfPlaylists(playlists = playlists, totalSize = totalSize))
                    } else {
                        Err(AppErrors.ResponseError)
                    }
                }
            )
        }
    }
}
