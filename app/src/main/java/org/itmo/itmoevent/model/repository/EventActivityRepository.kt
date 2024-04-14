package org.itmo.itmoevent.model.repository

import android.util.Log
import org.itmo.itmoevent.model.data.entity.EventActivity
import org.itmo.itmoevent.model.network.EventActivityApi

class EventActivityRepository(private val eventActivityApi: EventActivityApi) {

    suspend fun getActivity(eventId: Int? = null) : List<EventActivity>? {
        return try {
            val response = eventActivityApi.getActivity(eventId)
            if (response.isSuccessful)
                response.body()
            else
                emptyList()
        } catch (ex: Exception) {
            Log.i("retrofit", ex.stackTraceToString())
            emptyList()
        }
    }
}