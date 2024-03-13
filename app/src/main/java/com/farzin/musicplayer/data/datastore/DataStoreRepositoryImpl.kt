package com.farzin.musicplayer.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.farzin.musicplayer.utils.Constants.DATASTORE_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

val Context.dataStore by preferencesDataStore(DATASTORE_NAME)

class DataStoreRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : DataStoreRepository {
    override suspend fun getInt(key: String): Int? {
        return try {
            val preferenceKey = intPreferencesKey(key)
            val preferences = context.dataStore.data.first()
            preferences[preferenceKey]
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun putInt(key: String, value: Int) {
        val preferenceKey = intPreferencesKey(key)
        context.dataStore.edit {
            it[preferenceKey] = value
        }
    }
}