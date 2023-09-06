package com.example.music_app.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.music_app.R
import com.example.music_app.ui.screens.core.components.ButtonField
import com.example.music_app.ui.screens.core.components.ErrorSnackbar
import com.example.music_app.ui.screens.core.components.ScrollIndexChange
import com.example.music_app.ui.screens.playlists.PlaylistsField
import com.example.music_app.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = viewModel(factory = SearchViewModel.Factory),
    onBackToPlaylists: () -> Unit
) {
    val error by viewModel.error.collectAsStateWithLifecycle()
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
    val playlists by viewModel.playlists.collectAsStateWithLifecycle()
    val isSearching by viewModel.isSearching.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val lazyListState = rememberLazyListState()

    val snackbarHostState = remember { SnackbarHostState() }

    ScrollIndexChange(
        lazyListState = lazyListState,
        onScrollIndexChange = viewModel::onUIScrollIndexChange
    )

    ErrorSnackbar(
        error = error,
        snackbarHostState = snackbarHostState,
        onClick = { viewModel.onError() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(AppTheme.dimens.spacing10),
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.spacing10),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppTheme.dimens.spacing80)
                    .clip(RoundedCornerShape(AppTheme.dimens.spacing08))
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(AppTheme.dimens.spacing15)
            ) {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = viewModel::onSearchTextChange,
                    placeholder = { Text(stringResource(R.string.search)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(AppTheme.dimens.spacing20))
                        .background(Color.White),
                    singleLine = true,
                    shape = RoundedCornerShape(AppTheme.dimens.spacing20),
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
                )
            }
            PlaylistsField(
                lazyListState = lazyListState,
                playlists = playlists,
                isLoading = isLoading,
                isFirstLoading = isSearching,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            ButtonField(
                text = stringResource(R.string.to_playlists),
                onClick = { onBackToPlaylists() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppTheme.dimens.spacing80)
            )
        }
    }
}
