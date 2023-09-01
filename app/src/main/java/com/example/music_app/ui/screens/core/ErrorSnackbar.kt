package com.example.music_app.ui.screens.core

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.music_app.R
import com.example.music_app.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorSnackbar(
    errorId: Int,
    snackbarHostState: SnackbarHostState,
    onClick: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) {
                Snackbar(
                    action = {
                        Button(
                            onClick = { onClick() },
                            modifier = Modifier.padding(AppTheme.dimens.spacing10)
                        ) {
                            Text(text = stringResource(id = R.string.repeat))
                        }
                    },
                    modifier = Modifier.padding(AppTheme.dimens.spacing20)
                ) {
                    Text(text = stringResource(id = errorId))
                }
            }
        },
    ) { paddingValues ->
        content(paddingValues)
    }
}
