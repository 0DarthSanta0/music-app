package com.example.music_app.domain.use_cases

import com.example.music_app.domain.repositories.UserRepository

class RequestUserIdUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke() = userRepository.requestUserID()
}
