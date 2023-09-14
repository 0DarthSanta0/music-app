package com.example.music_app.ui.screens.core.components

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

@Composable
fun ScrollIndexChange(
    lazyListState: LazyListState,
    onScrollIndexChange: (Int) -> Unit
) {
    val index by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex
        }
    }

    LaunchedEffect(index) {
        onScrollIndexChange(index)
    }
}
