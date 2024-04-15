package org.itmo.itmoevent

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import org.itmo.itmoevent.model.network.EventNetworkService
import org.itmo.itmoevent.model.repository.EventActivityRepository
import org.itmo.itmoevent.model.repository.EventDetailsRepository
import org.itmo.itmoevent.model.repository.EventRepository
import org.itmo.itmoevent.model.repository.EventRequestRepository
import org.itmo.itmoevent.model.repository.NotificationRepository
import org.itmo.itmoevent.model.repository.RoleRepository
import org.itmo.itmoevent.model.repository.TaskRepository
import org.itmo.itmoevent.model.repository.UserRepository
import org.itmo.itmoevent.network.util.TokenManager

@HiltAndroidApp
class EventApplication : Application() {

    private val eventNetworkService by lazy {
        EventNetworkService(tokenManager)
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

    private val placeApi by lazy {
        eventNetworkService.placeApi
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

    val eventDetailsRepository by lazy {
        EventDetailsRepository(eventApi, placeApi)
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

    val tokenManager by lazy {
        TokenManager(applicationContext)
    }

}
