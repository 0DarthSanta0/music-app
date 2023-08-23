package com.example.music_app

private const val EMPTY_REC_TIME = "Empty receiving time"
private const val EMPTY_USER_ID = "Empty user id"
private const val WRONG_TIME_INTERVAL = "Wrong time interval"
private const val DATA_BASE_ERROR = "Data base error"
private const val RESPONSE_ERROR = "Response error"


sealed class AppErrors(val error: String) {
    object EmptyRecTime: AppErrors(EMPTY_REC_TIME)
    object EmptyUserID: AppErrors(EMPTY_USER_ID)
    object WrongTimeInterval: AppErrors(WRONG_TIME_INTERVAL)
    object DataBaseError: AppErrors(DATA_BASE_ERROR)
    object ResponseError: AppErrors(RESPONSE_ERROR)
}
