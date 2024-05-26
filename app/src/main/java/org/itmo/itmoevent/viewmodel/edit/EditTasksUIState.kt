package org.itmo.itmoevent.viewmodel.edit

import org.itmo.itmoevent.model.data.entity.task.Task
import org.itmo.itmoevent.viewmodel.base.UiText

sealed class EditTasksUIState {
    data object LoadingList : EditTasksUIState()
    data class TaskList(val tasksList: List<Task>) : EditTasksUIState()
    data class LoadingError(val mess: String) : EditTasksUIState()
    data class SubmitBlocked(val errorText: UiText) : EditTasksUIState()
    data object SaveInProgress : EditTasksUIState()
    data class SaveError(val errorText: UiText) : EditTasksUIState()
//    data class SaveError(val errorText: UiText) : EditTasksUIState()
}