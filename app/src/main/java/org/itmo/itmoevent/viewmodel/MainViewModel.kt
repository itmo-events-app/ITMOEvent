package org.itmo.itmoevent.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val eventId = MutableLiveData<Int>()
    val activityId = MutableLiveData<Int>()
    val placeId = MutableLiveData<Int>()
    val taskId = MutableLiveData<Int>()
    val exitIntended = MutableLiveData<Boolean>()

    fun selectEventItem(id: Int) {
        eventId.value = id
    }

    fun selectActivity(id: Int) {
        activityId.value = id
    }

    fun selectPlace(id: Int) {
        placeId.value = id
    }

    fun selectTask(id: Int) {
        taskId.value = id
    }

    fun exit() {
        exitIntended.value = true
    }

}