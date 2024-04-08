package org.itmo.itmoevent

import android.app.Application
import org.itmo.itmoevent.model.network.EventNetworkService
import org.itmo.itmoevent.model.network.NotificationApi
import org.itmo.itmoevent.model.repository.EventRepository
import org.itmo.itmoevent.model.repository.EventRequestRepository
import org.itmo.itmoevent.model.repository.NotificationRepository
import org.itmo.itmoevent.model.repository.RoleRepository
import org.itmo.itmoevent.model.repository.UserRepository

class EventApplication : Application() {

    private val eventNetworkService = EventNetworkService()

    private val eventApi by lazy {
        eventNetworkService.eventApi
    }

    private val roleApi by lazy {
        eventNetworkService.rolesApi
    }

    private val userApi by lazy {
        eventNetworkService.userApi
    }

    private val notificationApi by lazy {
        eventNetworkService.notificationApi
    }

    val eventRepository by lazy {
        EventRepository(eventApi)
    }

    val eventRequestRepository by lazy {
        EventRequestRepository(eventApi)
    }

    val roleRepository by lazy {
        RoleRepository(roleApi)
    }

    val userRepository by lazy {
        UserRepository(userApi)
    }

    val notificationRepository by lazy {
        NotificationRepository(notificationApi)
    }

}
