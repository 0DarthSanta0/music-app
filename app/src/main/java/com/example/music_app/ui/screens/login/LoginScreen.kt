package com.example.music_app.ui.screens.login


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.music_app.AppErrors
import com.example.music_app.constants.AUTH_URL
import com.example.music_app.ui.screens.core.components.ErrorMessage
import com.example.music_app.ui.screens.core.toUIStringRes
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onLoginError: () -> Unit,
    viewModel: LoginViewModel = viewModel(factory = LoginViewModel.Factory)
) {
    val state = rememberWebViewState(AUTH_URL)
    val error by viewModel.error.collectAsStateWithLifecycle()
    if (error != null) {
        ErrorMessage(
            errorId = error?.toUIStringRes() ?: 0,
            onClick = { onLoginError() },
            modifier = Modifier.fillMaxSize()
        )
    } else {
        WebView(
            state,
            modifier = Modifier.fillMaxSize(),
            onCreated = { webView ->
                with(webView) {
                    settings.javaScriptEnabled = true
                }
            },
            client = remember {
                AppLoginWebViewClient { code: String?, error: AppErrors? ->
                    viewModel.requestToken(
                        code = code,
                        error = error,
                        onLoginSuccess = onLoginSuccess
                    )
                }
            }
        )
    }
}
