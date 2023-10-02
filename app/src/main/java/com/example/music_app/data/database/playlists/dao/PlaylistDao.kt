package com.example.music_app.data.database.playlists.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.music_app.data.database.playlists.models.PlaylistEntity
import java.time.Instant

@Dao
interface PlaylistDao {
    @Upsert
    fun upsertPlaylist(playlistEntity: PlaylistEntity)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun upsertListOfPlaylists(playlists: List<PlaylistEntity>)
    @Query("SELECT * FROM PlaylistEntity ORDER BY CASE WHEN pinDate IS NULL THEN :minDate ELSE pinDate END DESC LIMIT :limit")
    fun getAllPlaylistsSortedByPinDate(limit: Int = 10, minDate: Instant = Instant.MIN): List<PlaylistEntity>
}
