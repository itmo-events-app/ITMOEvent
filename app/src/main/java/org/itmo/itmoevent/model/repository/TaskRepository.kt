package org.itmo.itmoevent.model.repository

import android.util.Log
import org.itmo.itmoevent.model.data.dto.request.TaskRequest
import org.itmo.itmoevent.model.data.dto.task.TaskDto
import org.itmo.itmoevent.model.data.entity.PlaceShort
import org.itmo.itmoevent.model.data.entity.enums.TaskStatus
import org.itmo.itmoevent.model.data.entity.task.Task
import org.itmo.itmoevent.model.data.entity.task.TaskShort
import org.itmo.itmoevent.model.network.TaskApi
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

class TaskRepository(private val taskApi: TaskApi) {

    suspend fun getEventOrActivityTasksShort(
        eventOrActivityId: Int, hasSubEventTasks: Boolean = false
    ): List<TaskShort>? {
        return try {
            val response =
                taskApi.getTasks(eventId = eventOrActivityId, subEventTasksGet = hasSubEventTasks)
            if (response.isSuccessful) {
                response.body()?.map(::mapTaskShortDtoToEntity)
            } else null
        } catch (ex: Exception) {
            Log.i("retrofit", ex.stackTraceToString())
            null
        }
    }

    suspend fun getEventOrActivityTasks(
        eventOrActivityId: Int, hasSubEventTasks: Boolean = false
    ): List<Task>? {
        return try {
            val response =
                taskApi.getTasks(eventId = eventOrActivityId, subEventTasksGet = hasSubEventTasks)
            if (response.isSuccessful) {
                response.body()?.map(::mapTaskDtoToEntity)
            } else null
        } catch (ex: Exception) {
            Log.i("retrofit", ex.stackTraceToString())
            null
        }
    }

    suspend fun deleteTask(taskId: Int): Boolean {
        return try {
            val response = taskApi.deleteTaskById(taskId)
            response.isSuccessful
        } catch (ex: Exception) {
            Log.i("retrofit", ex.stackTraceToString())
            false
        }
    }

    suspend fun createTask(task: TaskRequest): Int? {
        return try {
            val response = taskApi.createTask(task)
            if (response.isSuccessful) {
                return response.body()
            } else null
        } catch (ex: Exception) {
            Log.i("retrofit", ex.stackTraceToString())
            null
        }
    }

    suspend fun getTaskById(taskId: Int): Task? {
        return try {
            val response = taskApi.getTaskById(taskId)
            if (response.isSuccessful) {
                response.body()?.let { mapTaskDtoToEntity(it) }
            } else null
        } catch (ex: Exception) {
            Log.i("retrofit", ex.stackTraceToString())
            null
        }
    }

    suspend fun changeTaskStatus(taskId: Int, newStatus: TaskStatus): Task? {
        return try {
            val response = taskApi.changeTaskStatus(taskId, newStatus)
            if (response.isSuccessful) {
                response.body()?.let { mapTaskDtoToEntity(it) }
            } else null
        } catch (ex: Exception) {
            Log.i("retrofit", ex.stackTraceToString())
            null
        }
    }

    suspend fun removeUserAsAssignee(taskId: Int): Task? {
        return try {
            val response = taskApi.removeUserAsAssignee(taskId)
            if (response.isSuccessful) {
                response.body()?.let { mapTaskDtoToEntity(it) }
            } else null
        } catch (ex: Exception) {
            Log.i("retrofit", ex.stackTraceToString())
            null
        }
    }

    suspend fun setUserAsAssignee(taskId: Int): Task? {
        return try {
            val response = taskApi.setUserAsAssignee(taskId)
            if (response.isSuccessful) {
                response.body()?.let { mapTaskDtoToEntity(it) }
            } else null
        } catch (ex: Exception) {
            Log.i("retrofit", ex.stackTraceToString())
            null
        }
    }

    private fun mapTaskShortDtoToEntity(taskDto: TaskDto) = TaskShort(
        taskDto.id,
        taskDto.title,
        taskDto.taskStatus,
        taskDto.event.eventId,
        taskDto.assignee?.name,
        taskDto.assignee?.surname
    )

    private fun mapTaskDtoToEntity(taskDto: TaskDto) =
        taskDto.run {
            Task(
                id,
                title,
                taskStatus,
                description,
                assignee?.id,
                assignee?.name,
                assignee?.surname,
                event?.eventId,
                event?.activityId,
                event?.eventTitle,
                event?.activityTitle,
                place?.let {
                    PlaceShort(
                        place.id,
                        place.name,
                        place.address,
                        null
                    )
                },
                getLocalDateTime(creationTime),
                getLocalDateTime(deadline),
                getLocalDateTime(reminder)
            )
        }

    private fun getLocalDateTime(date: Date) =
        LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())

}