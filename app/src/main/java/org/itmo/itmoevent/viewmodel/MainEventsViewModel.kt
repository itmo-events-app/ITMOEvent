package org.itmo.itmoevent.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import org.itmo.itmoevent.model.data.entity.EventShort
import org.itmo.itmoevent.model.repository.MainEventsRepository


class MainEventsViewModel(private val eventsRepository: MainEventsRepository) : ViewModel() {

    val eventsLiveData: LiveData<List<EventShort>?> = liveData {
        val loaded = loadEvents()
        emit(loaded)
    }

    private suspend fun loadEvents(): List<EventShort>? {
        return eventsRepository.getEvents()
    }

    class MainEventsViewModelFactory(
        private val repository: MainEventsRepository
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
