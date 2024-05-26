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
import org.itmo.itmoevent.model.data.entity.task.Task
import org.itmo.itmoevent.view.fragments.base.BaseFragment
import org.itmo.itmoevent.view.fragments.binding.ContentBinding
import org.itmo.itmoevent.view.fragments.binding.PlaceItemContentBinding
import org.itmo.itmoevent.view.fragments.binding.TaskInfoBinding
import org.itmo.itmoevent.viewmodel.MainViewModel
import org.itmo.itmoevent.viewmodel.TaskDetailsViewModel

class TaskFragment : BaseFragment<FragmentTaskBinding>() {

    private var taskId: Int? = null

    private val mainViewModel: MainViewModel by activityViewModels()
    private val model: TaskDetailsViewModel by viewModels {
        TaskDetailsViewModel.TaskViewModelModelFactory(
            taskId!!,
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
        this.taskId = taskId

        viewBinding.run {
            handleContentItemViewByLiveData(
                model.taskDetailsLiveData,
                taskContent,
                progressBar.root,
                bindContent = ::bindTask
            )
        }
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
    }

}
