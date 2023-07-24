package com.example.music_app.data.data_store

import kotlinx.coroutines.flow.Flow


interface DataStoreManager {

    fun getString(key: String): Flow<String>

    suspend fun saveString(token: String, key: String)

}