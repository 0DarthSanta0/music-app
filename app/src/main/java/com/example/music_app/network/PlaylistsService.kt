package com.example.music_app.network

import com.example.music_app.data.models.CurrentUserResponse
import com.example.music_app.data.models.PlaylistsResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

private const val USER_ID = "user_id"
private const val OFFSET = "offset"
private const val LIMIT = "limit"

private const val BASE_URL = "https://api.spotify.com/"
private const val GET_PLAYLISTS_URL = "v1/me/playlists"
private const val GET_USER_URL = "v1/me"
private const val POST_PLAYLIST_URL = "v1/users/{${USER_ID}}/playlists"


data class BodyName(
    val name: String
)

interface PlaylistsService {

    @GET(GET_PLAYLISTS_URL)
    suspend fun getListOfPlaylists(
        @Query(OFFSET) offset: String,
        @Query(LIMIT) limit: String
    ): PlaylistsResponse

    @GET(GET_USER_URL)
    suspend fun getCurrentUser(): CurrentUserResponse

    @Headers("Content-Type: application/json")
    @POST(POST_PLAYLIST_URL)
    suspend fun createPlaylist(
        @Path(USER_ID) user: String,
        @Body body: BodyName
    )

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