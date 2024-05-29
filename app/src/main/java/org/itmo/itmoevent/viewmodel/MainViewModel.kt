package org.itmo.itmoevent.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val eventId = MutableLiveData<Int>()
    val activityId = MutableLiveData<Int>()
    val placeId = MutableLiveData<Int>()
    val taskEventId = MutableLiveData<Pair<Int,Int>>()
    val editEventId = MutableLiveData<Int>()
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

    fun selectTask(taskId: Int, eventId: Int) {
        taskEventId.value = Pair(taskId, eventId)
    }

    fun selectEditEvent(id: Int) {
        editEventId.value = id
    }

    fun exit() {
        exitIntended.value = true
    }

}