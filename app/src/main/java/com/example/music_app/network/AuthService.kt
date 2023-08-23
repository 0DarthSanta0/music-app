package com.example.music_app.network

import com.example.music_app.constants.BASE_URL
import com.example.music_app.data.models.TokenRefreshResponse
import com.example.music_app.data.models.TokenResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.HeaderMap
import retrofit2.http.POST

private const val POST_URL = "api/token"

private const val GRANT_TYPE = "grant_type"
private const val CODE = "code"
private const val REDIRECT_URI = "redirect_uri"
private const val REFRESH_TOKEN = "refresh_token"

interface AuthService {
    @FormUrlEncoded
    @POST(POST_URL)
    suspend fun getToken(
        @HeaderMap headers: Map<String, String>,
        @Field(GRANT_TYPE) grantType: String,
        @Field(CODE) code: String,
        @Field(REDIRECT_URI) redirectUri: String
    ): TokenResponse

    @FormUrlEncoded
    @POST(POST_URL)
    suspend fun getRefreshToken(
        @HeaderMap headers: Map<String, String>,
        @Field(GRANT_TYPE) grantType: String,
        @Field(REFRESH_TOKEN) refreshToken: String
    ): TokenRefreshResponse

    companion object AuthHelper {
        private const val SPOTIFY_URL = BASE_URL
        fun getInstance(): AuthService {
            return Retrofit.Builder()
                .baseUrl(SPOTIFY_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AuthService::class.java)
        }
    }
}
