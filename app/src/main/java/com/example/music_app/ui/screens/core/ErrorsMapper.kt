package com.example.music_app.ui.screens.core

import androidx.annotation.StringRes
import com.example.music_app.AppErrors
import com.example.music_app.R


@StringRes
fun AppErrors.toUIStringRes(): Int = when (this) {
    AppErrors.EmptyRecTime -> R.string.receiving_time_error
    AppErrors.EmptyUserID -> R.string.user_id_error
    AppErrors.WrongTimeInterval -> R.string.time_interval_error
    AppErrors.DataBaseError -> R.string.data_base_error
    AppErrors.ResponseError, AppErrors.ResponseError401, AppErrors.ResponseError403, AppErrors.ResponseError429 -> R.string.response_error
}
