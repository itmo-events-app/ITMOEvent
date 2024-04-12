package org.itmo.itmoevent

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import org.itmo.itmoevent.model.network.EventNetworkService
import org.itmo.itmoevent.model.repository.EventDetailsRepository
import org.itmo.itmoevent.model.repository.EventRepository
import org.itmo.itmoevent.model.repository.EventRequestRepository
import org.itmo.itmoevent.model.repository.RoleRepository

@HiltAndroidApp
class EventApplication : Application() {

    private val eventNetworkService = EventNetworkService()

    private val eventApi by lazy {
        eventNetworkService.eventApi
    }

    private val roleApi by lazy {
        eventNetworkService.rolesApi
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

}
