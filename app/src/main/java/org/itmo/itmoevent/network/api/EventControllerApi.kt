package org.itmo.itmoevent.network.api

import org.itmo.itmoevent.network.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import org.itmo.itmoevent.network.model.CreateEventRequest
import org.itmo.itmoevent.network.model.EventResponse
import org.itmo.itmoevent.network.model.PaginatedResponse
import org.itmo.itmoevent.network.model.UserRoleResponse

import okhttp3.MultipartBody

interface EventControllerApi {

    /**
     * enum for parameter format
     */
    enum class FormatAddActivity(val value: kotlin.String) {
        @SerialName(value = "ONLINE")
        ONLINE("ONLINE"),
        @SerialName(value = "OFFLINE")
        OFFLINE("OFFLINE"),
        @SerialName(value = "HYBRID")
        HYBRID("HYBRID")
    }


    /**
     * enum for parameter status
     */
    enum class StatusAddActivity(val value: kotlin.String) {
        @SerialName(value = "DRAFT")
        DRAFT("DRAFT"),
        @SerialName(value = "PUBLISHED")
        PUBLISHED("PUBLISHED"),
        @SerialName(value = "COMPLETED")
        COMPLETED("COMPLETED"),
        @SerialName(value = "CANCELED")
        CANCELED("CANCELED")
    }

    /**
     * Создание активности мероприятия
     *
     * Responses:
     *  - 200: OK
     *
     * @param placeId
     * @param startDate
     * @param endDate
     * @param title
     * @param shortDescription
     * @param fullDescription
     * @param format
     * @param status
     * @param registrationStart
     * @param registrationEnd
     * @param participantLimit
     * @param participantAgeLowest
     * @param participantAgeHighest
     * @param preparingStart
     * @param preparingEnd
     * @param parent  (optional)
     * @param image  (optional)
     * @return [kotlin.Int]
     */
    @Multipart
    @POST("api/events/activity")
    suspend fun addActivity(
        @Part("placeId") placeId: kotlin.Int,
        @Part("startDate") startDate: java.time.LocalDateTime,
        @Part("endDate") endDate: java.time.LocalDateTime,
        @Part("title") title: kotlin.String,
        @Part("shortDescription") shortDescription: kotlin.String,
        @Part("fullDescription") fullDescription: kotlin.String,
        @Part("format") format: kotlin.String,
        @Part("status") status: kotlin.String,
        @Part("registrationStart") registrationStart: java.time.LocalDateTime,
        @Part("registrationEnd") registrationEnd: java.time.LocalDateTime,
        @Part("participantLimit") participantLimit: kotlin.Int,
        @Part("participantAgeLowest") participantAgeLowest: kotlin.Int,
        @Part("participantAgeHighest") participantAgeHighest: kotlin.Int,
        @Part("preparingStart") preparingStart: java.time.LocalDateTime,
        @Part("preparingEnd") preparingEnd: java.time.LocalDateTime,
        @Part("parent") parent: kotlin.Int? = null,
        @Part image: MultipartBody.Part? = null
    ): Response<kotlin.Int>

    /**
     * Создание мероприятия
     *
     * Responses:
     *  - 200: OK
     *
     * @param createEventRequest
     * @return [kotlin.Int]
     */
    @POST("api/events")
    suspend fun addEventByOrganizer(@Body createEventRequest: CreateEventRequest): Response<kotlin.Int>

    /**
     * Копирование мероприятия
     *
     * Responses:
     *  - 200: OK
     *
     * @param id ID мероприятия
     * @param deep Включить копирование активностей (optional, default to false)
     * @return [kotlin.Int]
     */
    @POST("api/events/{id}/copy")
    suspend fun copyEvent(
        @Path("id") id: kotlin.Int,
        @Query("deep") deep: kotlin.Boolean? = false
    ): Response<kotlin.Int>

    /**
     * Удаление активности
     *
     * Responses:
     *  - 200: OK
     *
     * @param id ID активности
     * @return [Unit]
     */
    @DELETE("api/events/{id}")
    suspend fun deleteActivityById(@Path("id") id: kotlin.Int): Response<Unit>


    /**
     * enum for parameter status
     */
    enum class StatusGetAllOrFilteredEvents(val value: kotlin.String) {
        @SerialName(value = "DRAFT")
        DRAFT("DRAFT"),
        @SerialName(value = "PUBLISHED")
        PUBLISHED("PUBLISHED"),
        @SerialName(value = "COMPLETED")
        COMPLETED("COMPLETED"),
        @SerialName(value = "CANCELED")
        CANCELED("CANCELED")
    }


    /**
     * enum for parameter format
     */
    enum class FormatGetAllOrFilteredEvents(val value: kotlin.String) {
        @SerialName(value = "ONLINE")
        ONLINE("ONLINE"),
        @SerialName(value = "OFFLINE")
        OFFLINE("OFFLINE"),
        @SerialName(value = "HYBRID")
        HYBRID("HYBRID")
    }

    /**
     * Фильтрация мероприятий
     *
     * Responses:
     *  - 200: OK
     *
     * @param page Номер страницы, с которой начать показ мероприятий (optional, default to 0)
     * @param size Число мероприятий на странице (optional, default to 15)
     * @param parentId ID родительского мероприятия (optional)
     * @param title Название мероприятия (optional)
     * @param startDate Дата начала мероприятия (optional)
     * @param endDate Дата окончания мероприятия (optional)
     * @param status Статус мероприятия (optional)
     * @param format Формат мероприятия (optional)
     * @return [PaginatedResponse]
     */
    @GET("api/events")
    suspend fun getAllOrFilteredEvents(
        @Query("page") page: kotlin.Int? = 0,
        @Query("size") size: kotlin.Int? = 15,
        @Query("parentId") parentId: kotlin.Int? = null,
        @Query("title") title: kotlin.String? = null,
        @Query("startDate") startDate: java.time.LocalDateTime? = null,
        @Query("endDate") endDate: java.time.LocalDateTime? = null,
        @Query("status") status: StatusGetAllOrFilteredEvents? = null,
        @Query("format") format: FormatGetAllOrFilteredEvents? = null
    ): Response<PaginatedResponse>

    /**
     * Получение мероприятия по id
     *
     * Responses:
     *  - 200: OK
     *
     * @param id ID мероприятия
     * @return [EventResponse]
     */
    @GET("api/events/{id}")
    suspend fun getEventById(@Path("id") id: kotlin.Int): Response<EventResponse>

    /**
     * Получение списка пользователей, имеющих роль в данном мероприятии
     *
     * Responses:
     *  - 200: OK
     *
     * @param id ID мероприятия
     * @return [kotlin.collections.List<UserRoleResponse>]
     */
    @GET("api/events/{id}/organizers")
    suspend fun getUsersHavingRoles(@Path("id") id: kotlin.Int): Response<kotlin.collections.List<UserRoleResponse>>


    /**
     * enum for parameter format
     */
    enum class FormatUpdateEvent(val value: kotlin.String) {
        @SerialName(value = "ONLINE")
        ONLINE("ONLINE"),
        @SerialName(value = "OFFLINE")
        OFFLINE("OFFLINE"),
        @SerialName(value = "HYBRID")
        HYBRID("HYBRID")
    }


    /**
     * enum for parameter status
     */
    enum class StatusUpdateEvent(val value: kotlin.String) {
        @SerialName(value = "DRAFT")
        DRAFT("DRAFT"),
        @SerialName(value = "PUBLISHED")
        PUBLISHED("PUBLISHED"),
        @SerialName(value = "COMPLETED")
        COMPLETED("COMPLETED"),
        @SerialName(value = "CANCELED")
        CANCELED("CANCELED")
    }

    /**
     * Обновление мероприятия
     *
     * Responses:
     *  - 0: default response
     *
     * @param id ID мероприятия
     * @param placeId
     * @param startDate
     * @param endDate
     * @param title
     * @param shortDescription
     * @param fullDescription
     * @param format
     * @param status
     * @param registrationStart
     * @param registrationEnd
     * @param participantLimit
     * @param participantAgeLowest
     * @param participantAgeHighest
     * @param preparingStart
     * @param preparingEnd
     * @param parent  (optional)
     * @param image  (optional)
     * @return [EventResponse]
     */
    @Multipart
    @PUT("api/events/{id}")
    suspend fun updateEvent(
        @Path("id") id: kotlin.Int,
        @Part("placeId") placeId: kotlin.Int,
        @Part("startDate") startDate: java.time.LocalDateTime,
        @Part("endDate") endDate: java.time.LocalDateTime,
        @Part("title") title: kotlin.String,
        @Part("shortDescription") shortDescription: kotlin.String,
        @Part("fullDescription") fullDescription: kotlin.String,
        @Part("format") format: kotlin.String,
        @Part("status") status: kotlin.String,
        @Part("registrationStart") registrationStart: java.time.LocalDateTime,
        @Part("registrationEnd") registrationEnd: java.time.LocalDateTime,
        @Part("participantLimit") participantLimit: kotlin.Int,
        @Part("participantAgeLowest") participantAgeLowest: kotlin.Int,
        @Part("participantAgeHighest") participantAgeHighest: kotlin.Int,
        @Part("preparingStart") preparingStart: java.time.LocalDateTime,
        @Part("preparingEnd") preparingEnd: java.time.LocalDateTime,
        @Part("parent") parent: kotlin.Int? = null,
        @Part image: MultipartBody.Part? = null
    ): Response<EventResponse>

}
