package com.example.music_app.data.data_store


interface DataStoreManager {

    suspend fun getString(key: String): String

    suspend fun saveString(token: String, key: String)

}