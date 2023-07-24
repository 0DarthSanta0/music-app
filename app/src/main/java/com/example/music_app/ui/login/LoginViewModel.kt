package com.example.music_app.ui.login


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.music_app.data.data_store.DataStoreManagerManagerImpl
import com.example.music_app.data.repositories.LoginRepositoryImpl
import com.example.music_app.domain.use_cases.IsAuthorizedCheckUseCase
import com.example.music_app.domain.use_cases.RequestTokenUseCase
import kotlinx.coroutines.launch

class LoginViewModel(
    private val requestTokenUseCase: RequestTokenUseCase,
    private val isAuthorizedCheckUseCase: IsAuthorizedCheckUseCase
) : ViewModel() {

    fun requestToken(code: String?, error: String?) {
        if (code != null) {
            viewModelScope.launch {
                requestTokenUseCase(code)
                    .collect { token ->
                        Log.d("token", token.component1().toString())
                    }
            }
        }

    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            val loginRepository = LoginRepositoryImpl(DataStoreManagerManagerImpl)
            initializer {
                LoginViewModel(
                    requestTokenUseCase = RequestTokenUseCase(loginRepository = loginRepository),
                    isAuthorizedCheckUseCase = IsAuthorizedCheckUseCase(loginRepository = loginRepository)
                )
            }
        }
    }

}