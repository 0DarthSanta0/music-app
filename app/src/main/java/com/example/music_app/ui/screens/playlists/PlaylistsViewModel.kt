package com.example.music_app.ui.screens.playlists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.music_app.data.data_store.DataStoreManagerImpl
import com.example.music_app.data.models.ListOfPlaylists
import com.example.music_app.data.models.Playlist
import com.example.music_app.data.repositories.playlists.PlaylistsRepositoryImpl
import com.example.music_app.domain.use_cases.RequestPlaylistsUseCase
import com.github.michaelbull.result.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

private const val LIMIT = 10

class PlaylistsViewModel(
    private val requestPlaylistsUseCase: RequestPlaylistsUseCase
) : ViewModel() {

    private var totalSize = 0
    private var offset = 0
    private val _playlistsForDisplay: MutableStateFlow<List<Playlist>> = MutableStateFlow(listOf())
    val playlistsForDisplay: StateFlow<List<Playlist>> get() = _playlistsForDisplay
    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    init {
        requestPlaylists()
    }

    private fun requestPlaylists() {
        viewModelScope.launch {
            requestPlaylistsUseCase(offset, LIMIT).collect { playlistsData ->
                playlistsData.onSuccess { listOfPlaylists: ListOfPlaylists ->
                    _playlistsForDisplay.value =
                        _playlistsForDisplay.value + listOfPlaylists.playlists
                    totalSize = listOfPlaylists.totalSize
                    offset += LIMIT
                    _isLoading.value = false
                }
            }
        }
    }

    fun isScrollOnEnd(firstVisibleItemIndex: Int) {
        if (firstVisibleItemIndex == (offset - 5) && (totalSize - offset) > 0) {
            _isLoading.value = true
            requestPlaylists()
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                PlaylistsViewModel(
                    requestPlaylistsUseCase = RequestPlaylistsUseCase(playlistsRepository = PlaylistsRepositoryImpl(DataStoreManagerImpl))
                )
            }
        }
    }
}
