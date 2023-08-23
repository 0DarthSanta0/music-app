package com.example.music_app.ui.screens.new_playlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.music_app.R
import com.example.music_app.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewPlaylistInputsField(
    nameFieldState: String,
    descriptionFieldState: String,
    isNameValid: Boolean,
    isNameFieldActive: Boolean,
    modifier: Modifier = Modifier,
    onNameValueChange: (String) -> Unit,
    onDescriptionValueChange: (String) -> Unit
) {
    Column(
        horizontalAlignment = CenterHorizontally,
        modifier = modifier
            .clip(RoundedCornerShape(AppTheme.dimens.spacing08))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(AppTheme.dimens.spacing20)
    ) {
        Text(
            text = stringResource(R.string.name),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(AppTheme.dimens.spacing10))
        OutlinedTextField(
            value = nameFieldState,
            onValueChange = onNameValueChange,
            isError = !isNameValid && isNameFieldActive,
            placeholder = {
                if (!isNameFieldActive) {
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
        Text(
            text = stringResource(R.string.description),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(AppTheme.dimens.spacing10))
        OutlinedTextField(
            value = descriptionFieldState,
            onValueChange = onDescriptionValueChange,
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
}
