package com.example.music_app.ui.screens.playlists

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.example.music_app.data.models.Playlist
import com.example.music_app.ui.theme.AppTheme

@Composable
fun PlaylistsField(
    lazyListState: LazyListState,
    playlists: List<Playlist>,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(RoundedCornerShape(AppTheme.dimens.spacing08))
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Color.Blue,
                modifier = Modifier
                    .size(AppTheme.dimens.spacing80)
            )
        } else {
            LazyColumn(
                state = lazyListState,
                contentPadding = PaddingValues(AppTheme.dimens.spacing10),
                verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.spacing10),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(playlists) { playlist ->
                    PlaylistItem(
                        playlist = playlist,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(AppTheme.dimens.spacing100)
                    )
                }
            }
        }
    }
}