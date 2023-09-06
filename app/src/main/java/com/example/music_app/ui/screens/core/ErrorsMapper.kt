package com.example.music_app.ui.screens.core

import androidx.annotation.StringRes
import com.example.music_app.AppErrors
import com.example.music_app.R
import com.example.music_app.constants.DATA_BASE_ERROR
import com.example.music_app.constants.EMPTY_REC_TIME
import com.example.music_app.constants.EMPTY_USER_ID
import com.example.music_app.constants.RESPONSE_ERROR
import com.example.music_app.constants.WRONG_TIME_INTERVAL


@StringRes
fun AppErrors.toUIStringRes(): Int = when (error) {
    EMPTY_REC_TIME -> R.string.receiving_time_error
    EMPTY_USER_ID -> R.string.user_id_error
    WRONG_TIME_INTERVAL -> R.string.time_interval_error
    DATA_BASE_ERROR -> R.string.data_base_error
    RESPONSE_ERROR -> R.string.response_error
    else -> R.string.error
}
