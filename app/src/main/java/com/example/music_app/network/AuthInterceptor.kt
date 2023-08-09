package com.example.music_app.network

import okhttp3.Interceptor
import okhttp3.Response

private const val BEARER = "Bearer"
private const val AUTHORIZATION = "Authorization"

class AuthInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(AUTHORIZATION, "$BEARER $token")
            .build()
        return chain.proceed(request)
    }
}