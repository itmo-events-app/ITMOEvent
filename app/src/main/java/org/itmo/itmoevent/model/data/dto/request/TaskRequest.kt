package org.itmo.itmoevent.model.data.dto.request

import org.itmo.itmoevent.model.data.entity.enums.TaskStatus
import java.util.Date

class TaskRequest (
    val eventId: Int?,
    val assigneeId: Int?,
    val title: String,
    val description: String,
    val taskStatus: TaskStatus,
    val placeId: Int?,
    val deadline: Date?,
    val reminder: Date?
)