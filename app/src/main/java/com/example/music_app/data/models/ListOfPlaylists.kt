package com.example.music_app.data.models

data class ListOfPlaylists(
    val playlists: List<Playlist>,
    val totalSize: Int?
)