package com.example.music_app.ui.screens.new_playlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.music_app.AppErrors
import com.example.music_app.data.data_store.DataStoreManagerImpl
import com.example.music_app.data.repositories.playlists.PlaylistsRepositoryImpl
import com.example.music_app.domain.use_cases.CreatePlaylistUseCase
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

private const val MAX_NAME_SIZE = 20
private const val MAX_DESCRIPTION_SIZE = 300

class NewPlaylistViewModel(
    private val createPlaylistUseCase: CreatePlaylistUseCase
) : ViewModel() {

    private val _nameFieldState: MutableStateFlow<String> = MutableStateFlow("")
    val nameFieldState: StateFlow<String> get() = _nameFieldState
    private val _descriptionFieldState: MutableStateFlow<String> = MutableStateFlow("")
    val descriptionFieldState: StateFlow<String> get() = _descriptionFieldState
    private val _isNameValid: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isNameValid: StateFlow<Boolean> get() = _isNameValid
    private val _isNameFieldActive: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isNameFieldActive: StateFlow<Boolean> get() = _isNameFieldActive

    private fun createPlaylist(
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

    fun onCreatePlaylist(onCreatePlaylistSuccess: () -> Unit) {
        if (_isNameValid.value) {
            createPlaylist(
                name = nameFieldState.value,
                description = descriptionFieldState.value.ifEmpty { null },
                onCreatePlaylistSuccess = onCreatePlaylistSuccess
            )
        } else {
            _isNameFieldActive.value = true
        }
    }

    fun onNameValueChange(newValue: String) {
        if (MAX_NAME_SIZE >= newValue.length) _nameFieldState.value = newValue
        _isNameValid.value = newValue.isNotBlank()
        _isNameFieldActive.value = true
    }

    fun onDescriptionValueChange(newValue: String) {
        if (MAX_DESCRIPTION_SIZE >= newValue.length) _descriptionFieldState.value = newValue
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                NewPlaylistViewModel(
                    createPlaylistUseCase = CreatePlaylistUseCase(
                        PlaylistsRepositoryImpl(
                            DataStoreManagerImpl
                        )
                    )
                )
            }
        }
    }
}
