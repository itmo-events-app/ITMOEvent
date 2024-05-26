package org.itmo.itmoevent.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import org.itmo.itmoevent.model.data.entity.enums.PrivilegeName
import org.itmo.itmoevent.model.network.EventImageUrlService
import org.itmo.itmoevent.model.repository.EventActivityRepository
import org.itmo.itmoevent.model.repository.PlaceRepository
import org.itmo.itmoevent.model.repository.RoleRepository
import org.itmo.itmoevent.model.repository.TaskRepository
import org.itmo.itmoevent.viewmodel.base.ContentLiveDataProvider

class EventActivityViewModel(
    private val activityId: Int,
    private val activityRepository: EventActivityRepository,
    private val placeRepository: PlaceRepository,
    private val roleRepository: RoleRepository,
    private val taskRepository: TaskRepository
) : ViewModel() {

//    private val eventPrivileges = liveData {
//        emit(roleRepository.loadEventPrivileges(activityId)?.map { it.name })
//    }

    val activityInfoLiveData = ContentLiveDataProvider(
        false,
        viewModelScope
    ) {
        viewModelScope.async { activityRepository.getActivity(activityId) }
    }.contentLiveData

    val placeLiveData = ContentLiveDataProvider(
        !roleRepository.systemPrivilegesNames!!.contains(PrivilegeName.VIEW_EVENT_PLACE),
        viewModelScope
    ) {
        viewModelScope.async {
            activityRepository.getActivity(activityId)?.placeId?.let {
                placeRepository.getShortPlace(it)
            }
        }
    }.contentLiveData

    val tasksLiveData = ContentLiveDataProvider(
//            !hasSysPrivilege(PrivilegeName.VIEW_ALL_EVENT_TASKS),
        false,
        viewModelScope
    ) {
        viewModelScope.async {
            taskRepository.getEventOrActivityTasksShort(activityId)
        }
    }.contentLiveData

    val imageUrlLiveData: LiveData<String> = MutableLiveData(
        EventImageUrlService.getEventImageUrl(activityId)
    )

    class ActivityViewModelModelFactory(
        private val activityId: Int,
        private val activityRepository: EventActivityRepository,
        private val placeRepository: PlaceRepository,
        private val roleRepository: RoleRepository,
        private val taskRepository: TaskRepository
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EventActivityViewModel::class.java)) {
                return EventActivityViewModel(
                    activityId,
                    activityRepository,
                    placeRepository,
                    roleRepository,
                    taskRepository
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}