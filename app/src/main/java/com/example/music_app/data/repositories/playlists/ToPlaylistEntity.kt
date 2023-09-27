package com.example.music_app.data.repositories.playlists

import com.example.music_app.data.database.playlists.PlaylistEntity
import com.example.music_app.data.models.PlaylistItemResponse

fun PlaylistItemResponse.toPlaylistEntity() = PlaylistEntity(
    name = name,
    description = description,
    imageUrl = images.firstOrNull()?.url,
    isPinned = false,
    id = id
)