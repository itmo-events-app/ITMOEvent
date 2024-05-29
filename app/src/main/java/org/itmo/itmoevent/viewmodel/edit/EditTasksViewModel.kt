package org.itmo.itmoevent.viewmodel.edit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.itmo.itmoevent.R
import org.itmo.itmoevent.model.data.dto.request.TaskRequest
import org.itmo.itmoevent.model.data.entity.enums.TaskStatus
import org.itmo.itmoevent.model.data.entity.search.ActivitySearch
import org.itmo.itmoevent.model.data.entity.search.PlaceSearch
import org.itmo.itmoevent.model.data.entity.search.UserSearch
import org.itmo.itmoevent.model.data.entity.task.Task
import org.itmo.itmoevent.model.repository.RoleRepository
import org.itmo.itmoevent.model.repository.SearchRepository
import org.itmo.itmoevent.model.repository.TaskRepository
import org.itmo.itmoevent.viewmodel.base.UiText
import org.itmo.itmoevent.viewmodel.util.DateInputUtil
import java.time.LocalDateTime

class EditTasksViewModel(
    private val eventId: Int,
    private val roleRepository: RoleRepository,
    private val taskRepository: TaskRepository,
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<EditTasksUIState>()
    val uiState: LiveData<EditTasksUIState>
        get() = _uiState

    private var tasks = mutableListOf<Task>()
    private var orgsSearch = listOf<UserSearch>()
    private var placesSearch = listOf<PlaceSearch>()
    private var activitiesSearch = listOf<ActivitySearch>()

    private var mode = MODE_CREATE

    val inputTitle = MutableLiveData<String>()
    val inputDesc = MutableLiveData<String>()
    val inputDeadline = MutableLiveData<String>()
    val inputRemind = MutableLiveData<String>()
    val inputPlace = MutableLiveData<Int>()
    val inputActivity = MutableLiveData<Int>()
    val inputAssignee = MutableLiveData<Int>()

    val inputTitleError: LiveData<UiText?> = inputTitle.map { validateStringOnBlank(it) }
    val inputDescError: LiveData<UiText?> = inputDesc.map { validateStringOnBlank(it) }

    private var deadlineDateTime: LocalDateTime? = null
    val inputDeadlineError: LiveData<UiText?> = inputDeadline.map {
        validateStringOnBlank(it) ?: DateInputUtil.parseDate(it).let { date ->
            deadlineDateTime = date
            when {
                date == null -> UiText.StringResource(R.string.err_field_date_format)
                !DateInputUtil.isFutureDate(date) -> UiText.StringResource(R.string.err_deadline_in_past)
                else -> null
            }
        }
    }

    private var remindDateTime: LocalDateTime? = null
    val inputRemindError: LiveData<UiText?> = inputRemind.map {
        validateStringOnBlank(it) ?: DateInputUtil.parseDate(it).let { date ->
            remindDateTime = date
            when {
                date == null -> UiText.StringResource(R.string.err_field_date_format)
                !DateInputUtil.isFutureDate(date) -> UiText.StringResource(R.string.err_remind_in_past)
                else -> null
            }
        }
    }

    private fun validateStringOnBlank(string: String) =
        if (string.isBlank()) UiText.StringResource(R.string.err_field_empty) else null

    fun submit() {
        val errorAfterSubmit = validateInput()
        if (errorAfterSubmit != null) {
            _uiState.value = EditTasksUIState.SubmitBlocked(errorAfterSubmit)
            return
        }
        _uiState.value = EditTasksUIState.SaveInProgress

        when (mode) {
            MODE_CREATE -> {
                createTask()
            }

            MODE_CHANGE -> {}
            else -> {}
        }

    }

    private fun createTask() = viewModelScope.launch {
        val taskRequest = formTaskRequest()
        val newId = taskRepository.createTask(taskRequest)

        if (newId != null) {
            tasks.add(formTask(newId, taskRequest))
            _uiState.value = EditTasksUIState.TaskList(tasks)
        } else {
            _uiState.value = EditTasksUIState.LoadingError("Ошибка создания")
        }

    }

    private fun formTaskRequest() =
        TaskRequest(
            activitiesSearch[inputActivity.value ?: 0].id,
            orgsSearch[inputAssignee.value ?: 0].id,
            inputTitle.value!!,
            inputDesc.value!!,
            TaskStatus.NEW,
            placesSearch[inputPlace.value ?: 0].id,
            DateInputUtil.parseLocalDateTime(deadlineDateTime),
            DateInputUtil.parseLocalDateTime(remindDateTime)
        )

    private fun formTask(id: Int, taskRequest: TaskRequest) = Task(id, taskRequest.title)

    private fun validateInput(): UiText? {
        val isTitleValid = noError(inputTitleError, inputTitle)
        val isDescValid = noError(inputDescError, inputDesc)
        val isDeadlineValid = noError(inputDeadlineError, inputDeadline)
        val isRemindValid = noError(inputRemindError, inputRemind)

        val valid = isTitleValid && isDescValid && isDeadlineValid && isRemindValid
        return when {
            !valid -> UiText.DynamicString("")
            remindDateTime!!.isAfter(deadlineDateTime) -> UiText.StringResource(R.string.err_remind_later_than_deadline)
            else -> null
        }
    }

    private fun noError(
        errorLiveDate: LiveData<*>,
        dataLiveData: MutableLiveData<String>
    ): Boolean {
        if (dataLiveData.value == null) {
            dataLiveData.value = ""
        }
        return errorLiveDate.value == null
    }

    fun init() {
        loadTasks()
        loadForm()
    }

    fun deleteTask(taskId: Int) = viewModelScope.launch {
        _uiState.value = EditTasksUIState.LoadingList
        val isSuccessful = taskRepository.deleteTask(taskId)
        if (isSuccessful) {
            tasks.removeIf { it.id == taskId }
            _uiState.value = EditTasksUIState.TaskList(tasks)
        } else {
            _uiState.value = EditTasksUIState.LoadingError("Ошибка удаления")
        }
    }

    private fun loadTasks() = viewModelScope.launch {
        _uiState.value = EditTasksUIState.LoadingList
        val loadedTasks = taskRepository.getEventOrActivityTasks(eventId, true)
        if (loadedTasks == null) {
            _uiState.value = EditTasksUIState.LoadingError("Ошибка загрузки")
        } else {
            tasks = loadedTasks.toMutableList()
            _uiState.value = EditTasksUIState.TaskList(tasks)
        }
    }

    private fun loadForm() = viewModelScope.launch {
        val loadedOrgs = searchRepository.getEventOrgsSearch(eventId)
        val loadedActivities = searchRepository.getEventActivitiesSearch(eventId)
        val loadedPlaces = searchRepository.getPlacesSearch()

        if (loadedOrgs == null || loadedActivities == null || loadedPlaces == null) {
            _uiState.value = EditTasksUIState.LoadingError("Ошибка загрузки")
        } else {
            orgsSearch = loadedOrgs
            placesSearch = loadedPlaces
            val activitiesMutableList = loadedActivities.toMutableList()
            activitiesMutableList.add(0, ActivitySearch(eventId, "--"))
            activitiesSearch = activitiesMutableList
            _uiState.value =
                EditTasksUIState.FormData(orgsSearch.map { "${it.name} ${it.surname}" },
                    placesSearch.map { "${it.name} : ${it.address} : ${it.room}" },
                    activitiesSearch.map { it.title })
        }
    }

    companion object {
        const val MODE_CREATE = 1
        const val MODE_CHANGE = 2
    }


    class EditTasksViewModelFactory(
        private val activityId: Int,
        private val roleRepository: RoleRepository,
        private val taskRepository: TaskRepository,
        private val searchRepository: SearchRepository
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EditTasksViewModel::class.java)) {
                return EditTasksViewModel(
                    activityId,
                    roleRepository,
                    taskRepository,
                    searchRepository
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}