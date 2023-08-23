package com.example.music_app.ui.screens.login


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.music_app.data.data_store.DataStoreManagerImpl
import com.example.music_app.data.repositories.login.LoginRepositoryImpl
import com.example.music_app.data.repositories.user.UserRepositoryImpl
import com.example.music_app.domain.use_cases.RequestTokenUseCase
import com.example.music_app.domain.use_cases.RequestUserIdUseCase
import com.github.michaelbull.result.onSuccess
import kotlinx.coroutines.launch

class LoginViewModel(
    private val requestTokenUseCase: RequestTokenUseCase,
    private val requestUserIdUseCase: RequestUserIdUseCase
) : ViewModel() {
    fun requestToken(code: String?, error: String?, onLoginSuccess: () -> Unit) {
        if (code != null) {
            viewModelScope.launch {
                requestTokenUseCase(code)
                    .collect { token ->
                        token.onSuccess {
                            requestUserIdUseCase()
                            onLoginSuccess()
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
