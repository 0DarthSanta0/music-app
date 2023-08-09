package com.example.music_app.data.repositories.playlists

import com.example.music_app.data.models.Playlist
import com.example.music_app.data.models.PlaylistItem

fun List<PlaylistItem>.toPlaylistList(): List<Playlist> {
    return map { playlistItem ->
        Playlist(
            name = playlistItem.name,
            description = playlistItem.description,
            images = playlistItem.images
        )
    }
}