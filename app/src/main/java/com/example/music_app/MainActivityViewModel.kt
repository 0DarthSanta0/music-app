package com.example.music_app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.music_app.domain.use_cases.IsAuthorizedCheckUseCase
import com.example.music_app.domain.use_cases.IsOutdatedCheckUseCase
import com.example.music_app.domain.use_cases.RequestRefreshTokenUseCase
import com.example.music_app.ui.navigation.Screens
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val WAIT_TIME: Long = 2000

class MainActivityViewModel(
    private val isAuthorizedCheckUseCase: IsAuthorizedCheckUseCase,
    private val isOutdatedCheckUseCase: IsOutdatedCheckUseCase,
    private val requestRefreshTokenUseCase: RequestRefreshTokenUseCase
) : ViewModel() {

    private val _isSplashScreenVisible: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isSplashScreenVisible: StateFlow<Boolean> = _isSplashScreenVisible.asStateFlow()
    private val _route: MutableStateFlow<Screens?> = MutableStateFlow(null)
    val route: StateFlow<Screens?> get() = _route
    private val _error: MutableStateFlow<AppErrors?> = MutableStateFlow(null)
    val error: StateFlow<AppErrors?> get() = _error

    init {
        checkStatus()
    }

    private fun changeRouteState(screen: Screens) {
        _route.value = screen
    }

    private fun changeErrorState(appError: AppErrors) {
        _error.value = appError
    }

    fun checkStatus() {
        viewModelScope.launch {
            isAuthorizedCheckUseCase().onSuccess { isAuth ->
                if (isAuth) {
                    isOutdatedCheckUseCase().onSuccess { isOutdated ->
                        if (isOutdated) {
                            requestRefreshTokenUseCase().collect { token ->
                                token.onSuccess {
                                    changeRouteState(Screens.PlaylistsScreen)
                                }.onFailure { tokenRequestError ->
                                    changeErrorState(tokenRequestError)
                                }
                            }
                        } else {
                            changeRouteState(Screens.PlaylistsScreen)
                        }
                    }.onFailure { isOutdatedError ->
                        changeErrorState(isOutdatedError)
                    }
                } else {
                    changeRouteState(Screens.LoginScreen)
                }
            }.onFailure { isAuthError ->
                changeErrorState(isAuthError)
            }
            delay(WAIT_TIME)
            _isSplashScreenVisible.value = false
        }
    }
}
