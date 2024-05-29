package org.itmo.itmoevent.viewmodel.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.itmo.itmoevent.model.data.entity.enums.PrivilegeName
import org.itmo.itmoevent.model.data.entity.enums.TaskStatus
import org.itmo.itmoevent.model.data.entity.task.Task
import org.itmo.itmoevent.model.repository.RoleRepository
import org.itmo.itmoevent.model.repository.TaskRepository
import org.itmo.itmoevent.viewmodel.base.UiText
import org.itmo.itmoevent.viewmodel.edit.EditTasksUIState
import org.itmo.itmoevent.viewmodel.util.PrivilegeUtil

class TaskDetailsViewModel(
    private val taskId: Int,
    private val eventId: Int,
    private val taskRepository: TaskRepository,
    private val roleRepository: RoleRepository
) : ViewModel() {

    private var task: Task? = null
    private var userId: Int? = null
    private var privileges: List<PrivilegeName>? = null
    private val _uiState = MutableLiveData<TaskActionsState>()
    val uiState: LiveData<TaskActionsState>
        get() = _uiState

    fun init() {
        loadTask()
    }


    private fun loadTask() = viewModelScope.launch {
        privileges = PrivilegeUtil.getEventPrivileges(roleRepository, eventId)
        userId = roleRepository.getUserId()
        val loadedTask = taskRepository.getTaskById(taskId)
        if (loadedTask == null) {
            _uiState.value = TaskActionsState.LoadError(UiText.DynamicString("Ошибка загрузки"))
        } else {
            task = loadedTask
            _uiState.value = formDataState(loadedTask)
        }
    }

    fun changeTaskStatus(newStatus: TaskStatus) = viewModelScope.launch {
        _uiState.value = TaskActionsState.SaveInProgress
        val loadedTask = taskRepository.changeTaskStatus(taskId, newStatus)
        if (loadedTask == null) {
            _uiState.value = TaskActionsState.LoadError(UiText.DynamicString("Ошибка сохранения"))
        } else {
            task = loadedTask
            _uiState.value = formDataState(loadedTask)
        }
    }

    private fun formDataState(task: Task) =
        TaskActionsState.Data(
            task,
            privileges?.contains(PrivilegeName.CHANGE_ASSIGNED_TASK_STATUS) ?: false,
            task.taskStatus?.name,
            (privileges?.contains(PrivilegeName.DECLINE_TASK_EXECUTION) ?: false) && userId == task.assigneeId,
            (privileges?.contains(PrivilegeName.ASSIGN_SELF_AS_TASK_EXECUTOR) ?: false) && task.assigneeId == null,
        )

    fun declineTask() = viewModelScope.launch {
        _uiState.value = TaskActionsState.SaveInProgress
        val loadedTask = taskRepository.removeUserAsAssignee(taskId)
        if (loadedTask == null) {
            _uiState.value = TaskActionsState.LoadError(UiText.DynamicString("Ошибка сохранения"))
        } else {
            task = loadedTask
            _uiState.value = formDataState(loadedTask)
        }
    }

    fun getTaskOnExecution() = viewModelScope.launch {
        _uiState.value = TaskActionsState.SaveInProgress
        val loadedTask = taskRepository.setUserAsAssignee(taskId)
        if (loadedTask == null) {
            _uiState.value = TaskActionsState.LoadError(UiText.DynamicString("Ошибка сохранения"))
        } else {
            task = loadedTask
            _uiState.value = formDataState(loadedTask)
        }
    }


    class TaskViewModelModelFactory(
        private val taskId: Int,
        private val eventId: Int,
        private val taskRepository: TaskRepository,
        private val roleRepository: RoleRepository
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TaskDetailsViewModel::class.java)) {
                return TaskDetailsViewModel(
                    taskId,
                    eventId,
                    taskRepository,
                    roleRepository
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}