package org.itmo.itmoevent.model.data.entity.task

import org.itmo.itmoevent.model.data.entity.Place
import org.itmo.itmoevent.model.data.entity.PlaceShort
import org.itmo.itmoevent.model.data.entity.enums.TaskStatus
import java.time.LocalDateTime

data class Task (
    val id: Int,
    val title: String,
    val taskStatus: TaskStatus,
    val desc: String,
    val assigneeId: Int?,
    val assigneeName: String?,
    val assigneeSurname: String?,
    val eventId: Int?,
    val activityId: Int?,
    val eventTitle: String?,
    val activityTitle: String?,
    val place: PlaceShort?,
    val creationTime: LocalDateTime,
    val deadline: LocalDateTime,
    val reminder: LocalDateTime
)