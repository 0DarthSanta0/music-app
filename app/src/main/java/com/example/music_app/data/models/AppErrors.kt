package com.example.music_app.data.models

private const val EMPTY_REC_TIME = "Empty receiving time"
private const val WRONG_TIME_INTERVAL = "Wrong time interval"
private const val DATA_BASE_ERROR = "Data base error"


sealed class AppErrors(val error: String) {
    object RecTime: AppErrors(EMPTY_REC_TIME)
    object WrongTimeInterval: AppErrors(WRONG_TIME_INTERVAL)
    object DataBaseError: AppErrors(DATA_BASE_ERROR)
}