package org.itmo.itmoevent.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import org.itmo.itmoevent.databinding.FragmentTaskBinding
import org.itmo.itmoevent.databinding.PlaceItemBinding
import org.itmo.itmoevent.model.data.entity.PlaceShort
import org.itmo.itmoevent.model.data.entity.enums.TaskStatus
import org.itmo.itmoevent.model.data.entity.task.Task
import org.itmo.itmoevent.view.fragments.base.BaseFragment
import org.itmo.itmoevent.view.fragments.binding.ContentBinding
import org.itmo.itmoevent.view.fragments.binding.PlaceItemContentBinding
import org.itmo.itmoevent.view.fragments.binding.TaskInfoBinding
import org.itmo.itmoevent.viewmodel.MainViewModel
import org.itmo.itmoevent.viewmodel.task.TaskActionsState
import org.itmo.itmoevent.viewmodel.task.TaskDetailsViewModel

class TaskFragment : BaseFragment<FragmentTaskBinding>() {

    private var taskId: Int? = null
    private var eventId: Int? = null

    private val mainViewModel: MainViewModel by activityViewModels()
    private val model: TaskDetailsViewModel by viewModels {
        TaskDetailsViewModel.TaskViewModelModelFactory(
            taskId!!,
            eventId!!,
            application.taskRepository,
            application.roleRepository
        )
    }

    private val placeContentBinding: ContentBinding<PlaceItemBinding, PlaceShort> =
        PlaceItemContentBinding()
    private val taskContentBinding: ContentBinding<FragmentTaskBinding, Task> =
        TaskInfoBinding()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentTaskBinding
        get() = { inflater, container, attach ->
            FragmentTaskBinding.inflate(inflater, container, attach)
        }

    override fun setup(view: View, savedInstanceState: Bundle?) {
        val taskId = requireArguments().getInt(TASK_ID_ARG)
        val eventId = requireArguments().getInt(EVENT_ID_ARG)
        this.taskId = taskId
        this.eventId = eventId

        viewBinding.run {
            taskGetBtn.setOnClickListener {
                model.getTaskOnExecution()
            }
            taskDeclineBtn.setOnClickListener{
                model.declineTask()
            }
            taskStatusEdit.setOnItemClickListener { parent, view, position, id ->
                model.changeTaskStatus(TaskStatus.valueOf(parent.adapter.getItem(position).toString()))
            }
        }

        model.uiState.observe(this.viewLifecycleOwner) { state ->
            when (state) {
                is TaskActionsState.Data -> {
                    bindTask(state.task)
                    viewBinding.run {
                        taskGetBtn.isVisible = state.isGetTaskEnabled
                        taskDeclineBtn.isVisible = state.isDeclineEnabled
                        taskStatusLt.isVisible = state.isChangeStatusEnabled
                    }
                }

                is TaskActionsState.LoadError -> {
                    showShortToast("LoadError")
                }

                TaskActionsState.LoadInProgress -> {
                    showShortToast("LoadInProgress")
                }

                is TaskActionsState.SaveError -> {
                    showShortToast("SaveError")
                }

                TaskActionsState.SaveInProgress -> {
                    showShortToast("SaveInProgress")
                }
            }
        }

        model.init()
    }

    private fun bindTask(task: Task) {
        viewBinding.run {
            taskContentBinding.bindContentToView(this, task)
            if (task.place == null) {
                taskPlace.root.isVisible = false
            } else {
                placeContentBinding.bindContentToView(taskPlace, task.place)
                taskPlace.root.setOnClickListener {
                    mainViewModel.selectPlace(task.place.id)
                }
            }
        }
    }

    companion object {
        const val TASK_ID_ARG: String = "taskId"
        const val EVENT_ID_ARG: String = "eventId"
    }

}
