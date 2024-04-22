package org.itmo.itmoevent.model.data.entity

data class Participant (
    val id: Int,
    val name: String,
    val email: String,
    val additionalInfo: String,
    var visited: Boolean
)