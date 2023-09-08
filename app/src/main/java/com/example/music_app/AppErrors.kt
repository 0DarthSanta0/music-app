package com.example.music_app

import com.example.music_app.constants.DATA_BASE_ERROR
import com.example.music_app.constants.EMPTY_REC_TIME
import com.example.music_app.constants.EMPTY_USER_ID
import com.example.music_app.constants.RESPONSE_ERROR
import com.example.music_app.constants.RESPONSE_ERROR_401
import com.example.music_app.constants.RESPONSE_ERROR_403
import com.example.music_app.constants.RESPONSE_ERROR_429
import com.example.music_app.constants.WRONG_TIME_INTERVAL

sealed class AppErrors(val error: String) {
    object EmptyRecTime : AppErrors(EMPTY_REC_TIME)
    object EmptyUserID : AppErrors(EMPTY_USER_ID)
    object WrongTimeInterval : AppErrors(WRONG_TIME_INTERVAL)
    object DataBaseError : AppErrors(DATA_BASE_ERROR)
    object ResponseError : AppErrors(RESPONSE_ERROR)
    object ResponseError401 : AppErrors(RESPONSE_ERROR_401)
    object ResponseError403 : AppErrors(RESPONSE_ERROR_403)
    object ResponseError429 : AppErrors(RESPONSE_ERROR_429)
}
