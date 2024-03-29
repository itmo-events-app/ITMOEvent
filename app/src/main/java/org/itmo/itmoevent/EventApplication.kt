package org.itmo.itmoevent

import android.app.Application
import org.itmo.itmoevent.model.network.EventNetworkService
import org.itmo.itmoevent.model.repository.MainEventsRepository

class EventApplication : Application() {

    val recipeApi by lazy {
        EventNetworkService().eventApi
    }

    val mainEventsRepository by lazy {
        MainEventsRepository(recipeApi)
    }

}
