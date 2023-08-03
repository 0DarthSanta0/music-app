package com.example.music_app.network

import com.example.music_app.data.models.PlaylistsResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

private const val BASE_URL = "https://api.spotify.com/v1/me/playlists"

private const val AUTHORIZATION = "Authorization"

interface PlaylistsService {

    @GET
    suspend fun getListOfPlaylists(
        @Header(AUTHORIZATION) authorization: String,
    ): PlaylistsResponse

    companion object PlaylistsHelper {
        private const val SPOTIFY_URL = BASE_URL
        fun getInstance(): PlaylistsService {
            return Retrofit.Builder()
                .baseUrl(SPOTIFY_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PlaylistsService::class.java)
        }
    }
}