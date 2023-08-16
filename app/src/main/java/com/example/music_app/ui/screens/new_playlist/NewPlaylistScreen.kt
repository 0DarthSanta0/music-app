package com.example.music_app.ui.screens.new_playlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.music_app.R
import com.example.music_app.ui.screens.core.ButtonField
import com.example.music_app.ui.theme.AppTheme


@Composable
fun NewPlaylistScreen(
    viewModel: NewPlaylistViewModel = viewModel(factory = NewPlaylistViewModel.Factory),
    onCreatePlaylistSuccess: () -> Unit
) {
    val nameFieldState = remember { mutableStateOf("") }
    val descriptionFieldState = remember { mutableStateOf("") }

    val isNameValid = remember { mutableStateOf(false) }
    val isNameFieldActive = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(AppTheme.dimens.spacing10),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.spacing10),
        horizontalAlignment = CenterHorizontally
    ) {
        Surface(
            color = MaterialTheme.colorScheme.primaryContainer,
            shape = RoundedCornerShape(AppTheme.dimens.spacing08),
            modifier = Modifier
                .fillMaxWidth()
                .height(AppTheme.dimens.spacing80)
        ) {
        }
        NewPlaylistInputsField(
            nameFieldState = nameFieldState,
            descriptionFieldState = descriptionFieldState,
            isNameValid = isNameValid,
            isNameFieldActive = isNameFieldActive,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        ButtonField(
            text = stringResource(R.string.create_button),
            onClick = {
                if (isNameValid.value) {
                    viewModel.createPlaylist(
                        name = nameFieldState.value,
                        description = descriptionFieldState.value.ifEmpty { null },
                        onCreatePlaylistSuccess = onCreatePlaylistSuccess
                    )
                } else {
                    isNameFieldActive.value = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(AppTheme.dimens.spacing80)
        )
    }
}
