package com.example.music_app.data.database.playlists

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.music_app.data.database.playlists.dao.PlaylistDao
import com.example.music_app.data.database.playlists.models.PlaylistEntity

@Database(
    entities = [PlaylistEntity::class],
    version = 1
)
abstract class PlaylistDatabase: RoomDatabase() {
    abstract val dao: PlaylistDao
}
