package com.example.music_app.network

import com.example.music_app.data.models.PlaylistsResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

private const val BASE_URL = "https://api.spotify.com/"
private const val GET_URL = "v1/me/playlists"

private const val AUTHORIZATION = "Authorization"
private const val OFFSET = "offset"
private const val LIMIT = "limit"

interface PlaylistsService {

    @GET(GET_URL)
    suspend fun getListOfPlaylists(
        @Header(AUTHORIZATION) authorization: String,
        @Query(OFFSET) offset: String,
        @Query(LIMIT) limit: String
    ): PlaylistsResponse

    companion object PlaylistsHelper {
        fun getInstance(): PlaylistsService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PlaylistsService::class.java)
        }
    }
}