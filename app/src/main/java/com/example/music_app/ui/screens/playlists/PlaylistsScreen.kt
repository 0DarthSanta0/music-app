package com.example.music_app.ui.screens.playlists

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.music_app.R
import com.example.music_app.ui.theme.BASE_PADDING
import com.example.music_app.ui.theme.BLOCK_SIZE
import com.example.music_app.ui.theme.BUTTON_HEIGHT
import com.example.music_app.ui.theme.BUTTON_WIDTH
import com.example.music_app.ui.theme.Background1Light
import com.example.music_app.ui.theme.SHAPE_SIZE
import com.example.music_app.ui.theme.White

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
            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.Blue,
                    modifier = Modifier
                        .height(BLOCK_SIZE)
                        .width(BLOCK_SIZE)
                )
            } else {
                PlaylistsField(
                    lazyListState = lazyListState,
                    playlists = playlists,
                    modifier = Modifier
                        .fillMaxWidth()
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
                    .width(BUTTON_WIDTH)
                    .height(BUTTON_HEIGHT)
            ) {
                Text(text = stringResource(R.string.new_playlist))
            }
        }
    }
}
