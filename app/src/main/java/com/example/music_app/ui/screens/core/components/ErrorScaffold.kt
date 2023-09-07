package com.example.music_app.ui.screens.core.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.music_app.AppErrors
import com.example.music_app.R
import com.example.music_app.ui.screens.core.toUIStringRes
import com.example.music_app.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(containerColor),
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    error: AppErrors?,
    snackbarHostState: SnackbarHostState,
    onClick: () -> Unit,
    content: @Composable (PaddingValues) -> Unit,
) {
    LaunchedEffect(error) {
        if (error != null) {
            snackbarHostState.showSnackbar(
                message = "",
                duration = SnackbarDuration.Indefinite
            )
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = topBar,
        bottomBar = bottomBar,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        containerColor = containerColor,
        contentColor = contentColor,
        contentWindowInsets = contentWindowInsets,
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
                    Text(text = stringResource(id = error?.toUIStringRes() ?: R.string.error))
                }
            }
        },
    ) { paddingValues ->
        content(paddingValues)
    }
}
