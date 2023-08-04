package com.example.music_app.ui.screens.playlists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.music_app.data.data_store.DataStoreManagerImpl
import com.example.music_app.data.models.Playlist
import com.example.music_app.data.repositories.PlaylistsRepositoryImpl
import com.example.music_app.domain.use_cases.RequestPlaylistsUseCase
import com.github.michaelbull.result.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlaylistsViewModel(
    private val requestPlaylistsUseCase: RequestPlaylistsUseCase
) : ViewModel() {

    private val _playlistsForDisplay: MutableStateFlow<List<Playlist>?> = MutableStateFlow(null)
    val playlistsForDisplay: StateFlow<List<Playlist>?> get() = _playlistsForDisplay

    init {
        requestPlaylists()
    }

    fun requestPlaylists() {
        viewModelScope.launch {
            requestPlaylistsUseCase(0, 10).collect { playlistsData ->
                playlistsData.onSuccess { playlists ->
                    _playlistsForDisplay.value = playlists
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            val playlistsRepository = PlaylistsRepositoryImpl(DataStoreManagerImpl)
            initializer {
                PlaylistsViewModel(
                    requestPlaylistsUseCase = RequestPlaylistsUseCase(playlistsRepository = playlistsRepository)
                )
            }
        }
    }
}