package com.example.music_app.ui.screens.playlists

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.music_app.R
import com.example.music_app.ui.theme.AppTheme

@Preview
@Composable
fun PlaylistsScreen(
    viewModel: PlaylistsViewModel = viewModel(factory = PlaylistsViewModel.Factory)
) {

    val playlists by viewModel.playlistsForDisplay.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val lazyListState: LazyListState = rememberLazyListState()

    LaunchedEffect(lazyListState.firstVisibleItemIndex) {
        viewModel.isScrollOnEnd(lazyListState.firstVisibleItemIndex)
    }

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
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .height(AppTheme.dimens.spacing80)
                .clip(RoundedCornerShape(AppTheme.dimens.spacing08))
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            Button(
                onClick = { },
                modifier = Modifier
                    .width(AppTheme.dimens.spacing170)
                    .height(AppTheme.dimens.spacing40)
            ) {
                Text(text = stringResource(R.string.new_playlist))
            }
        }
    }
}
