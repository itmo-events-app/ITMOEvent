package org.itmo.itmoevent.model.data.entity

import org.itmo.itmoevent.model.data.entity.enums.PrivilegeName

data class Privilege (
    val id: Int,
    val name: PrivilegeName,
    val description: String
)