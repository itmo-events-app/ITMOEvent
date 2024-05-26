package org.itmo.itmoevent.model.network

class EventImageUrlService {

    companion object {
        private const val IMAGE_URL: String = "http://192.168.81.31:9000"

        fun getEventImageUrl(eventOrActivityId: Int): String =
            "$IMAGE_URL/event-images/$eventOrActivityId.jpg"
    }
}
