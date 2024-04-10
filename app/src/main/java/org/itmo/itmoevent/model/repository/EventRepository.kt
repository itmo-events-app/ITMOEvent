package org.itmo.itmoevent.model.repository

import android.util.Log
import org.itmo.itmoevent.model.data.dto.EventShortDto
import org.itmo.itmoevent.model.data.entity.EventShort
import org.itmo.itmoevent.model.network.EventApi
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

class EventRepository(private val eventApi: EventApi) {

    suspend fun getAllEvents(
        title: String? = null,
        from: Date? = null,
        to: Date? = null,
        status: String? = null,
        format: String? = null
    ): List<EventShort>? {
        return try {
            val response = eventApi.getEvents(title, from, to, status, format)
            if (response.isSuccessful) {
                response.body()?.map {
                    mapEventShortDtoToEntity(it)
                }
            } else {
                null
            }
        } catch (ex: Exception) {
            Log.i("retrofit", ex.stackTraceToString())
            null
        }
    }


    suspend fun getUserEventsByRole(roleName: String? = null): List<EventShort>? {
        return try {
            val response = eventApi.getUserEventsByRole(roleName)
            if (response.isSuccessful) {
                response.body()?.map {
                    mapEventShortDtoToEntity(it)
                }
            } else {
                null
            }
        } catch (ex: Exception) {
            Log.i("retrofit", ex.stackTraceToString())
            null
        }
    }

    private fun mapEventShortDtoToEntity(entity: EventShortDto) =
        EventShort(
            entity.id,
            entity.title,
            entity.shortDesc,
            entity.status,
            LocalDateTime.ofInstant(entity.start.toInstant(), ZoneId.systemDefault()),
            LocalDateTime.ofInstant(entity.end.toInstant(), ZoneId.systemDefault()),
            entity.format
        )

}
