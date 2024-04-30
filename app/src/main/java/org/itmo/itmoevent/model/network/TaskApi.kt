package org.itmo.itmoevent.model.network

import org.itmo.itmoevent.model.data.dto.taskShort.TaskShortDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.Date

interface TaskApi {

    @GET("/api/tasks/event/{eventId}")
    suspend fun getTasks(
        @Path("eventId") eventId: Int,
        @Query("assigneeId") assigneeId: Int? = null,
        @Query("assignerId") assignerId: Int? = null,
        @Query("taskStatus") taskStatus: String? = null,
        @Query("deadlineLowerLimit") deadlineLowerLimit: Date? = null,
        @Query("deadlineUpperLimit") deadlineUpperLimit: Date? = null,
        @Query("subEventTasksGet") subEventTasksGet: Boolean? = null,
        @Query("personalTasksGet") personalTasksGet: Boolean? = null,
        @Query("page") page: Int? = null,
        @Query("pageSize") pageSize: Int? = null
    ): Response<List<TaskShortDto>>

}