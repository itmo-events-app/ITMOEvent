package org.itmo.itmoevent.model.repository

import android.util.Log
import org.itmo.itmoevent.model.data.dto.PrivilegeDto
import org.itmo.itmoevent.model.data.dto.taskShort.TaskShortDto
import org.itmo.itmoevent.model.data.entity.Privilege
import org.itmo.itmoevent.model.data.entity.Task
import org.itmo.itmoevent.model.data.entity.TaskShort
import org.itmo.itmoevent.model.network.TaskApi

class TaskRepository(private val taskApi: TaskApi) {

    suspend fun getEventOrActivityTasks(
        eventOrActivityId: Int
    ): List<TaskShort>? {
        return try {
            val response = taskApi.getTasks(eventId = eventOrActivityId, subEventTasksGet = true)
            if (response.isSuccessful)
                response.body()?.map(::mapTaskShortDtoToEntity)
            else null
        } catch (ex: Exception) {
            Log.i("retrofit", ex.stackTraceToString())
            null
        }
    }

    private fun mapTaskShortDtoToEntity(taskDto: TaskShortDto) = TaskShort(
        taskDto.id,
        taskDto.title,
        taskDto.taskStatus,
        taskDto.assignee?.name,
        taskDto.assignee?.surname
    )

}