package org.itmo.itmoevent.model.network

import org.itmo.itmoevent.model.data.entity.EventActivity
import org.itmo.itmoevent.model.data.entity.Task
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface EventActivityApi {

    @GET("activity")
    suspend fun getActivity(
        @Query("eventId") eventId: Int?
    ) : Response<List<EventActivity>>
}