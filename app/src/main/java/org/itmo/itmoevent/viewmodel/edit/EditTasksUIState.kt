package org.itmo.itmoevent.viewmodel.edit

import org.itmo.itmoevent.model.data.entity.task.Task

sealed class EditTasksUIState {
    data object LoadingList : EditTasksUIState()
//    data object ActionInProgress : EditTasksUIState()
    data class TaskList(val tasksList: List<Task>) : EditTasksUIState()
    data class LoadingError(val mess: String) : EditTasksUIState()

}