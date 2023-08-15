package com.example.music_app.ui.screens.new_playlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.music_app.data.repositories.playlists.PlaylistsRepositoryImpl
import com.example.music_app.domain.use_cases.CreatePlaylistUseCase

class NewPlaylistViewModel(
    private val createPlaylistUseCase: CreatePlaylistUseCase
) : ViewModel() {
    fun createPlaylist(name: String, description: String? = null) {
        val a = 5
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                NewPlaylistViewModel(
                    createPlaylistUseCase = CreatePlaylistUseCase(PlaylistsRepositoryImpl())
                )
            }
        }
    }
}
