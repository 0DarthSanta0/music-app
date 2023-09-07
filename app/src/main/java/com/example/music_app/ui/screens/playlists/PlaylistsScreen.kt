package com.example.music_app.ui.screens.playlists

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.music_app.R
import com.example.music_app.ui.screens.core.components.ButtonField
import com.example.music_app.ui.screens.core.components.ErrorScaffold
import com.example.music_app.ui.screens.core.components.ScrollIndexChange
import com.example.music_app.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistsScreen(
    viewModel: PlaylistsViewModel = viewModel(factory = PlaylistsViewModel.Factory),
    onAddNewPlaylist: () -> Unit,
    onSearch: () -> Unit
) {
    val error by viewModel.error.collectAsStateWithLifecycle()

    val lazyListState: LazyListState = rememberLazyListState()
    val snackbarHostState = remember { SnackbarHostState() }

    ScrollIndexChange(
        lazyListState = lazyListState,
        onScrollIndexChange = viewModel::onUIScrollIndexChange
    )

    ErrorScaffold(
        error = error,
        snackbarHostState = snackbarHostState,
        onClick = { viewModel.onError() }
    ) { paddingValues ->
        PlaylistScreenContent(
            modifier = Modifier.padding(paddingValues),
            viewModel = viewModel,
            lazyListState = lazyListState,
            onAddNewPlaylist = onAddNewPlaylist,
            onSearch = onSearch
        )
    }
}

@Composable
private fun PlaylistScreenContent(
    modifier: Modifier = Modifier,
    viewModel: PlaylistsViewModel,
    lazyListState: LazyListState,
    onAddNewPlaylist: () -> Unit,
    onSearch: () -> Unit
) {
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val isFirstLoading by viewModel.isFirstLoading.collectAsStateWithLifecycle()
    val playlists by viewModel.playlistsForDisplay.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(AppTheme.dimens.spacing10),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.spacing10),
        horizontalAlignment = CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(AppTheme.dimens.spacing80)
                .clip(RoundedCornerShape(AppTheme.dimens.spacing08))
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(AppTheme.dimens.spacing60)
            )
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(AppTheme.dimens.spacing40)
                    .padding(AppTheme.dimens.spacing05)
                    .clip(RoundedCornerShape(AppTheme.dimens.spacing20))
                    .clickable {
                        onSearch()
                    }
            )
        }
        PlaylistsField(
            lazyListState = lazyListState,
            playlists = playlists,
            isLoading = isLoading,
            isFirstLoading = isLoading && isFirstLoading,
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
