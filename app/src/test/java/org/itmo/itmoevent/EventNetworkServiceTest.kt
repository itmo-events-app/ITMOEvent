package org.itmo.itmoevent

import kotlinx.coroutines.runBlocking
import org.itmo.itmoevent.model.network.EventApi
import org.itmo.itmoevent.model.network.EventNetworkService
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class EventNetworkServiceTest {

    private lateinit var api: EventApi

    @Before
    fun setup() {
        api = EventNetworkService().eventApi
    }

    @Test
    fun getEventsTest() {
        runBlocking {
            val response = api.getEvents(null, null, null, null, null)
            Assert.assertEquals(true, response.isSuccessful)
            val events = response.body()
            println("received: " + events.toString())
        }
    }

}