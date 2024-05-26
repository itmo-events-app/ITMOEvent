package org.itmo.itmoevent.model.data.entity.task

import org.itmo.itmoevent.model.data.entity.enums.TaskStatus


data class TaskShort (
    val id: Int,
    val title: String,
    val taskStatus: TaskStatus,
    val assigneeName: String?,
    val assigneeSurname: String?
)