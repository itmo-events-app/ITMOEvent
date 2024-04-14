package org.itmo.itmoevent.model.repository

import android.util.Log
import org.itmo.itmoevent.model.data.entity.Notification
import org.itmo.itmoevent.model.network.NotificationApi

class NotificationRepository(private val notificationApi: NotificationApi) {

    suspend fun getUserNotificationsByUserId(userId: Int): List<Notification>? {
        return try {
            val response = notificationApi.getUserNotificationsByUserId(userId)
            if (response.isSuccessful)
                response.body()
            else
                emptyList()
        } catch (ex: Exception) {
            Log.i("retrofit", ex.stackTraceToString())
            emptyList()
        }
    }
}