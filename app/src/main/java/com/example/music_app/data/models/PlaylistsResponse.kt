package com.example.music_app.data.models

data class PlaylistsResponse(
    val total: Int?,
    val items: List<PlaylistItem>?
)

data class PlaylistItem(
    val description: String,
    val images: List<Image>,
    val name: String,
)


data class Image(
    val url: String,
    val height: Int,
    val width: Int
)


