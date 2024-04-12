package org.itmo.itmoevent.viewmodel

import androidx.lifecycle.ViewModel
import org.itmo.itmoevent.network.repository.EventRepository
import org.itmo.itmoevent.network.repository.EventRequestRepository
import org.itmo.itmoevent.network.repository.RoleRepository

class UserEventsViewModel(
    private val eventReqRepository: EventRequestRepository,
    private val rolesRepository: RoleRepository,
    private val eventRepository: EventRepository
) : ViewModel() {


}
