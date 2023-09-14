package com.example.music_app.ui.screens.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.music_app.ui.theme.AppTheme

@Composable
fun ButtonField(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(RoundedCornerShape(AppTheme.dimens.spacing08))
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Button(
            onClick = onClick,
            enabled = isEnabled,
            modifier = Modifier
                .width(AppTheme.dimens.spacing170)
                .height(AppTheme.dimens.spacing40)
        ) {
            Text(text = text)
        }
    }
}
