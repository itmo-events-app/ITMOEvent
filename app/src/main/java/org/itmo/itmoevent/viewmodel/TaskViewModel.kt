package org.itmo.itmoevent.viewmodel

import androidx.datastore.preferences.protobuf.Api
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.itmo.itmoevent.model.data.entity.EventShort
import org.itmo.itmoevent.model.data.entity.Task
import org.itmo.itmoevent.model.repository.EventActivityRepository
import org.itmo.itmoevent.network.model.ProfileResponse
import org.itmo.itmoevent.network.model.TaskResponse
import org.itmo.itmoevent.network.repository.EventRepository
import org.itmo.itmoevent.network.repository.TaskRepository
import org.itmo.itmoevent.network.util.ApiResponse
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val eventRepository: EventRepository
) : BaseViewModel() {

    private val _taskListResponse = MutableLiveData<ApiResponse<List<TaskResponse>>>()
    val taskListResponse = _taskListResponse

    private val _taskResponse = MutableLiveData<ApiResponse<TaskResponse>>()
    val taskResponse = _taskResponse


    fun taskListShowWhereAssignee(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _taskListResponse,
        coroutinesErrorHandler
    ) {
        taskRepository.taskListShowWhereAssignee()
    }

    fun taskGet(taskId: Int, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _taskResponse,
        coroutinesErrorHandler
    ) {
        taskRepository.taskGet(taskId)
    }

    fun taskSetStatus(taskId: Int, newStatus: String, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        MutableLiveData(),
        coroutinesErrorHandler
    ) {
        taskRepository.taskSetStatus(taskId, newStatus)
    }

}