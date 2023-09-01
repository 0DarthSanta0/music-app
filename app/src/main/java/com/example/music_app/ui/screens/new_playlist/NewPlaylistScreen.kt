package com.example.music_app.ui.screens.new_playlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.music_app.R
import com.example.music_app.ui.screens.core.ButtonField
import com.example.music_app.ui.screens.core.ErrorLaunchedEffect
import com.example.music_app.ui.screens.core.ErrorSnackbar
import com.example.music_app.ui.theme.AppTheme


@Composable
fun NewPlaylistScreen(
    viewModel: NewPlaylistViewModel = viewModel(factory = NewPlaylistViewModel.Factory),
    onCreatePlaylistSuccess: () -> Unit
) {

    val error by viewModel.error.collectAsStateWithLifecycle()
    val nameFieldState by viewModel.nameFieldState.collectAsStateWithLifecycle()
    val descriptionFieldState by viewModel.descriptionFieldState.collectAsStateWithLifecycle()
    val isNameValid by viewModel.isNameValid.collectAsStateWithLifecycle()
    val isFormValid by viewModel.isFormValid.collectAsStateWithLifecycle()
    val isNameFieldActive by viewModel.isNameFieldActive.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    ErrorLaunchedEffect(
        error = error,
        snackbarHostState = snackbarHostState
    )

    ErrorSnackbar(
        errorId = error?.errorId ?: R.string.error,
        snackbarHostState = snackbarHostState,
        onClick = { viewModel.onError() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(AppTheme.dimens.spacing10),
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.spacing10),
            horizontalAlignment = CenterHorizontally
        ) {
            NewPlaylistInputsField(
                nameFieldState = nameFieldState,
                descriptionFieldState = descriptionFieldState,
                isNameValid = isNameValid,
                isNameFieldActive = isNameFieldActive,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                onNameValueChange = viewModel::onNameValueChange,
                onDescriptionValueChange = viewModel::onDescriptionValueChange
            )
            ButtonField(
                text = stringResource(R.string.create_button),
                onClick = {
                    viewModel.onCreatePlaylist(onCreatePlaylistSuccess)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppTheme.dimens.spacing80),
                isEnabled = isFormValid
            )
        }
    }
}
