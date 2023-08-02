package com.example.music_app.data.data_store

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.music_app.MusicAppApplication
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map


private const val NAME = "LoginStore"


@SuppressLint("StaticFieldLeak")
object DataStoreManagerImpl : DataStoreManager {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(NAME)

    private val context: Context = MusicAppApplication.applicationContext()

    override suspend fun getString(key: String): String =
        context.dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(key)] ?: ""
        }.firstOrNull() ?: ""

    override suspend fun saveString(string: String, key: String) {
        context.dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = string
        }
    }
}