package com.example.music_app.domain

import com.example.music_app.data.models.LoginRepository

class RequestTokenUseCase(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(code: String) = loginRepository.requestToken(code)
}