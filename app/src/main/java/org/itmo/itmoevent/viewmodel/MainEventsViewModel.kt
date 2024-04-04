package org.itmo.itmoevent.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import org.itmo.itmoevent.model.data.entity.EventShort
import org.itmo.itmoevent.model.repository.EventRepository


class MainEventsViewModel(private val eventsRepository: EventRepository) : ViewModel() {

    val eventsLiveData: LiveData<List<EventShort>?> = liveData {
        val loaded = loadEvents()
        emit(loaded)
    }

    val isEventListLoading = MutableLiveData<Boolean>()

    private suspend fun loadEvents(): List<EventShort>? {
        isEventListLoading.value = true
        val loaded = eventsRepository.getAllEvents()
        isEventListLoading.value = false
        return loaded
    }

    class MainEventsViewModelFactory(
        private val repository: EventRepository
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainEventsViewModel::class.java)) {
                return MainEventsViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}
