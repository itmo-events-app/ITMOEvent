package org.itmo.itmoevent

import android.app.Application
import org.itmo.itmoevent.model.network.EventNetworkService
import org.itmo.itmoevent.model.repository.AuthRepository
import org.itmo.itmoevent.model.repository.EventActivityRepository
import org.itmo.itmoevent.model.repository.EventRepository
import org.itmo.itmoevent.model.repository.EventRequestRepository
import org.itmo.itmoevent.model.repository.NotificationRepository
import org.itmo.itmoevent.model.repository.RoleRepository
import org.itmo.itmoevent.model.repository.TaskRepository
import org.itmo.itmoevent.model.repository.UserRepository


class EventApplication : Application() {

//    lateinit var sessionManager: SessionManager

    private val eventNetworkService = EventNetworkService(null)

    val profileApi by lazy {
        eventNetworkService.profileApi
    }

    val authApi by lazy {
        eventNetworkService.authApi
    }

    val authApiRepository by lazy {
        AuthRepository(authApi, null)
    }

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

    private val taskApi by lazy {
        eventNetworkService.taskApi
    }

    private val eventActivityApi by lazy {
        eventNetworkService.eventActivityApi
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

    val taskRepository by lazy {
        TaskRepository(taskApi)
    }

    val eventActivityRepository by lazy {
        EventActivityRepository(eventActivityApi)
    }

}
