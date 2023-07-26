package com.example.music_app.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.music_app.data.data_store.DataStoreManagerImpl
import com.example.music_app.data.repositories.LoginRepositoryImpl
import com.example.music_app.domain.use_cases.IsAuthorizedCheckUseCase
import com.example.music_app.ui.navigation.Screens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val isAuthorizedCheckUseCase: IsAuthorizedCheckUseCase
) : ViewModel() {

    init {
        checkStatus()
    }

    private val _route: MutableStateFlow<Screens?> = MutableStateFlow(null)
    val route: StateFlow<Screens?> get() = _route

    private fun checkStatus() {
        viewModelScope.launch {
            isAuthorizedCheckUseCase()
                .collect {isAuth ->
                    _route.value = if (isAuth) Screens.PlaylistsScreen else Screens.LoginScreen
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            val loginRepository = LoginRepositoryImpl(DataStoreManagerImpl)
            initializer {
                MainViewModel(
                    isAuthorizedCheckUseCase = IsAuthorizedCheckUseCase(loginRepository = loginRepository)
                )
            }
        }
    }

}