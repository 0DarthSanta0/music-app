package com.example.music_app.ui.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.music_app.domain.RequestTokenUseCase
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val requestTokenUseCase: RequestTokenUseCase = RequestTokenUseCase()

    fun requestToken(code: String?, error: String?) {
        if (code != null) {
            viewModelScope.launch {
                requestTokenUseCase.requestToken(code)
                    .collect { Tokens ->
                        Log.d("tokens", Tokens.toString())
                    }
            }
        }

    }
}