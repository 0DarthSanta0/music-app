package com.example.music_app.data.repositories

import com.example.music_app.AppErrors
import com.example.music_app.data.models.ErrorResponse
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result

inline fun <T> catchAPIErrors(
    isSuccess: Boolean,
    error: ErrorResponse?,
    onSuccess: () -> Result<T, AppErrors>
): Result<T, AppErrors> = when {
    isSuccess -> onSuccess()
    error != null -> Err(error.toAppErrors())
    else -> Err(AppErrors.ResponseError)
}
