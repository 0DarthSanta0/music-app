package com.example.music_app.ui.screens.playlists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.music_app.AppErrors
import com.example.music_app.data.data_store.DataStoreManagerImpl
import com.example.music_app.data.models.ListOfPlaylists
import com.example.music_app.data.models.Playlist
import com.example.music_app.data.repositories.playlists.PlaylistsRepositoryImpl
import com.example.music_app.domain.use_cases.RequestPlaylistsUseCase
import com.example.music_app.ui.screens.core.onScrollIndexChange
import com.example.music_app.ui.screens.core.reLoginCheck
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

private const val LIMIT = 10
private const val INDEX = 6

class PlaylistsViewModel(
    private val requestPlaylistsUseCase: RequestPlaylistsUseCase
) : ViewModel() {
    private var totalSize = 0
    private var offset = 0
    private val _playlistsForDisplay: MutableStateFlow<List<Playlist>> = MutableStateFlow(listOf())
    val playlistsForDisplay: StateFlow<List<Playlist>> get() = _playlistsForDisplay
    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> get() = _isLoading
    private val _isFirstLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isFirstLoading: StateFlow<Boolean> get() = _isFirstLoading
    private val _error: MutableStateFlow<AppErrors?> = MutableStateFlow(null)
    val error: StateFlow<AppErrors?> get() = _error

    init {
        requestPlaylists()
    }

    private fun changeErrorState(appError: AppErrors?) {
        _error.value = appError
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
                }.onFailure { requestError ->
                    changeErrorState(requestError)
                }
            }
        }
    }

    fun onUIScrollIndexChange(firstVisibleItemIndex: Int) {
        onScrollIndexChange(
            firstVisibleItemIndex = firstVisibleItemIndex,
            offset = offset,
            index = INDEX,
            size = totalSize,
            onSuccessful = {
                _isLoading.value = true
                _isFirstLoading.value = false
                requestPlaylists()
            }
        )
    }

    fun onError(navigateOnError: () -> Unit) {
        reLoginCheck(
            error = _error.value,
            onTrue = navigateOnError,
            onFalse = {
                changeErrorState(null)
                requestPlaylists()
            }
        )
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                PlaylistsViewModel(
                    requestPlaylistsUseCase = RequestPlaylistsUseCase(
                        playlistsRepository = PlaylistsRepositoryImpl(
                            DataStoreManagerImpl
                        )
                    )
                )
            }
        }
    }
}
