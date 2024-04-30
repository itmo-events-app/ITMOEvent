package org.itmo.itmoevent.model.data.dto.task

import java.util.Date

data class TaskDto(
    val id: Int,
    val title: String,
    val description: String,
    val taskStatus: String,
    val event: TaskEventDto,
    val assignee: TaskAssigneeDto,
    val place: TaskPlaceDto,
    val creationTime: Date,
    val deadline: Date,
    val reminder: Date
)