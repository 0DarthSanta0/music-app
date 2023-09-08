package com.example.music_app.domain.use_cases

import com.example.music_app.AppErrors
import com.example.music_app.domain.repositories.PlaylistsRepository
import com.github.michaelbull.result.Result
import kotlinx.coroutines.flow.Flow

class CreatePlaylistUseCase(
    private val playlistsRepository: PlaylistsRepository
) {
    suspend operator fun invoke(
        name: String,
        description: String?
    ): Flow<Result<Unit, AppErrors>> =
        playlistsRepository.createPlaylist(
            playlistName = name,
            description = description
        )
}
