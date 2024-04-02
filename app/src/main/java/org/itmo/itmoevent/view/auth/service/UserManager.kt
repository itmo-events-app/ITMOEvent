package org.itmo.itmoevent.view.auth.service

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.itmo.itmoevent.view.auth.service.models.UserData

val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_data")
class UserManager(context: Context) {
    private val dataStore = context.userDataStore
    private val gson = Gson()

    suspend fun saveUserData(userData: UserData) {
        val userDataJson = gson.toJson(userData)
        dataStore.edit { preferences ->
            preferences[USER_DATA_KEY] = userDataJson
        }
    }

    val userData: Flow<UserData?> = dataStore.data.map { preferences ->
        val userDataJson = preferences[USER_DATA_KEY] ?: return@map null
        gson.fromJson(userDataJson, UserData::class.java)
    }

    companion object {
        private val USER_DATA_KEY = stringPreferencesKey("user_data")
    }
}