package com.example.music_app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.music_app.data.data_store.DataStoreManagerImpl
import com.example.music_app.data.repositories.LoginRepositoryImpl
import com.example.music_app.domain.use_cases.IsAuthorizedCheckUseCase
import com.example.music_app.ui.navigation.Screens
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val isAuthorizedCheckUseCase: IsAuthorizedCheckUseCase
) : ViewModel() {

    private val mutableStateFlow = MutableStateFlow(true)
    private val _route: MutableStateFlow<Screens?> = MutableStateFlow(null)
    val isLoading = mutableStateFlow.asStateFlow()
    val route: StateFlow<Screens?> get() = _route

    init {
        checkStatus()
        viewModelScope.launch {
            delay(2000)
            mutableStateFlow.value = false
        }
    }

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
                MainActivityViewModel(
                    isAuthorizedCheckUseCase = IsAuthorizedCheckUseCase(loginRepository = loginRepository)
                )
            }
        }
    }

}