package com.example.music_app.domain

import com.example.music_app.data.LoginRepository

class RequestTokenUseCase {
    private val loginRepository: LoginRepository = LoginRepository()

    suspend fun requestToken(code: String) = loginRepository.requestToken(code)
}