package com.example.music_app.data

import kotlinx.coroutines.flow.Flow

private const val KEY = "access_token"

class LoginStoreManager(
    private val loginStore: LoginStore
) {

    suspend fun saveToken(token: String) {
        loginStore.saveString(token, KEY)
    }

    fun getToken(): Flow<String>? {
        return loginStore.getString(KEY)
    }

}