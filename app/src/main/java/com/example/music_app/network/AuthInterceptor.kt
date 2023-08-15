package com.example.music_app.network

import com.example.music_app.data.data_store.DataStoreManagerImpl
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

private const val BEARER = "Bearer"
private const val AUTHORIZATION = "Authorization"

private const val TOKEN_KEY = "access_token"

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { DataStoreManagerImpl.getString(TOKEN_KEY) }
        val request = chain.request().newBuilder()
            .addHeader(AUTHORIZATION, "$BEARER $token")
            .build()
        return chain.proceed(request)
    }
}