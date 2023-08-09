package com.example.music_app.ui.screens.playlists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.music_app.data.models.Playlist
import com.example.music_app.ui.theme.BASE_PADDING
import com.example.music_app.ui.theme.IMAGE_SIZE
import com.example.music_app.ui.theme.SHAPE_SIZE
import com.example.music_app.ui.theme.White

private const val DEFAULT_IMAGE =
    "https://play-lh.googleusercontent.com/qZpgL9UUqJ4vqDPV8nRYACCt5DUFrMZxJHwozHNsckNBa3x3nWcliksooqaw_DEzvQ=w240-h480-rw"
private const val DEFAULT_TEXT = "Something went wrong"

@Composable
fun PlaylistItem(
    playlist: Playlist?,
    modifier: Modifier = Modifier,
) {

    var isLoading by remember { mutableStateOf(true) }

    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top,
        modifier = modifier
    ) {
        val image = if (!playlist?.images.isNullOrEmpty()) {
            playlist?.images?.get(0)?.url
        } else {
            DEFAULT_IMAGE
        }
        Box(
            modifier = Modifier
                .height(IMAGE_SIZE)
                .width(IMAGE_SIZE)
        ) {
            AsyncImage(
                onSuccess = {
                    isLoading = false
                },
                model = image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(SHAPE_SIZE))
            )
            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.Blue,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                style = MaterialTheme.typography.titleLarge,
                text = playlist?.name ?: DEFAULT_TEXT,
                color = White,
                modifier = Modifier.padding(start = BASE_PADDING),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = playlist?.description ?: DEFAULT_TEXT,
                color = White,
                modifier = Modifier.padding(start = BASE_PADDING, top = 5.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}