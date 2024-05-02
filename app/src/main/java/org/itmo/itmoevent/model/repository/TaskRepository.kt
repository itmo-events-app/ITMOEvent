package org.itmo.itmoevent.model.repository

import android.util.Log
import org.itmo.itmoevent.model.data.dto.task.TaskDto
import org.itmo.itmoevent.model.data.dto.taskShort.TaskShortDto
import org.itmo.itmoevent.model.data.entity.Place
import org.itmo.itmoevent.model.data.entity.PlaceShort
import org.itmo.itmoevent.model.data.entity.task.Task
import org.itmo.itmoevent.model.data.entity.task.TaskShort
import org.itmo.itmoevent.model.network.TaskApi
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

class TaskRepository(private val taskApi: TaskApi) {

    suspend fun getEventOrActivityTasks(
        eventOrActivityId: Int
    ): List<TaskShort>? {
        return try {
            val response = taskApi.getTasks(eventId = eventOrActivityId, subEventTasksGet = true)
            if (response.isSuccessful) {
                response.body()?.map(::mapTaskShortDtoToEntity)
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

    private fun mapTaskShortDtoToEntity(taskDto: TaskShortDto) = TaskShort(
        taskDto.id,
        taskDto.title,
        taskDto.taskStatus,
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