package com.example.music_app.domain.use_cases

import com.example.music_app.domain.repositories.LoginRepository

class IsOutdatedCheckUseCase(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke() = loginRepository.isOutdated()
}