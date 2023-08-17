package com.example.music_app.network

import com.example.music_app.data.models.PlaylistsResponse
import com.example.music_app.data.models.SearchPlaylistsResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.spotify.com/"
private const val GET_URL = "v1/me/playlists"
private const val GET_SEARCH_URL = "v1/search"

private const val OFFSET = "offset"
private const val LIMIT = "limit"
private const val Q = "q"
private const val TYPE = "type"

interface PlaylistsService {

    @GET(GET_URL)
    suspend fun getListOfPlaylists(
        @Query(OFFSET) offset: String,
        @Query(LIMIT) limit: String
    ): PlaylistsResponse

    @GET(GET_SEARCH_URL)
    suspend fun getPlaylistsForSearch(
        @Query(Q) q: String,
        @Query(TYPE) type: String,
        @Query(OFFSET) offset: String,
        @Query(LIMIT) limit: String
    ): SearchPlaylistsResponse

    companion object PlaylistsHelper {
        fun getInstance(): PlaylistsService {
            val client = OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor())
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(PlaylistsService::class.java)
        }
    }
}