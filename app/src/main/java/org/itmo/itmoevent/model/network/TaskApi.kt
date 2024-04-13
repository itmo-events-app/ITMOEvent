package org.itmo.itmoevent.model.network

import org.itmo.itmoevent.model.data.entity.Task
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Date

interface TaskApi {

    @GET("tasks/")
    suspend fun getTasks(
        @Query("eventId") eventId: Int?,
        @Query("eventActivityId") eventActivityId: Int?
    ) : Response<List<Task>>


}