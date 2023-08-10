package com.example.music_app.domain.repositories

interface PlaylistsRepository {
    fun createPlaylist(name: String, description: String) {}
}