package com.ger.memo

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreData(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("storeData")
        val SOUND = booleanPreferencesKey("store_Data")
    }

    val getSoundPref: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[SOUND] ?: true
        }

    suspend fun saveSoundPref(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[SOUND] = enabled
        }
    }

}