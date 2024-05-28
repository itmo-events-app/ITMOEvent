package org.itmo.itmoevent.model.network

import org.itmo.itmoevent.config.NetworkSettings

class EventImageUrlService {

    companion object {
        fun getEventImageUrl(eventOrActivityId: Int): String =
            "${NetworkSettings.IMAGES_SOURCE_URL}/event-images/$eventOrActivityId.jpg"
    }
}
