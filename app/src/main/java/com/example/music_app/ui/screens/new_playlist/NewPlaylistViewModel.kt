package com.example.music_app.ui.screens.new_playlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.music_app.AppErrors
import com.example.music_app.data.repositories.playlists.PlaylistsRepositoryImpl
import com.example.music_app.domain.use_cases.CreatePlaylistUseCase
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.onSuccess
import kotlinx.coroutines.launch

class NewPlaylistViewModel(
    private val createPlaylistUseCase: CreatePlaylistUseCase
) : ViewModel() {
    fun createPlaylist(
        name: String,
        description: String? = null,
        onCreatePlaylistSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            createPlaylistUseCase(
                name = name,
                description = description
            ).collect { createPlaylist: Result<Boolean, AppErrors> ->
                createPlaylist.onSuccess {
                    onCreatePlaylistSuccess()
                }
            }
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
