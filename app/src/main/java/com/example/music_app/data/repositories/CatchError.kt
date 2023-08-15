package com.example.music_app.data.repositories

import com.example.music_app.AppErrors
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import java.io.IOException


inline infix fun <T, V> T.catchErrors(block: T.() -> V): Result<V, AppErrors> {
    return try {
        Ok(block())
    } catch (e: IOException) {
        Err(AppErrors.DataBaseError)
    }
}