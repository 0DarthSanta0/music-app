package com.example.music_app.network

import com.example.music_app.data.data_store.DataStoreManagerImpl
import com.example.music_app.data.models.PlaylistsResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.spotify.com/"
private const val GET_URL = "v1/me/playlists"

private const val OFFSET = "offset"
private const val LIMIT = "limit"

private const val TOKEN_KEY = "access_token"

interface PlaylistsService {

    @GET(GET_URL)
    suspend fun getListOfPlaylists(
        @Query(OFFSET) offset: String,
        @Query(LIMIT) limit: String
    ): PlaylistsResponse

    companion object PlaylistsHelper {
        suspend fun getInstance(): PlaylistsService {
            val token = DataStoreManagerImpl.getString(TOKEN_KEY)
            val authInterceptor = AuthInterceptor(token)
            val client = OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
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