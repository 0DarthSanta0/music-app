package com.example.music_app.data.repositories.playlists

import com.example.music_app.data.models.Playlist
import com.example.music_app.data.models.PlaylistItemResponse

fun PlaylistItemResponse.toPlaylist() = Playlist(
    name = name,
    description = description,
    imageUrl = images.firstOrNull()?.url,
    id = id
)
