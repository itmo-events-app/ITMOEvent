package org.itmo.itmoevent.model.network

import org.itmo.itmoevent.model.data.entity.Notification
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NotificationApi {

    @GET("notifications/")
    suspend fun getUserNotificationsByUserId(@Query("userId") userId: Int) : Response<List<Notification>>

}