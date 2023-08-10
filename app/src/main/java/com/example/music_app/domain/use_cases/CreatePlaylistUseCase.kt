package com.example.music_app.domain.use_cases

import com.example.music_app.domain.repositories.PlaylistsRepository

class CreatePlaylistUseCase(
    private val playlistsRepository: PlaylistsRepository
) {
    suspend operator fun invoke(name: String, description: String) =
        playlistsRepository.createPlaylist(
            name = name,
            description = description
        )
}