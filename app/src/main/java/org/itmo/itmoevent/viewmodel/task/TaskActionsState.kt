package org.itmo.itmoevent.viewmodel.task

import org.itmo.itmoevent.model.data.entity.task.Task
import org.itmo.itmoevent.viewmodel.base.UiText

sealed class TaskActionsState {

    data class Data(
        val task: Task,
        val isChangeStatusEnabled: Boolean,
        val status: String?,
        val isDeclineEnabled: Boolean,
        val isGetTaskEnabled: Boolean
    ) : TaskActionsState()

    data object LoadInProgress : TaskActionsState()
    data class LoadError(val errorText: UiText) : TaskActionsState()

    data object SaveInProgress : TaskActionsState()
    data class SaveError(val errorText: UiText) : TaskActionsState()
}