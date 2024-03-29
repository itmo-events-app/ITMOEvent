package org.itmo.itmoevent.model.data.dto

import java.util.Date

data class EventShortDto(
    val id: Int,
    val title: String,
    val shortDesc: String,
    val place: String,
    val status: String,
    val start: Date,
    val end: Date,
    val format: String
)
