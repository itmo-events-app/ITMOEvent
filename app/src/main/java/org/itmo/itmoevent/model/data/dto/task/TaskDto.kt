package org.itmo.itmoevent.model.data.dto.task

import org.itmo.itmoevent.model.data.entity.enums.TaskStatus
import java.util.Date

data class TaskDto(
    val id: Int,
    val title: String,
    val description: String,
    val taskStatus: TaskStatus,
    val event: TaskEventDto?,
    val assignee: TaskAssigneeDto?,
    val place: TaskPlaceDto?,
    val creationTime: Date,
    val deadline: Date,
    val reminder: Date
)