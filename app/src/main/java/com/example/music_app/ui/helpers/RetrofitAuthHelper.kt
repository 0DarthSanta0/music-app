package com.example.music_app.ui.helpers

import com.example.music_app.constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitAuthHelper {
    private const val baseUrl = BASE_URL

    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}