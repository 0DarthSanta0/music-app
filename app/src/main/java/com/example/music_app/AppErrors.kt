package com.example.music_app

sealed class AppErrors(val errorId: Int) {
    object EmptyRecTime : AppErrors(R.string.receiving_time_error)
    object EmptyUserID : AppErrors(R.string.user_id_error)
    object WrongTimeInterval : AppErrors(R.string.time_interval_error)
    object DataBaseError : AppErrors(R.string.data_base_error)
    object ResponseError : AppErrors(R.string.response_error)
}
