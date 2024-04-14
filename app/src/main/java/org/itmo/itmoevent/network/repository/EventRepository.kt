package org.itmo.itmoevent.network.repository

import okhttp3.MultipartBody
import org.itmo.itmoevent.network.api.EventControllerApi
import org.itmo.itmoevent.network.model.CreateEventRequest
import org.itmo.itmoevent.network.model.EventRequest
import org.itmo.itmoevent.network.util.apiRequestFlow
import retrofit2.http.Body
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

class EventRepository(private val eventApi: EventControllerApi) {
    fun addActivity(
        @Part("placeId") placeId: Int,
        @Part("startDate") startDate: java.time.OffsetDateTime,
        @Part("endDate") endDate: java.time.OffsetDateTime,
        @Part("title") title: String,
        @Part("shortDescription") shortDescription: String,
        @Part("fullDescription") fullDescription: String,
        @Part("format") format: String,
        @Part("status") status: String,
        @Part("registrationStart") registrationStart: java.time.OffsetDateTime,
        @Part("registrationEnd") registrationEnd: java.time.OffsetDateTime,
        @Part("participantLimit") participantLimit: Int,
        @Part("participantAgeLowest") participantAgeLowest: Int,
        @Part("participantAgeHighest") participantAgeHighest: Int,
        @Part("preparingStart") preparingStart: java.time.OffsetDateTime,
        @Part("preparingEnd") preparingEnd: java.time.OffsetDateTime,
        @Part image: MultipartBody.Part,
        @Part("parent") parent: Int? = null
    ) = apiRequestFlow {
        eventApi.addActivity(
            placeId,
            startDate,
            endDate,
            title,
            shortDescription,
            fullDescription,
            format,
            status,
            registrationStart,
            registrationEnd,
            participantLimit,
            participantAgeLowest,
            participantAgeHighest,
            preparingStart,
            preparingEnd,
            image
        )
    }

    fun addEventByOrganizer(@Body createEventRequest: CreateEventRequest) = apiRequestFlow {
        eventApi.addEventByOrganizer(createEventRequest)
    }

    fun copyEvent(@Path("id") id: Int, @Query("deep") deep: Boolean? = false) = apiRequestFlow {
        eventApi.copyEvent(id, deep)
    }

    fun deleteEventById(@Path("id") id: Int) = apiRequestFlow {
        eventApi.deleteEventById(id)
    }

    fun getAllOrFilteredEvents(
        @Query("page") page: Int? = 0,
        @Query("size") size: Int? = 15,
        @Query("parentId") parentId: Int? = null,
        @Query("title") title: String? = null,
        @Query("startDate") startDate: java.time.OffsetDateTime? = null,
        @Query("endDate") endDate: java.time.OffsetDateTime? = null,
        @Query("status") status: EventControllerApi.StatusGetAllOrFilteredEvents? = null,
        @Query("format") format: EventControllerApi.FormatGetAllOrFilteredEvents? = null
    ) = apiRequestFlow {
        eventApi.getAllOrFilteredEvents(
            page,
            size,
            parentId,
            title,
            startDate,
            endDate,
            status,
            format
        )
    }

    fun getEventById(@Path("id") id: Int) = apiRequestFlow {
        eventApi.getEventById(id)
    }

    fun getUsersHavingRoles(@Path("id") id: Int) = apiRequestFlow {
        eventApi.getUsersHavingRoles(id)
    }

    fun updateEvent(@Path("id") id: Int, @Body eventRequest: EventRequest? = null) =
        apiRequestFlow {
            eventApi.updateEvent(id, eventRequest)
        }

}
