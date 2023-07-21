package com.example.music_app.domain.use_cases

import com.example.music_app.domain.repositories.LoginRepository

class IsAuthorizedCheckUseCase(
    private val loginRepository: LoginRepository
) {
    operator fun invoke() = loginRepository.isAuthorized()
}