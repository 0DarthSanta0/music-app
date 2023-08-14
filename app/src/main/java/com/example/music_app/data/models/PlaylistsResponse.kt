package com.example.music_app.data.models

data class PlaylistsResponse(
    val total: Int?,
    val items: List<PlaylistItemResponse>?
)

data class PlaylistItemResponse(
    val description: String,
    val images: List<PlaylistImageResponse>,
    val name: String,
)


data class PlaylistImageResponse(
    val url: String,
    val height: Int,
    val width: Int
)


