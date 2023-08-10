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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.music_app.data.models.Playlist
import com.example.music_app.ui.theme.BASE_PADDING
import com.example.music_app.ui.theme.Background2Light
import com.example.music_app.ui.theme.PLAYLIST_ITEM_SIZE
import com.example.music_app.ui.theme.SHAPE_SIZE

@Composable
fun PlaylistsField(
    lazyListState: LazyListState,
    playlists: List<Playlist>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        state = lazyListState,
        contentPadding = PaddingValues(BASE_PADDING),
        verticalArrangement = Arrangement.spacedBy(BASE_PADDING),
        modifier = modifier
    ) {
        if (playlists.isNotEmpty()) {
            items(playlists.size) { index ->
                val playlist = playlists[index]
                PlaylistItem(
                    playlist = playlist,
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
}