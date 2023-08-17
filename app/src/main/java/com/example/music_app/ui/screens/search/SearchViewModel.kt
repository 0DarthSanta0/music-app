package com.example.music_app.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.music_app.data.models.Playlist
import com.example.music_app.data.repositories.playlists.PlaylistsRepositoryImpl
import com.example.music_app.domain.use_cases.RequestPlaylistsForSearchUseCase
import com.github.michaelbull.result.onSuccess
import com.google.android.material.color.utilities.PointProviderLab
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.descriptors.StructureKind


@OptIn(FlowPreview::class)
class SearchViewModel(
    private val searchUseCase: RequestPlaylistsForSearchUseCase
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _playlists = MutableStateFlow(listOf<Playlist>())
    val playlists: StateFlow<List<Playlist>> = searchText
        .debounce(500L)
        .onEach { _isSearching.update { true } }
        .combine(_playlists) { text, playlistsState ->
            if (text.isBlank() || text.length == 1) {
                emptyList()
            } else {
                viewModelScope.launch {
                    val repoResult = searchUseCase(offset = 0, limit = 10, prefix = text)
                    repoResult.collect {playlists ->
                        playlists.onSuccess { _playlists.value = it.playlists }
                    }
                }
                _playlists.value
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _playlists.value
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SearchViewModel(
                    searchUseCase = RequestPlaylistsForSearchUseCase(playlistsRepository = PlaylistsRepositoryImpl())
                )
            }
        }
    }
}