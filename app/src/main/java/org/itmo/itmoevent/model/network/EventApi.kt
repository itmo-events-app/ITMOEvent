package org.itmo.itmoevent.model.network

import org.itmo.itmoevent.model.data.dto.EventShortDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Date

interface EventApi {

    @GET("events/")
    suspend fun getEvents(
        @Query("title") title: String?,
        @Query("from") from: Date?,
        @Query("to") to: Date?,
        @Query("status") status: String?,
        @Query("format") format: String?
    ) : Response<List<EventShortDto>>

}
