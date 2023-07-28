package com.example.music_app.data.models

import com.google.gson.annotations.SerializedName

data class TokenRefreshResponse(
    @SerializedName("access_token")
    val accessToken: String?,
    @SerializedName("token_type")
    val tokenType: String?,
    @SerializedName("scope")
    val scope: String?,
    @SerializedName("expires_in")
    val expiresIn: Int?,
)