package org.itmo.itmoevent.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import org.itmo.itmoevent.model.data.entity.EventShort
import org.itmo.itmoevent.model.data.entity.Task
import org.itmo.itmoevent.model.repository.EventActivityRepository
import org.itmo.itmoevent.model.repository.EventRepository
import org.itmo.itmoevent.model.repository.TaskRepository

class TaskViewModel(
    private val taskRepository: TaskRepository,
    private val eventRepository: EventRepository,
    private val eventActivityRepository: EventActivityRepository
): ViewModel() {

    val taskLiveData: LiveData<List<Task>?> = liveData {
        val loaded = loadTask()
        emit(loaded)
    }

    private suspend fun loadTask(): List<Task>? {
        return taskRepository.getTasks()
    }

    val eventsLiveData: LiveData<List<EventShort>?> = liveData {
        val loaded = loadEvents()
        emit(loaded)
    }

    private suspend fun loadEvents(): List<EventShort>? {
        return eventRepository.getAllEvents()
    }


    class TaskViewModelFactory(
        private val taskRepository: TaskRepository,
        private val eventRepository: EventRepository,
        private val eventActivityRepository: EventActivityRepository
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
                return TaskViewModel(
                    taskRepository,
                    eventRepository,
                    eventActivityRepository
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}