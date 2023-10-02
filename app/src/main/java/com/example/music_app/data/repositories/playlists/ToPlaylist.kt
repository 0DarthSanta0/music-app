package com.example.music_app.data.repositories.playlists

import com.example.music_app.data.database.playlists.models.PlaylistEntity
import com.example.music_app.data.models.Playlist

fun PlaylistEntity.toPlaylist() = Playlist(
    name = name,
    description = description,
    imageUrl = imageUrl,
    id = id
)
