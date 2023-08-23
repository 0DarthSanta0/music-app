package com.example.music_app.data.models

data class PlaylistItemResponse(
    val description: String,
    val images: List<PlaylistImageResponse>,
    val name: String,
    val id: String
)
