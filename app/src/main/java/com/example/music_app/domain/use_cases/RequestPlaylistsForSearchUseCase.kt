package com.example.music_app.domain.use_cases

import com.example.music_app.domain.repositories.PlaylistsRepository

class RequestPlaylistsForSearchUseCase(
    private val playlistsRepository: PlaylistsRepository
) {
    suspend operator fun invoke(offset: Int, limit: Int, prefix: String) = playlistsRepository.requestPlaylistsForSearch(
        offset = offset,
        limit = limit,
        prefix = prefix
    )
}