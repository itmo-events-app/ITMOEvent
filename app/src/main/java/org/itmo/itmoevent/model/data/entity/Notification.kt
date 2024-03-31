package org.itmo.itmoevent.model.data.entity

import java.time.LocalDateTime

data class Notification(
    val id: Int,
    val title: String,
    val description: String,
    val readTime: LocalDateTime?
)
