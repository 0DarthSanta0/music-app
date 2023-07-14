package com.example.music_app.ui.models.utils

data class TokenResponse (
    val access_token: String? = null,
    val token_type: String? = null,
    val scope: String? = null,
    val expires_in: Int? = null,
    val refresh_token: String? = null
)