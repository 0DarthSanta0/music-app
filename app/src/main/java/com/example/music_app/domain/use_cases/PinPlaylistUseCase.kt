package com.example.music_app.domain.use_cases

import com.example.music_app.data.models.Playlist
import com.example.music_app.domain.repositories.PlaylistsRepository

class PinPlaylistUseCase(
    private val playlistsRepository: PlaylistsRepository
) {
    operator fun invoke(playlist: Playlist) = playlistsRepository.pinPlaylist(playlist)
}
