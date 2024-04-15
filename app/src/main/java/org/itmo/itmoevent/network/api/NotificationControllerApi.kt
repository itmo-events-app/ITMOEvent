package org.itmo.itmoevent.network.api

import org.itmo.itmoevent.network.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import org.itmo.itmoevent.network.model.NotificationPageResponse
import org.itmo.itmoevent.network.model.NotificationResponse

interface NotificationControllerApi {
    /**
     * Получение списка всех уведомлений
     * 
     * Responses:
     *  - 200: OK
     *
     * @param page Номер страницы, с которой начать показ уведомлений
     * @param size Число уведомлений на странице
     * @return [NotificationPageResponse]
     */
    @GET("api/notifications")
    suspend fun getNotifications(@Query("page") page: kotlin.Int, @Query("size") size: kotlin.Int): Response<NotificationPageResponse>

    /**
     * Установка статуса прочитано у всех уведомлений
     * 
     * Responses:
     *  - 200: OK
     *
     * @param page Номер страницы, с которой начать показ уведомлений
     * @param size Число уведомлений на странице
     * @return [kotlin.collections.List<NotificationResponse>]
     */
    @PUT("api/notifications")
    suspend fun setAllAsSeenNotifications(@Query("page") page: kotlin.Int, @Query("size") size: kotlin.Int): Response<kotlin.collections.List<NotificationResponse>>

    /**
     * Установка статуса прочитано у одного уведомления
     * 
     * Responses:
     *  - 200: OK
     *
     * @param notificationId ID уведомления
     * @return [NotificationResponse]
     */
    @PUT("api/notifications/{notificationId}")
    suspend fun setOneAsSeenNotification(@Path("notificationId") notificationId: kotlin.Int): Response<NotificationResponse>

}
