package org.itmo.itmoevent.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import org.itmo.itmoevent.model.data.entity.EventRequest
import org.itmo.itmoevent.model.data.entity.EventShort
import org.itmo.itmoevent.model.data.entity.Role
import org.itmo.itmoevent.model.repository.EventRepository
import org.itmo.itmoevent.model.repository.RoleRepository
import org.itmo.itmoevent.model.repository.EventRequestRepository
import java.util.stream.Collectors

class UserEventsViewModel(
    private val eventReqRepository: EventRequestRepository,
    private val rolesRepository: RoleRepository,
    private val eventRepository: EventRepository
) : ViewModel() {

    val eventRequestList: LiveData<List<EventRequest>?> = liveData {
        val loaded = getEventsRequests()
        emit(loaded)
    }

    val rolesList: LiveData<List<String>?> = liveData {
        val loaded = getOrgRoles()
        if (loaded == null) {
            emit(null)
        } else {
            emit(loaded.stream()
                .map { it.name }
                .collect(Collectors.toList())
            )
        }
    }

    var roleNameIndex = MutableLiveData<Int>()

    val roleEventList: LiveData<List<EventShort>?> = roleNameIndex.switchMap {
        liveData {
            val list = eventRepository.getUserEventsByRole(rolesList.value?.get(it))
            emit(list)
        }
    }

    private suspend fun getEventsRequests(): List<EventRequest>? {
        return eventReqRepository.getEventsRequests()
    }

    private suspend fun getOrgRoles(): List<Role>? {
        return rolesRepository.getOrgRoles()
    }


    class UserEventsViewModelFactory(
        private val eventRequestRepository: EventRequestRepository,
        private val rolesRepository: RoleRepository,
        private val eventRepository: EventRepository
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserEventsViewModel::class.java)) {
                return UserEventsViewModel(
                    eventRequestRepository,
                    rolesRepository,
                    eventRepository
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}
