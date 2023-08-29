package com.example.music_app.ui.screens.playlists

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.music_app.R
import com.example.music_app.ui.screens.core.ButtonField
import com.example.music_app.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("FrequentlyChangedStateReadInComposition", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PlaylistsScreen(
    viewModel: PlaylistsViewModel = viewModel(factory = PlaylistsViewModel.Factory),
    onAddNewPlaylist: () -> Unit,
) {
    val playlists by viewModel.playlistsForDisplay.collectAsStateWithLifecycle()
    val error by viewModel.error.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val isFirstLoading by viewModel.isFirstLoading.collectAsStateWithLifecycle()
    val lazyListState: LazyListState = rememberLazyListState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(lazyListState.firstVisibleItemIndex) {
        viewModel.isScrollOnEnd(lazyListState.firstVisibleItemIndex)
    }

    LaunchedEffect(error) {
        if (error != null) {
            snackbarHostState.showSnackbar(
                message = "",
                duration = SnackbarDuration.Indefinite
            )
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) {
                Snackbar(
                    action = {
                        Button(
                            onClick = { viewModel.onError() },
                            modifier = Modifier.padding(AppTheme.dimens.spacing10)
                        ) {
                            Text(text = stringResource(id = R.string.repeat))
                        }
                    },
                    modifier = Modifier.padding(AppTheme.dimens.spacing20)
                ) {
                    Text(text = stringResource(id = error?.errorId ?: R.string.error))
                }
            }
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(AppTheme.dimens.spacing10),
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.spacing10),
            horizontalAlignment = CenterHorizontally
        ) {
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(AppTheme.dimens.spacing08),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppTheme.dimens.spacing80)
            ) {
            }
            PlaylistsField(
                lazyListState = lazyListState,
                playlists = playlists,
                isLoading = isLoading,
                isFirstLoading = isFirstLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            ButtonField(
                text = stringResource(R.string.new_playlist),
                onClick = { onAddNewPlaylist() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppTheme.dimens.spacing80)
            )
        }
    }
}
