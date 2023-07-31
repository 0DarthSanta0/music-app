package com.example.music_app.ui.screens.login


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.music_app.constants.AUTH_URL
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.music_app.ui.navigation.Screens


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun LoginScreen(
    onLoginSuccess: (screen: Screens) -> Unit,
    viewModel: LoginViewModel = viewModel(factory = LoginViewModel.Factory)
) {
    val state = rememberWebViewState(AUTH_URL)
    WebView(
        state,
        modifier = Modifier.fillMaxHeight(),
        onCreated = { webView ->
            with(webView) {
                settings.javaScriptEnabled = true
            }
        },
        client = remember {
            AppLoginWebViewClient { code: String?, error: String? ->
                viewModel.requestToken(
                    code = code,
                    error = error,
                    onLoginSuccess = onLoginSuccess
                )
            }
        }

    )

}
