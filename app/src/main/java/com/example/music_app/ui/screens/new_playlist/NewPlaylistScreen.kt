package com.example.music_app.ui.screens.new_playlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.music_app.ui.theme.BASE_PADDING
import com.example.music_app.ui.theme.BLOCK_SIZE
import com.example.music_app.ui.theme.BUTTON_HEIGHT
import com.example.music_app.ui.theme.BUTTON_WIDTH
import com.example.music_app.ui.theme.Background1Light
import com.example.music_app.ui.theme.SHAPE_SIZE
import com.example.music_app.ui.theme.White

@Preview
@Composable
fun NewPlaylistScreen(
    viewModel: NewPlaylistViewModel = viewModel(factory = NewPlaylistViewModel.Factory)
) {
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
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(SHAPE_SIZE))
                .background(Background1Light)
        ) {

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

            }
        }
    }
}
