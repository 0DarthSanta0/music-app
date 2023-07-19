package com.example.music_app.ui.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.music_app.data.LoginRepositoryImpl
import com.example.music_app.domain.RequestTokenUseCase
import kotlinx.coroutines.launch

class LoginViewModel(
    private val requestTokenUseCase: RequestTokenUseCase
) : ViewModel() {

    fun requestToken(code: String?, error: String?) {
        if (code != null) {
            viewModelScope.launch {
                requestTokenUseCase(code)
                    .collect { Tokens ->
                        Log.d("tokens", Tokens.component1().toString())
                    }
            }
        }

    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                LoginViewModel(
                    requestTokenUseCase = RequestTokenUseCase(loginRepository = LoginRepositoryImpl()),
                )
            }
        }
    }

}