package com.example.music_app.ui.screens.playlists

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.music_app.data.models.Playlist
import com.example.music_app.ui.theme.AppTheme

@Composable
fun PlaylistsField(
    lazyListState: LazyListState,
    playlists: List<Playlist>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        state = lazyListState,
        contentPadding = PaddingValues(AppTheme.dimens.spacing10),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.spacing10),
        modifier = modifier
    ) {
        if (playlists.isNotEmpty()) {
            items(playlists.size) { index ->
                val playlist = playlists[index]
                PlaylistItem(
                    playlist = playlist,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(AppTheme.dimens.spacing100)
                        .clip(RoundedCornerShape(AppTheme.dimens.spacing08))
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .padding(AppTheme.dimens.spacing10)
                )
            }
        }
    }
}