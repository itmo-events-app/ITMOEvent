package org.itmo.itmoevent.view.fragments.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import org.itmo.itmoevent.databinding.FragmentEventEditTaskBinding
import org.itmo.itmoevent.view.adapters.EditTaskAdapter
import org.itmo.itmoevent.view.fragments.base.BaseFragment
import org.itmo.itmoevent.viewmodel.edit.EditTasksUIState
import org.itmo.itmoevent.viewmodel.edit.EditTasksViewModel

class EditTasksFragment : BaseFragment<FragmentEventEditTaskBinding>() {

    private var eventId: Int? = null

    private val model: EditTasksViewModel by viewModels {
        EditTasksViewModel.EditTasksViewModelFactory(
            eventId!!,
            application.roleRepository,
            application.taskRepository
        )
    }

    private var taskAdapter: EditTaskAdapter? = null

    private var listItemListener = object : EditTaskAdapter.EditTasksClickListener {
        override fun onCopyClicked(taskId: Int) {

        }

        override fun onDeleteClicked(taskId: Int) {
            model.deleteTask(taskId)
        }

        override fun onChangeClicked(taskId: Int) {

        }

    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentEventEditTaskBinding
        get() = { inflater, container, attach ->
            FragmentEventEditTaskBinding.inflate(inflater, container, attach)
        }

    override fun setup(view: View, savedInstanceState: Bundle?) {
        val eventId = requireArguments().getInt(EditEventFragment.EVENT_ID_ARG)
        this.eventId = eventId

        taskAdapter = taskAdapter ?: EditTaskAdapter(listItemListener)
        viewBinding.run {
            editTasksExistingRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            editTasksExistingRv.adapter = taskAdapter
        }


        model.uiState.observe(this.viewLifecycleOwner) { state ->
            when (state) {
                EditTasksUIState.LoadingList -> {
                    showShortToast("Loading")
                }
                is EditTasksUIState.TaskList -> {
                    showShortToast("Loaded")
                    taskAdapter?.tasks = state.tasksList
                }
                is EditTasksUIState.LoadingError -> {
                    showShortToast(state.mess)
                }
            }

        }

        if (savedInstanceState == null) {
            model.init()
        }

    }


    companion object {
        const val EVENT_ID_ARG: String = "eventId"
    }
}