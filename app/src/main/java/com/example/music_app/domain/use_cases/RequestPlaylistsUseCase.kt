package com.example.music_app.domain.use_cases

import com.example.music_app.domain.repositories.PlaylistsRepository

class RequestPlaylistsUseCase(
    private val playlistsRepository: PlaylistsRepository
) {
    suspend operator fun invoke(offset: Int, limit: Int) =
        playlistsRepository.requestListOfPlaylists(offset = offset, limit = limit)
}