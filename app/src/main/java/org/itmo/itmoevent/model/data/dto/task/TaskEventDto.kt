package org.itmo.itmoevent.model.data.dto.task

data class TaskEventDto (
    val eventId: Int,
    val activityId: Int,
    val eventTitle: String,
    val activityTitle: String
)