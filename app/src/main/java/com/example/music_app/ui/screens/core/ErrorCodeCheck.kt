package com.example.music_app.ui.screens.core

import com.example.music_app.AppErrors

fun reLoginCheck(
    error: AppErrors?,
    onTrue: () -> Unit,
    onFalse: () -> Unit
) {
    if (error == AppErrors.ResponseError401 || error == AppErrors.ResponseError403) {
        onTrue()
    } else {
        onFalse()
    }
}
