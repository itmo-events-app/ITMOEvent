package org.itmo.itmoevent.model.network

class EventImageUrlService {

    companion object {
        private const val IMAGE_URL: String = "http://95.216.146.187:9000"

        fun getEventImageUrl(eventOrActivityId: Int): String =
            "$IMAGE_URL/event-images/$eventOrActivityId.jpg"
    }
}
