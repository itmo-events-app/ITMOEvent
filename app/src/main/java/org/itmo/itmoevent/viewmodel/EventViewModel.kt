package org.itmo.itmoevent.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.itmo.itmoevent.model.data.entity.Participant
import org.itmo.itmoevent.model.data.entity.UserRole
import org.itmo.itmoevent.model.data.entity.enums.PrivilegeName
import org.itmo.itmoevent.model.network.EventImageUrlService
import org.itmo.itmoevent.model.repository.EventDetailsRepository
import org.itmo.itmoevent.model.repository.RoleRepository
import org.itmo.itmoevent.model.repository.TaskRepository
import org.itmo.itmoevent.viewmodel.ContentLiveDataProvider.ContentItemUIState.*

class EventViewModel(
    private val eventId: Int,
    private val eventDetailsRepository: EventDetailsRepository,
    private val roleRepository: RoleRepository,
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val eventPrivileges = liveData {
        emit(roleRepository.loadEventPrivileges(eventId)?.map { it.name })
    }

    fun markEventParticipant(participantId: Int, isMarked: Boolean) = viewModelScope.launch {
        eventDetailsRepository.updateEventParticipantIsMarked(eventId, participantId, isMarked)
    }

    fun markEventParticipantsAll() = viewModelScope.launch {
        participantsLiveData.value?.let {
            participants
                ?.forEach {
                    eventDetailsRepository.updateEventParticipantIsMarked(eventId, it.id, true)
                }
        }
    }

    val placeLiveData = eventPrivileges.switchMap {
        ContentLiveDataProvider(
            !hasSysPrivilege(PrivilegeName.VIEW_EVENT_PLACE),
            viewModelScope
        ) {
            viewModelScope.async {
                eventDetailsRepository.getEventInfo(eventId)?.placeId?.let {
                    eventDetailsRepository.getShortPlace(it)
                }
            }
        }.contentLiveData
    }

    val activitiesLiveData = eventPrivileges.switchMap {
        ContentLiveDataProvider(
            !hasSysPrivilege(PrivilegeName.VIEW_EVENT_ACTIVITIES),
            viewModelScope
        ) {
            viewModelScope.async { eventDetailsRepository.getActivities(eventId) }
        }.contentLiveData
    }

    val eventInfoLiveData = ContentLiveDataProvider(
        false,
        viewModelScope
    ) {
        viewModelScope.async { eventDetailsRepository.getEventInfo(eventId) }
    }.contentLiveData

    val orgsLiveData = eventPrivileges.switchMap {
        ContentLiveDataProvider(
            !hasEventPrivilege(PrivilegeName.VIEW_ORGANIZER_USERS),
            viewModelScope
        ) {
            viewModelScope.async {
                eventDetailsRepository.getOrgs(eventId)
            }
        }.contentLiveData
    }

    private var participants: List<Participant>? = null
    val participantsLiveData = eventPrivileges.switchMap {
        ContentLiveDataProvider(
            !hasEventPrivilege(PrivilegeName.WORK_WITH_PARTICIPANT_LIST),
            viewModelScope
        ) {
            viewModelScope.async {
                participants = eventDetailsRepository.getParticipants(eventId)
                participants
            }
        }.contentLiveData
    }

    val tasksLiveData = eventPrivileges.switchMap {
        ContentLiveDataProvider(
//            !hasSysPrivilege(PrivilegeName.VIEW_ALL_EVENT_TASKS),
            false,
            viewModelScope
        ) {
            viewModelScope.async {
                taskRepository.getEventOrActivityTasks(eventId)
            }
        }.contentLiveData
    }


    val roleName = MutableLiveData<String>()
    val roleOrganizersList: LiveData<List<UserRole>?> = roleName.map { role ->
        if (orgsLiveData.value is Success<*>) {
            val orgList = (orgsLiveData.value as Success<*>).content as List<UserRole>?
            orgList?.filter {
                it.roleName == role
            }
        } else {
            emptyList()
        }
    }

    val rolesList: LiveData<List<String>> = orgsLiveData.map { orgsState ->
        if (orgsState is Success<*>) {
            val orgList = orgsState.content as List<UserRole>?
            orgList?.map {
                it.roleName
            }?.distinct() ?: emptyList()
        } else {
            emptyList()
        }
    }

    val availableEditEventLiveData: LiveData<Boolean> = eventPrivileges.switchMap {
        liveData {
            if (hasEventPrivilege(PrivilegeName.EDIT_EVENT_INFO)) {
                emit(true)
            }
        }
    }

//    val availableMarkAllLiveData: LiveData<Boolean> = participantsLiveData.switchMap {
//        liveData {
//
//        }
//    }

    val imageUrlLiveData: LiveData<String> = MutableLiveData(
        EventImageUrlService.getEventImageUrl(eventId)
    )

    private fun hasSysPrivilege(sysPrivilegeName: PrivilegeName) =
        roleRepository.systemPrivilegesNames!!.contains(sysPrivilegeName)

    private fun hasEventPrivilege(eventPrivilegeName: PrivilegeName) =
        eventPrivileges.value?.contains(eventPrivilegeName) ?: false


    class EventViewModelFactory(
        private val eventId: Int,
        private val eventDetailsRepository: EventDetailsRepository,
        private val roleRepository: RoleRepository,
        private val taskRepository: TaskRepository
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EventViewModel::class.java)) {
                return EventViewModel(eventId, eventDetailsRepository, roleRepository, taskRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}