package com.example.music_app.ui.screens.playlists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.music_app.data.models.Playlist
import com.example.music_app.ui.theme.BASE_PADDING
import com.example.music_app.ui.theme.Background2Light
import com.example.music_app.ui.theme.IMAGE_SIZE
import com.example.music_app.ui.theme.SHAPE_SIZE
import com.example.music_app.ui.theme.White

private const val DEFAULT_IMAGE =
    "https://play-lh.googleusercontent.com/qZpgL9UUqJ4vqDPV8nRYACCt5DUFrMZxJHwozHNsckNBa3x3nWcliksooqaw_DEzvQ=w240-h480-rw"
private const val DEFAULT_TEXT = "Something went wrong"

@Composable
fun PlaylistItem(playlist: Playlist?) {

    var isLoading by remember { mutableStateOf(true) }

    Surface(
        color = Background2Light,
        shape = RoundedCornerShape(SHAPE_SIZE),
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .padding(BASE_PADDING)
        ) {
            val image = if (!playlist?.images.isNullOrEmpty()) playlist?.images?.get(0)?.url
            else DEFAULT_IMAGE
            AsyncImage(
                onSuccess = {
                    isLoading = false
                },
                model = image,
                contentDescription = null,
                modifier = Modifier
                    .height(IMAGE_SIZE)
                    .width(IMAGE_SIZE)
            )
            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.Blue,
                    modifier = Modifier
                        .height(IMAGE_SIZE)
                        .width(IMAGE_SIZE)
                        .offset((-80).dp)
                )
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
}