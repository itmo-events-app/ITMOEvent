package org.itmo.itmoevent.model.data.dto.taskShort

import org.itmo.itmoevent.model.data.entity.enums.TaskStatus

data class TaskShortDto(
    val id: Int,
    val title: String,
    val taskStatus: TaskStatus,
    val assignee: TaskAssigneeShortDto?
)