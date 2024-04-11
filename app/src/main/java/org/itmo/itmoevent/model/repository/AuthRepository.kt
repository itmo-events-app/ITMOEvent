package org.itmo.itmoevent.model.repository

import android.util.Log
import org.itmo.itmoevent.network.SessionManager
import org.itmo.itmoevent.network.api.AuthControllerApi
import org.itmo.itmoevent.network.model.LoginRequest

class AuthRepository(private val authApi: AuthControllerApi, private val sessionManager: SessionManager?) {

    suspend fun login(request: LoginRequest) {
        try {
            val  response = authApi.login(request)
            if (response.isSuccessful) {
//                sessionManager.token = response.body()
            }
        } catch (ex: Exception) {
            Log.i("retrofit", ex.stackTraceToString())
            null
        }

    }
}