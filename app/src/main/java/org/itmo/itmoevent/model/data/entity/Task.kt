package org.itmo.itmoevent.model.data.entity

import java.time.LocalDateTime

data class Task(
    val id: Int,
    val assigneeId: Int,
    val assignerId: Int,
    val description: String,
    val status: String,
    val deadline: LocalDateTime,
    val placeId: Int,
    val name: String,
    val notificationDeadline: LocalDateTime
)
