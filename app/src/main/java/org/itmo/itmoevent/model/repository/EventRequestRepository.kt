package org.itmo.itmoevent.model.repository

import android.util.Log
import org.itmo.itmoevent.model.data.entity.EventRequest
import org.itmo.itmoevent.model.network.EventApi

class EventRequestRepository(private val eventApi: EventApi) {

    suspend fun getEventsRequests(): List<EventRequest>? {
        try {
            val response = eventApi.getEventRequests()
            return if (response.isSuccessful) {
                response.body()?.map {
                    EventRequest(
                        it.id,
                        it.title
                    )
                }
            } else {
                null
            }
        } catch (ex: Exception) {
            Log.i("retrofit", ex.stackTraceToString())
            return null
        }
    }

}
