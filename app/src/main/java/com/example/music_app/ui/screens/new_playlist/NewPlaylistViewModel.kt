package com.example.music_app.ui.screens.new_playlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.music_app.data.repositories.playlists.PlaylistsRepositoryImpl
import com.example.music_app.domain.use_cases.CreatePlaylistUseCase
import kotlinx.coroutines.launch

class NewPlaylistViewModel(
    private val createPlaylistUseCase: CreatePlaylistUseCase
) : ViewModel() {
    fun createPlaylist(name: String, description: String? = null) {
        viewModelScope.launch {
            createPlaylistUseCase(
                name = name,
                description = description,
            )
        }
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
