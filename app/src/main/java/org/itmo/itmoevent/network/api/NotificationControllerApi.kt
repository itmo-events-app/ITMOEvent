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
     * Получение количества уведомлений у пользователя
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [kotlin.Long]
     */
    @GET("api/notifications/notSeenCount")
    suspend fun getNotSeenCountNotification(): Response<kotlin.Long>

    /**
     * Получение списка всех уведомлений
     * 
     * Responses:
     *  - 200: OK
     *
     * @param page Номер страницы, с которой начать показ уведомлений (optional, default to 0)
     * @param size Число уведомлений на странице (optional, default to 25)
     * @return [NotificationPageResponse]
     */
    @GET("api/notifications")
    suspend fun getNotifications(@Query("page") page: kotlin.Int? = 0, @Query("size") size: kotlin.Int? = 25): Response<NotificationPageResponse>

    /**
     * Установка статуса прочитано у всех уведомлений
     * 
     * Responses:
     *  - 200: OK
     *
     * @param page Номер страницы, с которой начать показ уведомлений (optional, default to 0)
     * @param size Число уведомлений на странице (optional, default to 25)
     * @return [kotlin.collections.List<NotificationResponse>]
     */
    @PUT("api/notifications")
    suspend fun setAllAsSeenNotifications(@Query("page") page: kotlin.Int? = 0, @Query("size") size: kotlin.Int? = 25): Response<kotlin.collections.List<NotificationResponse>>

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
