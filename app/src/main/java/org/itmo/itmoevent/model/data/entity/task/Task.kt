package org.itmo.itmoevent.model.data.entity.task

import org.itmo.itmoevent.model.data.entity.PlaceShort
import org.itmo.itmoevent.model.data.entity.enums.TaskStatus
import java.time.LocalDateTime

data class Task (
    val id: Int,
    val title: String,
    val taskStatus: TaskStatus? = null,
    val desc: String? = null,
    val assigneeId: Int? = null,
    val assigneeName: String? = null,
    val assigneeSurname: String? = null,
    val eventId: Int? = null,
    val activityId: Int? = null,
    val eventTitle: String? = null,
    val activityTitle: String? = null,
    val place: PlaceShort? = null,
    val creationTime: LocalDateTime? = null,
    val deadline: LocalDateTime? = null,
    val reminder: LocalDateTime? = null
)