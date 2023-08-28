package com.example.music_app.ui.screens.login


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.music_app.AppErrors
import com.example.music_app.data.data_store.DataStoreManagerImpl
import com.example.music_app.data.repositories.login.LoginRepositoryImpl
import com.example.music_app.data.repositories.user.UserRepositoryImpl
import com.example.music_app.domain.use_cases.RequestTokenUseCase
import com.example.music_app.domain.use_cases.RequestUserIdUseCase
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val requestTokenUseCase: RequestTokenUseCase,
    private val requestUserIdUseCase: RequestUserIdUseCase
) : ViewModel() {
    private val _error: MutableStateFlow<AppErrors?> = MutableStateFlow(null)
    val error: StateFlow<AppErrors?> get() = _error

    fun requestToken(code: String?, error: AppErrors?, onLoginSuccess: () -> Unit) {
        if (error != null) {
            _error.value = error
        }
        if (code != null) {
            viewModelScope.launch {
                requestTokenUseCase(code)
                    .collect { token ->
                        token.onSuccess {
                            requestUserIdUseCase()
                            onLoginSuccess()
                        }.onFailure {tokenResponseError ->
                            _error.value = tokenResponseError
                        }
                    }
            }
        }

    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                LoginViewModel(
                    requestTokenUseCase = RequestTokenUseCase(
                        loginRepository = LoginRepositoryImpl(DataStoreManagerImpl)
                    ),
                    requestUserIdUseCase = RequestUserIdUseCase(
                        userRepository = UserRepositoryImpl(DataStoreManagerImpl)
                    )
                )
            }
        }
    }
}
