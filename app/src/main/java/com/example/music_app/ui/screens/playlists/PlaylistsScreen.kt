package com.example.music_app.ui.screens.playlists

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.music_app.R
import com.example.music_app.ui.theme.BASE_PADDING
import com.example.music_app.ui.theme.BLOCK_SIZE
import com.example.music_app.ui.theme.BUTTON_HEIGHT
import com.example.music_app.ui.theme.BUTTON_WIDTH
import com.example.music_app.ui.theme.Background1Light
import com.example.music_app.ui.theme.Background2Light
import com.example.music_app.ui.theme.PLAYLIST_ITEM_SIZE
import com.example.music_app.ui.theme.SHAPE_SIZE
import com.example.music_app.ui.theme.White

@Preview
@Composable
fun PlaylistsScreen(
    viewModel: PlaylistsViewModel = viewModel(factory = PlaylistsViewModel.Factory)
) {

    val playlists by viewModel.playlistsForDisplay.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val lazyListState: LazyListState = rememberLazyListState()

    LaunchedEffect(lazyListState.firstVisibleItemIndex) {
        viewModel.isScrollOnEnd(lazyListState.firstVisibleItemIndex)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(BASE_PADDING),
        verticalArrangement = Arrangement.spacedBy(BASE_PADDING),
        horizontalAlignment = CenterHorizontally
    ) {
        Surface(
            color = Background1Light,
            shape = RoundedCornerShape(SHAPE_SIZE),
            modifier = Modifier
                .fillMaxWidth()
                .height(BLOCK_SIZE)
        ) {
        }
        Surface(
            color = Background1Light,
            shape = RoundedCornerShape(SHAPE_SIZE),
            modifier = Modifier
                .fillMaxWidth()
                .height(BLOCK_SIZE)
        ) {
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(SHAPE_SIZE))
                .background(Background1Light)
        ) {
            LazyColumn(
                state = lazyListState,
                contentPadding = PaddingValues(BASE_PADDING),
                verticalArrangement = Arrangement.spacedBy(BASE_PADDING),
                modifier = Modifier
                    .fillMaxSize()
            ) {
                if (playlists != null) {
                    items(playlists?.size ?: 0) { index ->
                        PlaylistItem(
                            playlist = playlists?.get(index),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(PLAYLIST_ITEM_SIZE)
                                .clip(RoundedCornerShape(SHAPE_SIZE))
                                .background(Background2Light)
                                .padding(BASE_PADDING)
                        )
                    }
                }
            }
            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.Blue,
                    modifier = Modifier
                        .height(BLOCK_SIZE)
                        .width(BLOCK_SIZE)
                )
            }
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .height(BLOCK_SIZE)
                .clip(RoundedCornerShape(SHAPE_SIZE))
                .background(Background1Light)
        ) {
            Button(
                onClick = { },
                modifier = Modifier
                    .align(CenterHorizontally)
                    .width(BUTTON_WIDTH)
                    .height(BUTTON_HEIGHT)
            ) {
                Text(text = stringResource(R.string.new_playlist))
            }
        }
    }
}
