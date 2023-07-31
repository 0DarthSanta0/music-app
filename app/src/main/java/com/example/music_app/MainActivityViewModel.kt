package com.example.music_app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.music_app.domain.use_cases.IsAuthorizedCheckUseCase
import com.example.music_app.ui.navigation.Screens
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val WAIT_TIME: Long = 2000

class MainActivityViewModel(
    private val isAuthorizedCheckUseCase: IsAuthorizedCheckUseCase
) : ViewModel() {

    private val _isSplashScreenVisible: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isSplashScreenVisible: StateFlow<Boolean> = _isSplashScreenVisible.asStateFlow()
    private val _route: MutableStateFlow<Screens?> = MutableStateFlow(null)
    val route: StateFlow<Screens?> get() = _route

    init {
        checkStatus()
    }

    private fun checkStatus() {
        viewModelScope.launch {
            isAuthorizedCheckUseCase()
                .collect {isAuth ->
                    _route.value = if (isAuth) Screens.PlaylistsScreen else Screens.LoginScreen
                    delay(WAIT_TIME)
                    _isSplashScreenVisible.value = false
                }
        }
    }

}