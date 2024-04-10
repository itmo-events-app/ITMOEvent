package org.itmo.itmoevent.model.repository

import android.util.Log
import org.itmo.itmoevent.model.data.dto.EventDto
import org.itmo.itmoevent.model.data.dto.EventShortDto
import org.itmo.itmoevent.model.data.dto.ParticipantDto
import org.itmo.itmoevent.model.data.dto.PlaceDto
import org.itmo.itmoevent.model.data.dto.UserRoleDto
import org.itmo.itmoevent.model.data.entity.Event
import org.itmo.itmoevent.model.data.entity.EventDetails
import org.itmo.itmoevent.model.data.entity.EventShort
import org.itmo.itmoevent.model.data.entity.Participant
import org.itmo.itmoevent.model.data.entity.Place
import org.itmo.itmoevent.model.data.entity.UserRole
import org.itmo.itmoevent.model.network.EventApi
import org.itmo.itmoevent.model.network.PlaceApi
import java.time.LocalDateTime
import java.time.ZoneId

class EventDetailsRepository(private val eventApi: EventApi, private val placeApi: PlaceApi) {

    suspend fun updateEventParticipantIsMarked(eventId: Int, participantId: Int, isMarked: Boolean) : Boolean {
        return try {
            val response = eventApi.markEventParticipants(eventId, participantId, isMarked)
            response.isSuccessful
        } catch (ex: Exception) {
            false
        }
    }

    suspend fun eventDetails(eventId: Int): EventDetails? {
        try {
            val eventResponse = eventApi.getEventInfo(eventId)
            val activitiesResponse = eventApi.getEventActivities(eventId)
            val orgsResponse = eventApi.getEventOrganizers(eventId)
            val participantsResponse = eventApi.getEventParticipants(eventId)
            eventResponse.body()?.run {
                val placeResponse = placeApi.getPlaceById(this.placeId)
                if (eventResponse.isSuccessful && activitiesResponse.isSuccessful
                    && orgsResponse.isSuccessful && placeResponse.isSuccessful
                    && participantsResponse.isSuccessful
                ) {
                    return mapEventDtoToEntity(
                        eventResponse.body(),
                        activitiesResponse.body(),
                        orgsResponse.body(),
                        placeResponse.body(),
                        participantsResponse.body()
                    )
                } else {
                    return null
                }
            }
            return null
        } catch (ex: Exception) {
            Log.i("retrofit", ex.stackTraceToString())
            return null
        }
    }

    private fun mapEventDtoToEntity(
        eventDto: EventDto?,
        activitiesDto: List<EventShortDto>?,
        orgsDto: List<UserRoleDto>?,
        placeDto: PlaceDto?,
        participantsDto: List<ParticipantDto>?
    ): EventDetails? {
        if (eventDto == null || activitiesDto == null || orgsDto == null
            || placeDto == null || participantsDto == null
        ) {
            return null
        }

        return EventDetails(
            Event(
                eventDto.id,
                eventDto.title,
                eventDto.shortDescription,
                eventDto.fullDescription,
                eventDto.placeId,
                eventDto.status,
                LocalDateTime.ofInstant(eventDto.startDate.toInstant(), ZoneId.systemDefault()),
                LocalDateTime.ofInstant(eventDto.endDate.toInstant(), ZoneId.systemDefault()),
                eventDto.format,
                LocalDateTime.ofInstant(
                    eventDto.registrationStart.toInstant(),
                    ZoneId.systemDefault()
                ),
                LocalDateTime.ofInstant(
                    eventDto.registrationEnd.toInstant(),
                    ZoneId.systemDefault()
                ),
                eventDto.parent,
                eventDto.participantLimit,
                eventDto.participantAgeLowest,
                eventDto.participantAgeHighest,
                LocalDateTime.ofInstant(
                    eventDto.preparingStart.toInstant(),
                    ZoneId.systemDefault()
                ),
                LocalDateTime.ofInstant(eventDto.preparingEnd.toInstant(), ZoneId.systemDefault())
            ),
            activitiesDto.map {
                EventShort(
                    it.id,
                    it.title,
                    it.shortDesc,
                    it.status,
                    LocalDateTime.ofInstant(it.start.toInstant(), ZoneId.systemDefault()),
                    LocalDateTime.ofInstant(it.end.toInstant(), ZoneId.systemDefault()),
                    it.format
                )
            },
            orgsDto.map {
                UserRole(
                    it.id,
                    it.name,
                    it.surname,
                    it.roleName
                )
            }.groupBy { it.roleName },
            Place(
                placeDto.id,
                placeDto.name,
                placeDto.address,
                placeDto.format,
                placeDto.room,
                placeDto.description,
                placeDto.latitude,
                placeDto.longitude,
                placeDto.renderInfo
            ),

            participantsDto.map {
                Participant(
                    it.id,
                    it.name,
                    it.email,
                    it.additionalInfo,
                    it.visited
                )
            }
        )
    }

}
