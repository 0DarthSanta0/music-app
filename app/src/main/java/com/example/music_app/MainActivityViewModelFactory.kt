package com.example.music_app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.music_app.data.data_store.DataStoreManagerImpl
import com.example.music_app.data.repositories.login.LoginRepositoryImpl
import com.example.music_app.domain.use_cases.IsAuthorizedCheckUseCase
import com.example.music_app.domain.use_cases.IsOutdatedCheckUseCase
import com.example.music_app.domain.use_cases.RequestRefreshTokenUseCase

class MainActivityViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val loginRepository = LoginRepositoryImpl(DataStoreManagerImpl)
        return MainActivityViewModel(
            isAuthorizedCheckUseCase = IsAuthorizedCheckUseCase(loginRepository = loginRepository),
            isOutdatedCheckUseCase = IsOutdatedCheckUseCase(loginRepository = loginRepository),
            requestRefreshTokenUseCase = RequestRefreshTokenUseCase(loginRepository = loginRepository)
        ) as T
    }
}