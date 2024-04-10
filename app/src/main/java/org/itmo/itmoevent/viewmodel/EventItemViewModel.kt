package org.itmo.itmoevent.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EventItemViewModel : ViewModel() {

    val eventId = MutableLiveData<Int>()

    fun selectEventItem(id: Int) {
        eventId.value = id
    }

}