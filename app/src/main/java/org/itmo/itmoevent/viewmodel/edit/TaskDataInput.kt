package org.itmo.itmoevent.viewmodel.edit

import org.itmo.itmoevent.model.data.entity.UserRole
import org.itmo.itmoevent.model.data.entity.enums.TaskStatus
import org.itmo.itmoevent.model.data.entity.search.ActivitySearch
import org.itmo.itmoevent.model.data.entity.search.EventSearch
import java.time.LocalDateTime

data class TaskDataInput(
    var title: String,
    var desc: String,
    var deadlineDateTime: LocalDateTime? = null,
    var remindDateTime: LocalDateTime? = null,
    var status: TaskStatus,
    var assignee: UserRole? = null,
    var event: EventSearch? = null,
    var activity: ActivitySearch? = null
)