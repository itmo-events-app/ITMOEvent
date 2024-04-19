package org.itmo.itmoevent.network.repository

import org.itmo.itmoevent.network.api.TaskControllerApi
import org.itmo.itmoevent.network.model.TaskRequest
import org.itmo.itmoevent.network.util.apiRequestFlow
import retrofit2.http.Body
import retrofit2.http.Path
import retrofit2.http.Query

class TaskRepository(private val taskApi: TaskControllerApi) {

    fun taskAdd(taskRequest: TaskRequest) = apiRequestFlow {
        taskApi.taskAdd(taskRequest)
    }

    fun taskDelete(id: Int) = apiRequestFlow {
        taskApi.taskDelete(id)
    }

    fun taskDeleteAssignee(id: Int) = apiRequestFlow {
        taskApi.taskDeleteAssignee(id)
    }

    fun taskEdit(id: Int, taskRequest: TaskRequest) = apiRequestFlow {
        taskApi.taskEdit(id, taskRequest)
    }

    fun taskGet(id: Int) = apiRequestFlow {
        taskApi.taskGet(id)
    }

    fun taskListCopy(dstEventId: Int, requestBody: List<Int>) = apiRequestFlow {
        taskApi.taskListCopy(dstEventId, requestBody)
    }

    fun taskListMove(dstEventId: Int, requestBody: List<Int>) = apiRequestFlow {
        taskApi.taskListMove(dstEventId, requestBody)
    }

    fun taskListShowInEvent(
        eventId: Int,
        assigneeId: Int? = null,
        assignerId: Int? = null,
        taskStatus: TaskControllerApi.TaskStatusTaskListShowInEvent? = null,
        deadlineLowerLimit: java.time.LocalDateTime? = null,
        deadlineUpperLimit: java.time.LocalDateTime? = null,
        subEventTasksGet: Boolean? = false,
        personalTasksGet: Boolean? = false,
        page: Int? = 0,
        pageSize: Int? = 50
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
        eventId: Int? = null,
        assignerId: Int? = null,
        taskStatus: TaskControllerApi.TaskStatusTaskListShowWhereAssignee? = null,
        deadlineLowerLimit: java.time.LocalDateTime? = null,
        deadlineUpperLimit: java.time.LocalDateTime? = null,
        page: Int? = 0,
        pageSize: Int? = 50
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

    fun taskSetAssignee(id: Int, userId: Int) = apiRequestFlow {
        taskApi.taskSetAssignee(id, userId)
    }

    fun taskSetStatus(id: Int, body: String) = apiRequestFlow {
        taskApi.taskSetStatus(id, body)
    }

    fun taskTakeOn(id: Int) = apiRequestFlow {
        taskApi.taskTakeOn(id)
    }
}
