package org.itmo.itmoevent.network.repository

import org.itmo.itmoevent.network.api.NotificationControllerApi
import org.itmo.itmoevent.network.model.NotificationResponse
import org.itmo.itmoevent.network.model.NotificationPageResponse
import org.itmo.itmoevent.network.util.apiRequestFlow
import retrofit2.http.Path
import retrofit2.http.Query

class NotificationRepository(private val notificationApi: NotificationControllerApi) {

    fun getNotSeenCountNotification() = apiRequestFlow {
        notificationApi.getNotSeenCountNotification()
    }

    fun getNotifications(page: Int = 0, size: Int = 25) = apiRequestFlow {
        notificationApi.getNotifications(page, size)
    }

    fun setAllAsSeenNotifications(page: Int = 0, size: Int = 25) = apiRequestFlow {
        notificationApi.setAllAsSeenNotifications(page, size)
    }

    fun setOneAsSeenNotification(notificationId: Int) = apiRequestFlow {
        notificationApi.setOneAsSeenNotification(notificationId)
    }
}
