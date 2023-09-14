package com.example.music_app.ui.screens.core

import androidx.annotation.StringRes
import com.example.music_app.AppErrors
import com.example.music_app.AppErrors.DataBaseError
import com.example.music_app.AppErrors.EmptyRecTime
import com.example.music_app.AppErrors.EmptyUserID
import com.example.music_app.AppErrors.ResponseError
import com.example.music_app.AppErrors.ResponseError401
import com.example.music_app.AppErrors.ResponseError403
import com.example.music_app.AppErrors.ResponseError429
import com.example.music_app.AppErrors.WrongTimeInterval
import com.example.music_app.R


@StringRes
fun AppErrors.toUIStringRes(): Int = when (this) {
    EmptyRecTime -> R.string.receiving_time_error
    EmptyUserID -> R.string.user_id_error
    WrongTimeInterval -> R.string.time_interval_error
    DataBaseError -> R.string.data_base_error
    ResponseError, ResponseError401, ResponseError403, ResponseError429 -> R.string.response_error
}
