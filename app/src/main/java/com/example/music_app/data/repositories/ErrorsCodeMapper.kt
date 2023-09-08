package com.example.music_app.data.repositories

import com.example.music_app.AppErrors
import com.example.music_app.data.models.ErrorResponse

const val CODE_401 = 401
const val CODE_403 = 403
const val CODE_429 = 429

fun ErrorResponse.toAppErrors(): AppErrors = when (status) {
    CODE_401 -> AppErrors.ResponseError401
    CODE_403 -> AppErrors.ResponseError403
    CODE_429 -> AppErrors.ResponseError429
    else -> AppErrors.ResponseError
}
