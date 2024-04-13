package org.itmo.itmoevent.model.network

import org.itmo.itmoevent.model.data.dto.EventDto
import org.itmo.itmoevent.model.data.dto.EventRequestDto
import org.itmo.itmoevent.model.data.dto.EventShortDto
import org.itmo.itmoevent.model.data.dto.ParticipantDto
import org.itmo.itmoevent.model.data.dto.UserRoleDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.Date

interface EventApi {

    @GET("/api/events/")
    suspend fun getEvents(
        @Query("title") title: String?,
        @Query("from") from: Date?,
        @Query("to") to: Date?,
        @Query("status") status: String?,
        @Query("format") format: String?
    ): Response<List<EventShortDto>>

    @GET("requests/")
    suspend fun getEventRequests(): Response<List<EventRequestDto>>

    @GET("events/my/")
    suspend fun getUserEventsByRole(@Query("role") roleName: String?): Response<List<EventShortDto>>

    @GET("/api/events/")
    suspend fun getEventActivities(@Query("parentId") eventId: Int): Response<List<EventShortDto>>

    @GET("/api/events/{id}/participants/list")
    suspend fun getEventParticipants(@Path("id") eventId: Int): Response<List<ParticipantDto>>

    @PUT("/api/events/{id}/participants")
    suspend fun markEventParticipants(
        @Path("id") eventId: Int,
        @Query("idParticipant") participantId: Int,
        @Query("isVisited") isMarked: Boolean
    ) : Response<Void>

    @GET("/api/events/{id}/organizers")
    suspend fun getEventOrganizers(@Path("id") eventId: Int): Response<List<UserRoleDto>>

    @GET("/api/events/{id}")
    suspend fun getEventInfo(@Path("id") eventId: Int): Response<EventDto>


}
