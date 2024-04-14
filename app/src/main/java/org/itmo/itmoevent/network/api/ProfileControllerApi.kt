package org.itmo.itmoevent.network.api

import org.itmo.itmoevent.network.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import org.itmo.itmoevent.network.model.NotificationSettingsRequest
import org.itmo.itmoevent.network.model.PrivilegeResponse
import org.itmo.itmoevent.network.model.ProfileResponse
import org.itmo.itmoevent.network.model.UserChangeLoginRequest
import org.itmo.itmoevent.network.model.UserChangeNameRequest
import org.itmo.itmoevent.network.model.UserChangePasswordRequest

interface ProfileControllerApi {
    /**
     * Смена логина пользователя
     * 
     * Responses:
     *  - 200: OK
     *
     * @param userChangeLoginRequest 
     * @return [Unit]
     */
    @PUT("api/profile/login")
    suspend fun changeLogin(@Body userChangeLoginRequest: UserChangeLoginRequest): Response<Unit>

    /**
     * Смена имени пользователя
     * 
     * Responses:
     *  - 200: OK
     *
     * @param userChangeNameRequest 
     * @return [Unit]
     */
    @PUT("api/profile/name")
    suspend fun changeName(@Body userChangeNameRequest: UserChangeNameRequest): Response<Unit>

    /**
     * Смена пароля пользователя
     * 
     * Responses:
     *  - 200: OK
     *
     * @param userChangePasswordRequest 
     * @return [Unit]
     */
    @PUT("api/profile/password")
    suspend fun changePassword(@Body userChangePasswordRequest: UserChangePasswordRequest): Response<Unit>

    /**
     * Получение списка всех привилегий пользователя в данном мероприятии
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id ID мероприятия
     * @return [kotlin.collections.List<PrivilegeResponse>]
     */
    @GET("api/profile/event-privileges/{id}")
    suspend fun getUserEventPrivileges(@Path("id") id: kotlin.Int): Response<kotlin.collections.List<PrivilegeResponse>>

    /**
     * Получение информации о текущем пользователе
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [ProfileResponse]
     */
    @GET("api/profile/me")
    suspend fun getUserInfo(): Response<ProfileResponse>

    /**
     * Получение списка системных привилегий пользователя
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [kotlin.collections.List<PrivilegeResponse>]
     */
    @GET("api/profile/system-privileges")
    suspend fun getUserSystemPrivileges(): Response<kotlin.collections.List<PrivilegeResponse>>

    /**
     * Обновление настроек уведомлений
     * 
     * Responses:
     *  - 200: OK
     *
     * @param notificationSettingsRequest 
     * @return [Unit]
     */
    @PUT("api/profile/notifications")
    suspend fun updateNotifications(@Body notificationSettingsRequest: NotificationSettingsRequest): Response<Unit>

}
