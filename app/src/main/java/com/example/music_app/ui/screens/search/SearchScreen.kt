package com.example.music_app.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.music_app.R
import com.example.music_app.data.models.Playlist
import com.example.music_app.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = viewModel(factory = SearchViewModel.Factory)
) {
    val searchText by viewModel.searchText.collectAsState()
    val playlists: List<Playlist> by viewModel.playlists.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(AppTheme.dimens.spacing10),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.spacing10),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            color = MaterialTheme.colorScheme.primaryContainer,
            shape = RoundedCornerShape(AppTheme.dimens.spacing08),
            modifier = Modifier
                .fillMaxWidth()
                .height(AppTheme.dimens.spacing80)
        ) {
            TextField(value = searchText, onValueChange = viewModel::onSearchTextChange)
        }
        if (isSearching) {

        } else {
            LazyColumn {
                items(items = playlists) { playlists ->
                    Text(text = playlists.name)
                }
            }
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .height(AppTheme.dimens.spacing80)
                .clip(RoundedCornerShape(AppTheme.dimens.spacing08))
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            Button(
                onClick = { },
                modifier = Modifier
                    .width(AppTheme.dimens.spacing170)
                    .height(AppTheme.dimens.spacing40)
            ) {
                Text(text = stringResource(R.string.new_playlist))
            }
        }
    }
}