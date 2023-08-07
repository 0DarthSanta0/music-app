package com.example.music_app.ui.screens.playlists

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.music_app.data.data_store.DataStoreManagerImpl
import com.example.music_app.data.models.ListOfPlaylists
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

    private var totalSize: Int? = null
    private var offset = 0
    private val LIMIT = 10
    private val _playlistsForDisplay: MutableStateFlow<List<Playlist>?> = MutableStateFlow(null)
    val playlistsForDisplay: StateFlow<List<Playlist>?> get() = _playlistsForDisplay

    init {
        requestPlaylists()
    }

    private fun requestPlaylists() {
        viewModelScope.launch {
            requestPlaylistsUseCase(offset, LIMIT).collect { playlistsData ->
                playlistsData.onSuccess { listOfPlaylists: ListOfPlaylists ->
                    _playlistsForDisplay.value =
                        _playlistsForDisplay.value?.plus(listOfPlaylists.playlists)
                            ?: listOfPlaylists.playlists
                    totalSize = listOfPlaylists.totalSize
                    offset += LIMIT
                }
            }
        }
    }

    fun isScrollOnEnd(lazyListState: LazyListState) {
        if (lazyListState.firstVisibleItemIndex == (offset - 5)) requestPlaylists()
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