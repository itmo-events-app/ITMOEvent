package org.itmo.itmoevent.network.repository

import org.itmo.itmoevent.network.api.NotificationControllerApi
import org.itmo.itmoevent.network.util.apiRequestFlow
import retrofit2.http.Path
import retrofit2.http.Query

class NotificationRepository(private val notificationApi: NotificationControllerApi) {

    fun getAllNotifications(@Query("page") page: Int, @Query("size") size: Int) = apiRequestFlow {
        notificationApi.getAllNotifications(page, size)
    }

    fun setAllAsSeenNotifications(@Query("page") page: Int, @Query("size") size: Int) =
        apiRequestFlow {
            notificationApi.setAllAsSeenNotifications(page, size)
        }

    fun setOneAsSeenNotification(@Path("notificationId") notificationId: Int) = apiRequestFlow {
        notificationApi.setOneAsSeenNotification(notificationId)
    }
}