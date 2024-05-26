package org.itmo.itmoevent.model.network

import org.itmo.itmoevent.model.data.dto.request.TaskRequest
import org.itmo.itmoevent.model.data.dto.task.TaskDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
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
    ): Response<List<TaskDto>>

    @GET("/api/tasks/{id}")
    suspend fun getTaskById(@Path("id") taskId: Int): Response<TaskDto>

    @DELETE("/api/tasks/{id}")
    suspend fun deleteTaskById(@Path("id") taskId: Int): Response<Unit>

    @POST("/api/tasks")
    suspend fun createTask(@Body task: TaskRequest): Response<Int>

}