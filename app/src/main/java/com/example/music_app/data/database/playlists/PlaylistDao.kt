package com.example.music_app.data.database.playlists

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import java.time.Instant

@Dao
interface PlaylistDao {
    @Upsert
    fun upsertPlaylist(playlistEntity: PlaylistEntity)
    @Upsert
    fun upsertListOfPlaylists(playlists: List<PlaylistEntity>)
    @Query("SELECT * FROM PlaylistEntity LIMIT :limit")
    fun getAllPlaylistsSortedByPinDate(limit: Int = 10): List<PlaylistEntity>
}