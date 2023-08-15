package com.example.music_app.ui.screens.playlists

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import com.example.music_app.R
import com.example.music_app.data.models.Playlist
import com.example.music_app.ui.theme.AppTheme

@Composable
fun PlaylistItem(
    playlist: Playlist,
    modifier: Modifier = Modifier,
) {

    var isLoading by remember { mutableStateOf(true) }

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(AppTheme.dimens.spacing08))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(AppTheme.dimens.spacing10)
    ) {
        Box(
            modifier = Modifier.size(AppTheme.dimens.spacing80)
        ) {
            val painter = painterResource(id = R.drawable.default_image)
            AsyncImage(
                onError = {
                    isLoading = false
                },
                onSuccess = {
                    isLoading = false
                },
                error = painter,
                model = playlist.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(AppTheme.dimens.spacing08))
            )
            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.Blue,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        Column(
            modifier = Modifier.padding(start = AppTheme.dimens.spacing10),
        ) {
            Text(
                style = MaterialTheme.typography.titleLarge,
                text = playlist.name,
                color = MaterialTheme.colorScheme.background,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(AppTheme.dimens.spacing05))
            Text(
                text = playlist.description,
                color = MaterialTheme.colorScheme.background,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
