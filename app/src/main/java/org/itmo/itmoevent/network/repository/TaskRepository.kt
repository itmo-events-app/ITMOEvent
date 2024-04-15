package org.itmo.itmoevent.network.repository

import org.itmo.itmoevent.network.api.TaskControllerApi
import org.itmo.itmoevent.network.model.TaskRequest
import org.itmo.itmoevent.network.util.apiRequestFlow
import retrofit2.http.Body
import retrofit2.http.Path
import retrofit2.http.Query


class TaskRepository(private val taskApi: TaskControllerApi) {

    fun taskAdd(@Body taskRequest: TaskRequest) = apiRequestFlow {
        taskApi.taskAdd(taskRequest)
    }

    fun taskDelete(@Path("id") id: Int) = apiRequestFlow {
        taskApi.taskDelete(id)
    }

    fun taskDeleteAssignee(@Path("id") id: Int) = apiRequestFlow {
        taskApi.taskDeleteAssignee(id)
    }

    fun taskEdit(@Path("id") id: Int, @Body taskRequest: TaskRequest) = apiRequestFlow {
        taskApi.taskEdit(id, taskRequest)
    }

    fun taskGet(@Path("id") id: Int) = apiRequestFlow {
        taskApi.taskGet(id)
    }

    fun taskListCopy(@Path("dstEventId") dstEventId: Int, @Body requestBody: List<Int>) =
        apiRequestFlow {
            taskApi.taskListCopy(dstEventId, requestBody)
        }

    fun taskListMove(@Path("dstEventId") dstEventId: Int, @Body requestBody: List<Int>) =
        apiRequestFlow {
            taskApi.taskListMove(dstEventId, requestBody)
        }

    fun taskListShowInEvent(
        @Path("eventId") eventId: Int,
        @Query("assigneeId") assigneeId: Int? = null,
        @Query("assignerId") assignerId: Int? = null,
        @Query("taskStatus") taskStatus: TaskControllerApi.TaskStatusTaskListShowInEvent? = null,
        @Query("deadlineLowerLimit") deadlineLowerLimit: java.time.LocalDateTime? = null,
        @Query("deadlineUpperLimit") deadlineUpperLimit: java.time.LocalDateTime? = null,
        @Query("subEventTasksGet") subEventTasksGet: Boolean? = false,
        @Query("personalTasksGet") personalTasksGet: Boolean? = false,
        @Query("page") page: Int? = 0,
        @Query("pageSize") pageSize: Int? = 50
    ) = apiRequestFlow {
        taskApi.taskListShowInEvent(
            eventId,
            assigneeId,
            assignerId,
            taskStatus,
            deadlineLowerLimit,
            deadlineUpperLimit,
            subEventTasksGet,
            personalTasksGet,
            page,
            pageSize
        )
    }

    fun taskListShowWhereAssignee(
        @Query("eventId") eventId: Int? = null,
        @Query("assignerId") assignerId: Int? = null,
        @Query("taskStatus") taskStatus: TaskControllerApi.TaskStatusTaskListShowWhereAssignee? = null,
        @Query("deadlineLowerLimit") deadlineLowerLimit: java.time.LocalDateTime? = null,
        @Query("deadlineUpperLimit") deadlineUpperLimit: java.time.LocalDateTime? = null,
        @Query("page") page: Int? = 0,
        @Query("pageSize") pageSize: Int? = 50
    ) = apiRequestFlow {
        taskApi.taskListShowWhereAssignee(
            eventId,
            assignerId,
            taskStatus,
            deadlineLowerLimit,
            deadlineUpperLimit,
            page,
            pageSize
        )
    }

    fun taskSetAssignee(@Path("id") id: Int, @Path("userId") userId: Int) = apiRequestFlow {
        taskApi.taskSetAssignee(id, userId)
    }

    fun taskSetStatus(@Path("id") id: Int, @Body body: String) = apiRequestFlow {
        taskApi.taskSetStatus(id, body)
    }

    fun taskTakeOn(@Path("id") id: Int) = apiRequestFlow {
        taskApi.taskTakeOn(id)
    }
}
