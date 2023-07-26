package com.example.music_app.ui.screens.login


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.music_app.data.data_store.DataStoreManagerImpl
import com.example.music_app.data.repositories.LoginRepositoryImpl
import com.example.music_app.domain.use_cases.RequestTokenUseCase
import com.example.music_app.ui.navigation.Screens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val requestTokenUseCase: RequestTokenUseCase
) : ViewModel() {

    private val _route: MutableStateFlow<Screens?> = MutableStateFlow(null)
    val route: StateFlow<Screens?> get() = _route

    fun requestToken(code: String?, error: String?) {
        if (code != null) {
            viewModelScope.launch {
                requestTokenUseCase(code)
                    .collect { token ->
                        _route.value = if (token.component1() != null) Screens.PlaylistsScreen else Screens.LoginScreen
                    }
            }
        }

    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            val loginRepository = LoginRepositoryImpl(DataStoreManagerImpl)
            initializer {
                LoginViewModel(
                    requestTokenUseCase = RequestTokenUseCase(loginRepository = loginRepository)
                )
            }
        }
    }

}