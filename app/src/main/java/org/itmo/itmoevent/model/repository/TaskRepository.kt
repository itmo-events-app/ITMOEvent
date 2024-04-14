package org.itmo.itmoevent.model.repository

import android.util.Log
import org.itmo.itmoevent.model.data.entity.Task
import org.itmo.itmoevent.model.network.TaskApi

class TaskRepository(private val taskApi: TaskApi) {

    suspend fun getTasks(
        eventId: Int? = null,
        eventActivityId: Int? = null
    ) : List<Task>? {
        return try {
            val response = taskApi.getTasks(eventId, eventActivityId)
            if (response.isSuccessful)
                response.body()
            else
                emptyList()
        } catch (ex: Exception) {
            Log.i("retrofit", ex.stackTraceToString())
            emptyList()
        }
    }
}