package com.example.music_app.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.music_app.AppErrors
import com.example.music_app.data.data_store.DataStoreManagerImpl
import com.example.music_app.data.models.Playlist
import com.example.music_app.data.repositories.playlists.PlaylistsRepositoryImpl
import com.example.music_app.domain.use_cases.RequestPlaylistsForSearchUseCase
import com.example.music_app.ui.screens.core.onScrollIndexChange
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private const val LIMIT = 10
private const val INDEX = 6

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val searchUseCase: RequestPlaylistsForSearchUseCase
) : ViewModel() {
    private var totalSize = 0
    private var globalOffset = 0
    private var currentText = ""

    private val _error: MutableStateFlow<AppErrors?> = MutableStateFlow(null)
    val error: StateFlow<AppErrors?> get() = _error
    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()
    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()
    private val _playlists = MutableStateFlow(listOf<Playlist>())
    val playlists: StateFlow<List<Playlist>> = searchText
        .debounce(300L)
        .combine(_playlists) { text, _ ->
            when {
                text.isBlank() || text.length == 1 -> emptyList()
                text == currentText -> _playlists.value
                else -> {
                    currentText = text
                    _isSearching.value = true
                    requestPlaylistsForSearch(
                        text = text,
                        isGetMorePlaylistsCase = false
                    ).join()
                    _playlists.value
                }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _playlists.value
        )

    private fun changeErrorState(appError: AppErrors?) {
        _error.value = appError
    }

    private fun requestPlaylistsForSearch(
        text: String,
        isGetMorePlaylistsCase: Boolean,
        offset: Int = 0
    ) = viewModelScope.launch {
        searchUseCase(
            offset = offset,
            limit = LIMIT,
            prefix = text
        ).collect { playlistsResponse ->
            playlistsResponse.onSuccess { playlists ->
                if (isGetMorePlaylistsCase) {
                    _playlists.value += playlists.playlists
                    globalOffset += LIMIT
                    _isLoading.value = false
                } else {
                    _playlists.value = playlists.playlists
                    totalSize = playlists.totalSize
                    globalOffset = LIMIT
                    _isSearching.value = false
                }
            }.onFailure { playlistsResponseError ->
                changeErrorState(playlistsResponseError)
            }
        }
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun onUIScrollIndexChange(firstVisibleItemIndex: Int) {
        onScrollIndexChange(
            firstVisibleItemIndex = firstVisibleItemIndex,
            offset = globalOffset,
            index = INDEX,
            size = totalSize,
            onSuccessful = {
                _isLoading.value = true
                requestPlaylistsForSearch(
                    text = _searchText.value,
                    offset = globalOffset,
                    isGetMorePlaylistsCase = true,
                )
            }
        )
    }

    fun onError() {
        changeErrorState(null)
        _isLoading.value = false
        _isSearching.value = false
        _playlists.value = listOf()
        _searchText.value = ""
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SearchViewModel(
                    searchUseCase = RequestPlaylistsForSearchUseCase(
                        playlistsRepository = PlaylistsRepositoryImpl(
                            DataStoreManagerImpl
                        )
                    )
                )
            }
        }
    }
}
