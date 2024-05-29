package org.itmo.itmoevent.view.fragments.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout
import org.itmo.itmoevent.R
import org.itmo.itmoevent.databinding.FragmentEventEditTaskBinding
import org.itmo.itmoevent.view.adapters.EditTaskAdapter
import org.itmo.itmoevent.view.fragments.base.BaseFragment
import org.itmo.itmoevent.viewmodel.base.UiText
import org.itmo.itmoevent.viewmodel.edit.EditTasksUIState
import org.itmo.itmoevent.viewmodel.edit.EditTasksViewModel

class EditTasksFragment : BaseFragment<FragmentEventEditTaskBinding>() {

    private var eventId: Int? = null

    private val model: EditTasksViewModel by viewModels {
        EditTasksViewModel.EditTasksViewModelFactory(
            eventId!!,
            application.roleRepository,
            application.taskRepository,
            application.searchRepository
        )
    }

    private var taskAdapter: EditTaskAdapter? = null

    private var listItemListener = object : EditTaskAdapter.EditTasksClickListener {
        override fun onCopyClicked(taskId: Int) {
            showShortToast(getString(R.string.not_yet_implemented))

        }

        override fun onDeleteClicked(taskId: Int) {
            model.deleteTask(taskId)
        }

        override fun onChangeClicked(taskId: Int) {
            showShortToast(getString(R.string.not_yet_implemented))
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
            editTasksExistingRv.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            editTasksExistingRv.adapter = taskAdapter
        }

        registerForm()

        model.uiState.observe(this.viewLifecycleOwner) { state ->
            when (state) {
                EditTasksUIState.LoadingList -> { }

                is EditTasksUIState.TaskList -> {
                    taskAdapter?.tasks = state.tasksList
                }

                is EditTasksUIState.LoadingForm -> { }

                is EditTasksUIState.FormData -> {
                    viewBinding.editTasksForm.run {
                        (editTaskPlace as? MaterialAutoCompleteTextView)?.setSimpleItems(
                            state.placesSearch.toTypedArray()
                        )
                        (editTaskActivity as? MaterialAutoCompleteTextView)?.setSimpleItems(
                            state.activitiesSearch.toTypedArray()
                        )
                        (editTaskExecutor as? MaterialAutoCompleteTextView)?.setSimpleItems(
                            state.orgsSearch.toTypedArray()
                        )
                    }
                }

                is EditTasksUIState.LoadingError -> {
                    showShortToast(state.mess)
                }

                is EditTasksUIState.SubmitBlocked -> {
                    state.errorText.asString(requireContext()).let { text ->
                        if (text.isNotBlank()) {
                            showShortToast(text)
                        }
                    }
                }

                is EditTasksUIState.SaveInProgress -> { }

                is EditTasksUIState.SaveError -> {
                    showShortToast("Ошибка сохранения. Попробуйте еще")
                }
            }

        }

        if (savedInstanceState == null) {
            model.init()
        }

    }

    private fun registerForm() {
        viewBinding.editTasksForm.run {
            model.run {
                bindEditText(editTaskTitle, inputTitle)
                bindEditText(editTaskDesc, inputDesc)
                bindEditText(editTaskDeadline, inputDeadline)
                bindEditText(editTaskNotificationTime, inputRemind)
                bindAutoCompleteTextView(editTaskPlace, inputPlace)
                bindAutoCompleteTextView(editTaskExecutor, inputAssignee)
                bindAutoCompleteTextView(editTaskActivity, inputActivity)

                bindEditTextErrors(editTaskTitleLt, inputTitleError)
                bindEditTextErrors(editTaskDescLt, inputDescError)
                bindEditTextErrors(editTaskDeadlineLt, inputDeadlineError)
                bindEditTextErrors(editTaskNotificationTimeLt, inputRemindError)
            }
        }

        viewBinding.editTasksFormAcceptBtn.setOnClickListener {
            model.submit()
        }
    }

    private fun bindEditText(editText: EditText?, liveData: MutableLiveData<String>) {
        editText?.doOnTextChanged { text, _, _, _ ->
            liveData.value = text.toString()
        }
    }

    private fun bindAutoCompleteTextView(
        autoCompleteTextView: AutoCompleteTextView?,
        liveData: MutableLiveData<Int>
    ) {
        autoCompleteTextView?.setOnItemClickListener { parent, view, position, id ->
            liveData.value = position
        }
    }

    private fun bindEditTextErrors(inputLayout: TextInputLayout, liveData: LiveData<UiText?>) {
        liveData.observe(this.viewLifecycleOwner) {
            inputLayout.error = it?.asString(requireContext()) ?: ""
        }
    }

    companion object {
        const val EVENT_ID_ARG: String = "eventId"
    }
}