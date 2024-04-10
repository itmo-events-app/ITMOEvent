package org.itmo.itmoevent.model.data.dto

data class ParticipantDto (
    val id: Int,
    val name: String,
    val email: String,
    val additionalInfo: String,
    val visited: Boolean,
    val eventId: Int
)
