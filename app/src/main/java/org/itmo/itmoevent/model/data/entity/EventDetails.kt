package org.itmo.itmoevent.model.data.entity

data class EventDetails(
    val event: Event,
    val activities: List<EventShort>,
    val orgRolesMap: Map<String, List<UserRole>>,
    val place: Place,
    val participants: List<Participant>
)
