package com.example.music_app.ui.screens.playlists

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.music_app.R
import com.example.music_app.ui.theme.BASE_PADDING
import com.example.music_app.ui.theme.Background1Light
import com.example.music_app.ui.theme.Background2Light
import com.example.music_app.ui.theme.SHAPE_SIZE
import com.example.music_app.ui.theme.White

@Preview
@Composable
fun PlaylistsScreen(
    viewModel: PlaylistsViewModel = viewModel(factory = PlaylistsViewModel.Factory)
) {

    val playlists by viewModel.playlistsForDisplay.collectAsState()
    val lazyListState: LazyListState = rememberLazyListState()

    LaunchedEffect(lazyListState.firstVisibleItemIndex) {
        viewModel.isScrollOnEnd(lazyListState = lazyListState)
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
                .height(80.dp)
        ) {
        }
        Surface(
            color = Background1Light,
            shape = RoundedCornerShape(SHAPE_SIZE),
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        ) {
        }
        Surface(
            color = Background1Light,
            shape = RoundedCornerShape(SHAPE_SIZE),
            modifier = Modifier
                .fillMaxWidth()
                .height(540.dp)
        ) {
            LazyColumn(
                state = lazyListState,
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (playlists != null) {
                    items(playlists?.size ?: 0) { index ->
                        PlaylistItem(
                            playlist = playlists?.get(index),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .clip(RoundedCornerShape(SHAPE_SIZE))
                                .background(Background2Light)
                                .padding(BASE_PADDING)
                        )
                    }
                }
            }
        }
        Surface(
            color = Background1Light,
            shape = RoundedCornerShape(SHAPE_SIZE),
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = CenterHorizontally
            ) {
                Button(
                    onClick = { },
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .width(170.dp)
                        .height(40.dp)
                ) {
                    Text(text = stringResource(R.string.new_playlist))
                }
            }
        }
    }
}
