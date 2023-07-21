package com.example.music_app.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private const val NAME = "LoginStore"


class LoginStore(private val context: Context?) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(NAME)
    }

    fun getString(key: String): Flow<String>? = context?.dataStore?.data?.map { preferences ->
        preferences[stringPreferencesKey(key)] ?: ""
    }

    suspend fun saveString(token: String, key: String) {
        context?.dataStore?.edit { preferences ->
            preferences[stringPreferencesKey(key)] = token
        }
    }
}