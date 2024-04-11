package org.itmo.itmoevent.view.auth.service

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "auth")
object UserDatastore {
    val TOKEN_KEY = stringPreferencesKey("token")
    val EMAIL_KEY = stringPreferencesKey("email")
    val PASSWORD_KEY = stringPreferencesKey("password")

    suspend fun saveToken(context: Context, token: String?) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token ?: ""
        }
    }

    suspend fun saveEmail(context: Context, email: String?) {
        context.dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = email ?: ""
        }
    }

    suspend fun savePassword(context: Context, password: String?) {
        context.dataStore.edit { preferences ->
            preferences[PASSWORD_KEY] = password ?: ""
        }
    }

    fun tokenFlow(context: Context): Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[TOKEN_KEY]
    }

    fun emailFlow(context: Context): Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[EMAIL_KEY]
    }

    fun passwordFlow(context: Context): Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[PASSWORD_KEY]
    }

}