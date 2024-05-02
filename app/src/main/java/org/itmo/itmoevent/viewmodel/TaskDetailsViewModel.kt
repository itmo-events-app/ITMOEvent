package org.itmo.itmoevent.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import org.itmo.itmoevent.model.data.entity.enums.PrivilegeName
import org.itmo.itmoevent.model.repository.PlaceRepository
import org.itmo.itmoevent.model.repository.RoleRepository
import org.itmo.itmoevent.model.repository.TaskRepository

class TaskDetailsViewModel(
    private val taskId: Int,
    private val taskRepository: TaskRepository,
    private val roleRepository: RoleRepository
) : ViewModel() {

    val taskDetailsLiveData = ContentLiveDataProvider(
        scope = viewModelScope
    ) {
        viewModelScope.async {
            taskRepository.getTaskById(taskId)
        }
    }.contentLiveData


    class TaskViewModelModelFactory(
        private val taskId: Int,
        private val taskRepository: TaskRepository,
        private val roleRepository: RoleRepository
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TaskDetailsViewModel::class.java)) {
                return TaskDetailsViewModel(
                    taskId,
                    taskRepository,
                    roleRepository
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}