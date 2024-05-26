package org.itmo.itmoevent.model.data.entity

import org.itmo.itmoevent.network.model.NotificationResponse
import java.time.LocalDateTime

data class Notification(
    val id: Int?,
    val title: String?,
    val description: String?,
    var seen: Boolean? = false,
    val sentTime: LocalDateTime? = null,
    var isOpen: Boolean = false,
    var taskId: Int? = null
)

fun mapNotificationResponseToNotification(notification: NotificationResponse): Notification {
    return Notification(
        notification.id,
        notification.title,
        notification.description,
        notification.seen,
        notification.sentTime,
        taskId = extractTaskNumber(notification.link)
    )
}

fun extractTaskNumber(url: String?): Int? {
    val regex = """.*/task[s]?/(\d+)""".toRegex()
    val matchResult = url?.let { regex.find(it) }
    return matchResult?.groups?.get(1)?.value?.toIntOrNull()
}