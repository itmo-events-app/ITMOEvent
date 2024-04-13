package org.itmo.itmoevent.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.itmo.itmoevent.model.data.entity.EventDetails
import org.itmo.itmoevent.model.data.entity.UserRole
import org.itmo.itmoevent.model.repository.EventDetailsRepository

class EventViewModel(
    private val eventId: Int,
    private val eventDetailsRepository: EventDetailsRepository
) : ViewModel() {

    val eventDetails: LiveData<EventDetails?> = liveData {
        isInitDataLoading.value = true
        val loaded = eventDetailsRepository.eventDetails(eventId)
        isInitDataLoading.value = false
        emit(loaded)
    }

    val roleName = MutableLiveData<String>()
    val roleOrganizersList: LiveData<List<UserRole>> = roleName.map {
        eventDetails.value!!.orgRolesMap[it] ?: emptyList()
    }

    val isInitDataLoading = MediatorLiveData<Boolean>()

    fun markEventParticipant(participantId: Int, isMarked: Boolean) = viewModelScope.launch {
        eventDetailsRepository.updateEventParticipantIsMarked(eventId, participantId, isMarked)
    }

    fun markEventParticipantsAll() = viewModelScope.launch {
        eventDetails.value?.run {
            participants
                .forEach {
                    eventDetailsRepository.updateEventParticipantIsMarked(eventId, it.id, true)
                }
        }
    }

    fun deleteEvent() = viewModelScope.launch {

    }

    class EventViewModelFactory(
        private val eventId: Int, private val eventDetailsRepository: EventDetailsRepository
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EventViewModel::class.java)) {
                return EventViewModel(eventId, eventDetailsRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}