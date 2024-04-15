package org.itmo.itmoevent.model.data.entity

import org.itmo.itmoevent.network.model.NotificationResponse
import java.time.LocalDateTime

data class Notification(
    val id: Int?,
    val title: String?,
    val description: String?,
    var seen: Boolean? = false,
    var isOpen: Boolean = false
)

fun mapNotificationResponseToNotification(notification: NotificationResponse): Notification {
    return Notification(
        notification.id,
        notification.title,
        notification.description,
        notification.seen
    )
}