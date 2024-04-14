package org.itmo.itmoevent.model.repository

import android.util.Log
import org.itmo.itmoevent.model.data.entity.User
import org.itmo.itmoevent.model.network.UserApi

class UserRepository(private val userApi: UserApi) {

    suspend fun getUserById(userId: Int): User? {
        return try {
            val  response = userApi.getUserById(userId)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (ex: Exception) {
            Log.i("retrofit", ex.stackTraceToString())
            null
        }
    }
}