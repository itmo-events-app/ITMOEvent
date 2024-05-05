package org.itmo.itmoevent.viewmodel.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.itmo.itmoevent.model.data.entity.task.Task
import org.itmo.itmoevent.model.repository.RoleRepository
import org.itmo.itmoevent.model.repository.TaskRepository

class EditTasksViewModel(
    private val eventId: Int,
    private val roleRepository: RoleRepository,
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<EditTasksUIState>()
    val uiState: LiveData<EditTasksUIState>
        get() = _uiState

    private var tasks = mutableListOf<Task>()


    fun init() {
        loadTasks()
    }

    fun deleteTask(taskId: Int)  = viewModelScope.launch {
        _uiState.value = EditTasksUIState.LoadingList
        val isSuccessful = taskRepository.deleteTask(taskId)
        if (isSuccessful) {
            tasks.removeIf { it.id == taskId }
            _uiState.value = EditTasksUIState.TaskList(tasks)
        } else {
            _uiState.value = EditTasksUIState.LoadingError("Ошибка удаления")
        }
    }

    private fun loadTasks() = viewModelScope.launch {
        _uiState.value = EditTasksUIState.LoadingList
        val loadedTasks = taskRepository.getEventOrActivityTasks(eventId, true)
        if (loadedTasks == null) {
            _uiState.value = EditTasksUIState.LoadingError("Ошибка загрузки")
        } else {
            tasks = loadedTasks.toMutableList()
            _uiState.value = EditTasksUIState.TaskList(tasks)
        }
    }

    class EditTasksViewModelFactory(
        private val activityId: Int,
        private val roleRepository: RoleRepository,
        private val taskRepository: TaskRepository
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EditTasksViewModel::class.java)) {
                return EditTasksViewModel(
                    activityId,
                    roleRepository,
                    taskRepository
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}