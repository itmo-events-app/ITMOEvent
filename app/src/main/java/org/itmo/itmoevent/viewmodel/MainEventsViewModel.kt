package org.itmo.itmoevent.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import org.itmo.itmoevent.model.data.entity.EventShort
import org.itmo.itmoevent.model.data.entity.enums.PrivilegeName
import org.itmo.itmoevent.model.data.entity.enums.PrivilegeName.*
import org.itmo.itmoevent.model.repository.EventRepository
import org.itmo.itmoevent.model.repository.RoleRepository


class MainEventsViewModel(
    private val roleRepository: RoleRepository,
    private val eventsRepository: EventRepository
) : ViewModel() {

    val isEventListLoading = MutableLiveData<Boolean>()
    val isPrivilegesLoading = MutableLiveData<Boolean>()


    val disabledFunctionsLiveData: LiveData<List<Function>?> = liveData {
        isPrivilegesLoading.value = true
        val privileges = roleRepository.getSystemPrivileges()
        if (privileges == null) {
            emit(null)
        } else {
            val disabledFunctions = Function.values().filter { func ->
                !privileges
                    .map { it.name }
                    .contains(mapFuncToPrivilegeName(func))
            }
            emit(disabledFunctions)
            isPrivilegesLoading.value = false
        }
    }

    val eventsLiveData: LiveData<List<EventShort>?> =
        disabledFunctionsLiveData.switchMap { disabled ->
            liveData {
                if (disabled != null && !disabled.contains(Function.SEE_EVENTS)) {
                    val loaded = loadEvents()
                    emit(loaded)
                }
            }
        }

    private suspend fun loadEvents(): List<EventShort>? {
        isEventListLoading.value = true
        val loaded = eventsRepository.getAllEvents()
        isEventListLoading.value = false
        return loaded
    }

    enum class Function {
        SEE_EVENTS,
        FILTER_EVENTS
    }

    private fun mapFuncToPrivilegeName(func: Function): PrivilegeName =
        when (func) {
            Function.SEE_EVENTS -> VIEW_ALL_EVENTS
            Function.FILTER_EVENTS -> SEARCH_EVENTS_AND_ACTIVITIES
        }


    class MainEventsViewModelFactory(
        private val roleRepository: RoleRepository,
        private val eventRepository: EventRepository
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainEventsViewModel::class.java)) {
                return MainEventsViewModel(roleRepository, eventRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
