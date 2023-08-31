package com.example.music_app.ui.screens.core


fun onScrollIndexChange(
    firstVisibleItemIndex: Int,
    offset: Int,
    index: Int,
    size: Int,
    onSuccessful: () -> Unit
) {
    if (firstVisibleItemIndex == (offset - index) && (size - offset) > 0) onSuccessful()
}
