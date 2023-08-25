package com.example.music_app.ui.screens.core

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.music_app.R
import com.example.music_app.ui.theme.AppTheme

@Composable
fun ErrorMessage(
    errorId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(stringResource(id = errorId))
        Spacer(modifier = Modifier.width(AppTheme.dimens.spacing10))
        Button(
            onClick = { onClick() }
        ) {
            Text(stringResource(id = R.string.OK))
        }
    }
}
