package org.itmo.itmoevent.viewmodel.edit

import org.itmo.itmoevent.model.data.entity.search.ActivitySearch
import org.itmo.itmoevent.model.data.entity.search.PlaceSearch
import org.itmo.itmoevent.model.data.entity.search.UserSearch
import org.itmo.itmoevent.model.data.entity.task.Task
import org.itmo.itmoevent.viewmodel.base.UiText

sealed class EditTasksUIState {
    data object LoadingList : EditTasksUIState()

    data class TaskList(val tasksList: List<Task>) : EditTasksUIState()

    data class FormData(
        val orgsSearch: List<String>,
        val placesSearch: List<String>,
        val activitiesSearch: List<String>
    ) : EditTasksUIState()

    data object LoadingForm : EditTasksUIState()

    data class LoadingError(val mess: String) : EditTasksUIState()
    data class SubmitBlocked(val errorText: UiText) : EditTasksUIState()
    data object SaveInProgress : EditTasksUIState()
    data class SaveError(val errorText: UiText) : EditTasksUIState()
//    data class SaveError(val errorText: UiText) : EditTasksUIState()
}