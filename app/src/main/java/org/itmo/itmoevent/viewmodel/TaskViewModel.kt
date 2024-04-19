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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.itmo.itmoevent.model.data.entity.EventShort
import org.itmo.itmoevent.model.data.entity.Task
import org.itmo.itmoevent.model.repository.EventActivityRepository
import org.itmo.itmoevent.network.api.TaskControllerApi
import org.itmo.itmoevent.network.model.ProfileResponse
import org.itmo.itmoevent.network.model.TaskResponse
import org.itmo.itmoevent.network.repository.EventRepository
import org.itmo.itmoevent.network.repository.TaskRepository
import org.itmo.itmoevent.network.util.ApiResponse
import retrofit2.Response
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDateTime
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

    private val _filterResponse = MutableLiveData<ApiResponse<List<TaskResponse>>>()
    val filterResponse = _filterResponse

    val isCurrent = MutableLiveData<Boolean>(true)

    val selectedEventId = MutableLiveData<Int>(-1)
    val selectedActivityId = MutableLiveData<Int>(-1)

    fun taskListShowWhereAssignee(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _taskListResponse,
        coroutinesErrorHandler
    ) {
        if (isCurrent.value!!) {
            taskRepository.taskListShowWhereAssignee(deadlineLowerLimit = LocalDateTime.now())
        } else {
            taskRepository.taskListShowWhereAssignee(deadlineUpperLimit = LocalDateTime.now())
        }

    }

    fun taskGet(taskId: Int, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _taskResponse,
        coroutinesErrorHandler
    ) {
        taskRepository.taskGet(taskId)
    }

    fun taskSetStatus(
        taskId: Int,
        newStatus: String,
        coroutinesErrorHandler: CoroutinesErrorHandler
    ) = baseRequest(
        MutableLiveData(),
        coroutinesErrorHandler
    ) {
        taskRepository.taskSetStatus(taskId, newStatus)
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
        pageSize: Int? = 50,
        coroutinesErrorHandler: CoroutinesErrorHandler
    ) = baseRequest(_filterResponse, coroutinesErrorHandler) {
        if (isCurrent.value!!) {
            taskRepository.taskListShowInEvent(
                eventId,
                assigneeId,
                assignerId,
                taskStatus,
                LocalDateTime.now(),
                deadlineUpperLimit,
                subEventTasksGet,
                personalTasksGet,
                page,
                pageSize
            )
        } else {
            taskRepository.taskListShowInEvent(
                eventId,
                assigneeId,
                assignerId,
                taskStatus,
                deadlineLowerLimit,
                LocalDateTime.now(),
                subEventTasksGet,
                personalTasksGet,
                page,
                pageSize
            )
        }

    }

    fun taskDeleteAssignee(
        taskId: Int,
        coroutinesErrorHandler: CoroutinesErrorHandler
    ) = baseRequest(
        MutableLiveData(),
        coroutinesErrorHandler
    )  {
        taskRepository.taskDeleteAssignee(taskId)
    }

    fun updateIsCurrent(newIsCurrent: Boolean) {
        viewModelScope.launch {
            isCurrent.value = newIsCurrent
        }
    }

    fun selectEventId(eventId: Int) {
        viewModelScope.launch {
            selectedEventId.value = eventId
        }
    }

    fun selectActivityId(activityId: Int) {
        viewModelScope.launch {
            selectedActivityId.value = activityId
        }
    }

}