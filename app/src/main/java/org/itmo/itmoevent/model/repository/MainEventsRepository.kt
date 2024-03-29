package org.itmo.itmoevent.model.repository

import org.itmo.itmoevent.model.data.entity.EventShort
import org.itmo.itmoevent.model.network.EventApi
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

class MainEventsRepository(private val eventApi: EventApi) {

    suspend fun getEvents(
        title: String? = null,
        from: Date? = null,
        to: Date? = null,
        status: String? = null,
        format: String? = null
    ): List<EventShort>? {
        val response = eventApi.getEvents(title, from, to, status, format)
        if (response.isSuccessful) {
            return response.body()?.map {
                EventShort(
                    it.id,
                    it.title,
                    it.shortDesc,
                    it.place,
                    it.status,
                    LocalDateTime.ofInstant(it.start.toInstant(), ZoneId.systemDefault()),
                    LocalDateTime.ofInstant(it.end.toInstant(), ZoneId.systemDefault()),
                    it.format
                )
            }
        } else {
            return null
        }

    }

}
