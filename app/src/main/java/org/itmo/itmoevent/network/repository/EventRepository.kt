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
        placeId: Int,
        startDate: java.time.OffsetDateTime,
        endDate: java.time.OffsetDateTime,
        title: String,
        shortDescription: String,
        fullDescription: String,
        format: String,
        status: String,
        registrationStart: java.time.OffsetDateTime,
        registrationEnd: java.time.OffsetDateTime,
        participantLimit: Int,
        participantAgeLowest: Int,
        participantAgeHighest: Int,
        preparingStart: java.time.OffsetDateTime,
        preparingEnd: java.time.OffsetDateTime,
        image: MultipartBody.Part,
        parent: Int? = null
    ) = apiRequestFlow {
        eventApi.addActivity(
            placeId,
            startDate.toLocalDateTime(),
            endDate.toLocalDateTime(),
            title,
            shortDescription,
            fullDescription,
            format,
            status,
            registrationStart.toLocalDateTime(),
            registrationEnd.toLocalDateTime(),
            participantLimit,
            participantAgeLowest,
            participantAgeHighest,
            preparingStart.toLocalDateTime(),
            preparingEnd.toLocalDateTime(),
            parent,
            image
        )
    }

    fun addEventByOrganizer(createEventRequest: CreateEventRequest) = apiRequestFlow {
        eventApi.addEventByOrganizer(createEventRequest)
    }

    fun copyEvent(id: Int, deep: Boolean? = false) = apiRequestFlow {
        eventApi.copyEvent(id, deep)
    }

    fun deleteActivityById(id: Int) = apiRequestFlow {
        eventApi.deleteActivityById(id)
    }

    fun getAllOrFilteredEvents(
        page: Int? = 0,
        size: Int? = 15,
        parentId: Int? = null,
        title: String? = null,
        startDate: java.time.OffsetDateTime? = null,
        endDate: java.time.OffsetDateTime? = null,
        status: EventControllerApi.StatusGetAllOrFilteredEvents? = null,
        format: EventControllerApi.FormatGetAllOrFilteredEvents? = null
    ) = apiRequestFlow {
        eventApi.getAllOrFilteredEvents(
            page,
            size,
            parentId,
            title,
            startDate?.toLocalDateTime(),
            endDate?.toLocalDateTime(),
            status,
            format
        )
    }

    fun getEventById(id: Int) = apiRequestFlow {
        eventApi.getEventById(id)
    }

    fun getUsersHavingRoles(id: Int) = apiRequestFlow {
        eventApi.getUsersHavingRoles(id)
    }

    fun updateEvent(
        id: Int,
        placeId: Int,
        startDate: java.time.OffsetDateTime,
        endDate: java.time.OffsetDateTime,
        title: String,
        shortDescription: String,
        fullDescription: String,
        format: String,
        status: String,
        registrationStart: java.time.OffsetDateTime,
        registrationEnd: java.time.OffsetDateTime,
        participantLimit: Int,
        participantAgeLowest: Int,
        participantAgeHighest: Int,
        preparingStart: java.time.OffsetDateTime,
        preparingEnd: java.time.OffsetDateTime,
        parent: Int? = null,
        image: MultipartBody.Part? = null
    ) = apiRequestFlow {
        eventApi.updateEvent(
            id,
            placeId,
            startDate.toLocalDateTime(),
            endDate.toLocalDateTime(),
            title,
            shortDescription,
            fullDescription,
            format,
            status,
            registrationStart.toLocalDateTime(),
            registrationEnd.toLocalDateTime(),
            participantLimit,
            participantAgeLowest,
            participantAgeHighest,
            preparingStart.toLocalDateTime(),
            preparingEnd.toLocalDateTime(),
            parent,
            image
        )
    }


}
