package com.example.music_app.ui.login


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.music_app.MusicAppApplication
import com.example.music_app.data.LoginStore
import com.example.music_app.data.repositories.LoginRepositoryImpl
import com.example.music_app.domain.use_cases.GetTokenUseCase
import com.example.music_app.domain.use_cases.RequestTokenUseCase
import com.example.music_app.domain.use_cases.SaveTokenUseCase
import kotlinx.coroutines.launch

class LoginViewModel(
    private val requestTokenUseCase: RequestTokenUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
    private val getTokenUseCase: GetTokenUseCase
) : ViewModel() {

    fun requestToken(code: String?, error: String?) {
        if (code != null) {
            viewModelScope.launch {
                getTokenUseCase().collect {Token ->
                    Log.d("savedToken", Token)
                }
                requestTokenUseCase(code)
                    .collect { Tokens ->
                        saveTokenUseCase(Tokens.component1()!!.accessToken)
                    }
            }
        }

    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            val loginRepository = LoginRepositoryImpl(LoginStore(context = MusicAppApplication.applicationContext()))
            initializer {
                LoginViewModel(
                    requestTokenUseCase = RequestTokenUseCase(loginRepository = loginRepository),
                    saveTokenUseCase = SaveTokenUseCase(loginRepository = loginRepository),
                    getTokenUseCase = GetTokenUseCase(loginRepository = loginRepository)
                )
            }
        }
    }

}