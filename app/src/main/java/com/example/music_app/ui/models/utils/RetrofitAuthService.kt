package com.example.music_app.ui.models.utils


import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.HeaderMap
import retrofit2.http.POST


private const val POST_URL = "api/token"


interface RetrofitAuthService {
    @FormUrlEncoded
    @POST(POST_URL)
    fun getToken(
        @HeaderMap headers: Map<String, String>,
        @Field("grant_type") grantType: String,
        @Field("code") code: String,
        @Field("redirect_uri") redirectUri: String
    ): Call<TokenResponse>
}

