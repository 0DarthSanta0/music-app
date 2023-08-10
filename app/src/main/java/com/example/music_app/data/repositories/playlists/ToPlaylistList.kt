package com.example.music_app.data.repositories.playlists

import com.example.music_app.data.models.Playlist
import com.example.music_app.data.models.PlaylistsResponse

fun PlaylistsResponse.toPlaylistList(): List<Playlist>? {
    return items?.map { playlistItem ->
        Playlist(
            name = playlistItem.name,
            description = playlistItem.description,
            images = playlistItem.images
        )
    }
}