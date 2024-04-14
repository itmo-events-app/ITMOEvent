package org.itmo.itmoevent.network.api

import org.itmo.itmoevent.network.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import okhttp3.ResponseBody
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import org.itmo.itmoevent.network.model.ParticipantPresenceRequest
import org.itmo.itmoevent.network.model.ParticipantResponse
import org.itmo.itmoevent.network.model.SetPartisipantsListRequest

interface ParticipantsControllerApi {
    /**
     * Изменения поля visited у участника мероприятия
     * 
     * Responses:
     *  - 0: default response
     *
     * @param id 
     * @param participantPresenceRequest 
     * @return [ParticipantResponse]
     */
    @PUT("api/events/{id}/participants")
    suspend fun changePresence(@Path("id") id: kotlin.Int, @Body participantPresenceRequest: ParticipantPresenceRequest): Response<ParticipantResponse>

    /**
     * Получение списка участников мероприятия
     * 
     * Responses:
     *  - 0: default response
     *
     * @param id 
     * @return [kotlin.collections.List<ParticipantResponse>]
     */
    @GET("api/events/{id}/participants")
    suspend fun getParticipants(@Path("id") id: kotlin.Int): Response<kotlin.collections.List<ParticipantResponse>>

    /**
     * Экспорт списка участников мероприятия
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [ResponseBody]
     */
    @GET("api/events/{id}/participants/file")
    suspend fun getParticipantsXlsxFile(@Path("id") id: kotlin.Int): Response<ResponseBody>

    /**
     * Импорт списка участников мероприятия
     * 
     * Responses:
     *  - 0: default response
     *
     * @param id 
     * @param setPartisipantsListRequest  (optional)
     * @return [kotlin.collections.List<ParticipantResponse>]
     */
    @POST("api/events/{id}/participants")
    suspend fun setPartisipantsList(@Path("id") id: kotlin.Int, @Body setPartisipantsListRequest: SetPartisipantsListRequest? = null): Response<kotlin.collections.List<ParticipantResponse>>

}
