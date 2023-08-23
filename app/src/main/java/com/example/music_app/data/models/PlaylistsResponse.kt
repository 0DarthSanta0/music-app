package com.example.music_app.data.models

data class PlaylistsResponse(
    val total: Int?,
    val items: List<PlaylistItemResponse>?
)
