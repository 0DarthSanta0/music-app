package com.example.music_app.data.database.playlists.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity
data class PlaylistEntity(
    val name: String,
    val description: String,
    val imageUrl: String?,
    val isPinned: Boolean = false,
    val pinDate: Instant? = null,
    @PrimaryKey val id: String
)
