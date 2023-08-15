package com.example.music_app.ui.screens.new_playlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.music_app.R
import com.example.music_app.ui.theme.AppTheme

private const val MAX_NAME_SIZE = 20
private const val MAX_DESCRIPTION_SIZE = 300

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun NewPlaylistScreen(
    viewModel: NewPlaylistViewModel = viewModel(factory = NewPlaylistViewModel.Factory)
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
        Column(
            horizontalAlignment = CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(AppTheme.dimens.spacing08))
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(AppTheme.dimens.spacing20)
        ) {
            Text(stringResource(R.string.name))
            Spacer(modifier = Modifier.height(AppTheme.dimens.spacing10))
            OutlinedTextField(
                value = nameFieldState.value,
                onValueChange = {newValue ->
                    if (MAX_NAME_SIZE >= newValue.length) nameFieldState.value = newValue
                    isNameValid.value = newValue.isNotBlank()
                    isNameFieldActive.value = true
                },
                isError = !isNameValid.value && isNameFieldActive.value,
                placeholder = {
                    if (!isNameFieldActive.value) {
                        Text(stringResource(R.string.name_field))
                    } else {
                        Text(stringResource(R.string.empty_field_error), color = Color.Red)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(AppTheme.dimens.spacing20))
                    .background(Color.White),
                singleLine = true,
                shape = RoundedCornerShape(AppTheme.dimens.spacing20)
            )
            Spacer(modifier = Modifier.height(AppTheme.dimens.spacing30))
            Text(stringResource(R.string.description))
            Spacer(modifier = Modifier.height(AppTheme.dimens.spacing10))
            OutlinedTextField(
                value = descriptionFieldState.value,
                onValueChange = {newValue ->
                    if (MAX_DESCRIPTION_SIZE >= newValue.length) descriptionFieldState.value = newValue
                },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(AppTheme.dimens.spacing20))
                    .background(Color.White),
                placeholder = {
                    Text(stringResource(R.string.description_field))
                },
                shape = RoundedCornerShape(AppTheme.dimens.spacing20)
            )
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            Button(
                onClick = {
                    if (isNameValid.value) {
                        viewModel.createPlaylist(
                            name = nameFieldState.value,
                            description = descriptionFieldState.value.ifEmpty { null }
                        )
                    } else {
                        isNameFieldActive.value = true
                    }
                },
                modifier = Modifier
                    .width(170.dp)
                    .height(40.dp)
            ) {
                Text(text = stringResource(R.string.create_button))
            }
        }
    }
}
