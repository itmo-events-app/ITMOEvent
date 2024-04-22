package org.itmo.itmoevent.view.fragments

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.itmo.itmoevent.EventApplication
import org.itmo.itmoevent.R
import org.itmo.itmoevent.databinding.FragmentTaskSectionBinding
import org.itmo.itmoevent.model.data.entity.Task
import org.itmo.itmoevent.network.model.TaskResponse
import org.itmo.itmoevent.network.util.ApiResponse
import org.itmo.itmoevent.view.adapters.OnTaskClickListener
import org.itmo.itmoevent.view.adapters.TaskAdapter
import org.itmo.itmoevent.viewmodel.CoroutinesErrorHandler
import org.itmo.itmoevent.viewmodel.TaskViewModel
import org.itmo.itmoevent.viewmodel.UserNotificationsViewModel
import java.lang.IllegalStateException
import java.time.LocalDateTime


@AndroidEntryPoint
class TaskSectionFragment : Fragment(R.layout.fragment_task_section), OnTaskClickListener {
    private lateinit var binding: FragmentTaskSectionBinding
    private lateinit var adapter: TaskAdapter
    private val model: TaskViewModel by viewModels()
    private var taskList: List<TaskResponse> = emptyList()
    private var activityList: List<TaskResponse> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskSectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TaskAdapter(this)

        binding.run {

            model.isCurrent.observe(viewLifecycleOwner) {
                if (it) {
                    toggleButton.check(R.id.current)
                    current.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.blue_300))
                    current.setTextColor(Color.WHITE)
                    past.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
                    past.setTextColor(Color.BLACK)
                } else {
                    toggleButton.check(R.id.past)
                    past.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.blue_300))
                    past.setTextColor(Color.WHITE)
                    current.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
                    current.setTextColor(Color.BLACK)
                }

                if (model.selectedActivityId.value != -1) {
                    model.taskListShowInEvent(model.selectedActivityId.value!!, subEventTasksGet = true, personalTasksGet = true, coroutinesErrorHandler = object: CoroutinesErrorHandler {
                        override fun onError(message: String) {
                            Log.d("api", message)
                        }
                    })
                } else if (model.selectedEventId.value != -1) {
                    model.taskListShowInEvent(model.selectedEventId.value!!, subEventTasksGet = true, personalTasksGet = true, coroutinesErrorHandler = object: CoroutinesErrorHandler {
                        override fun onError(message: String) {
                            Log.d("api", message)
                        }
                    })
                } else if (model.selectedActivityId.value == -1 && model.selectedEventId.value == -1) {
                    model.taskListShowWhereAssignee(object: CoroutinesErrorHandler {
                        override fun onError(message: String) {
                            Log.d("api", message)
                        }
                    })
                }
            }

            taskRecycler.adapter = adapter
            taskRecycler.layoutManager = LinearLayoutManager(context)

            current.setOnClickListener {
                model.updateIsCurrent(true)
            }

            past.setOnClickListener {
                model.updateIsCurrent(false)
            }

            model.taskListShowWhereAssignee(object: CoroutinesErrorHandler {
                override fun onError(message: String) {
                    Log.d("api", message)
                }
            })

            model.taskListResponse.observe(viewLifecycleOwner) {
                when (it) {
                    is ApiResponse.Failure -> {}
                    ApiResponse.Loading -> {}
                    is ApiResponse.Success -> {
                        taskList = it.data.distinctBy { taskResponse -> taskResponse.event!!.eventTitle}
                        adapter.refresh(it.data)

                        val eventTitleList: List<String> = listOf("Все мероприятия") + it.data.map {
                            it.event!!.eventTitle
                        }.filterNotNull().distinct()

                        val eventsAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, eventTitleList)
                        taskEventsFilterSelect.setAdapter(eventsAdapter)
                        taskActivityFilter.visibility = View.GONE
                    }
                }
            }

            model.selectedEventId.observe(viewLifecycleOwner) {
                if (it != -1) {
                    model.taskListShowInEvent(it, subEventTasksGet = true, personalTasksGet = true, coroutinesErrorHandler = object: CoroutinesErrorHandler {
                        override fun onError(message: String) {
                            Log.d("api", message)
                        }
                    })
                }
                taskActivityFilterSelect.setText("Все активности", false)
            }

            model.selectedActivityId.observe(viewLifecycleOwner) {
                if (it != -1) {
                    model.taskListShowInEvent(it, subEventTasksGet = true, personalTasksGet = true, coroutinesErrorHandler = object: CoroutinesErrorHandler {
                        override fun onError(message: String) {
                            Log.d("api", message)
                        }
                    })
                }
            }

            model.filterResponse.observe(viewLifecycleOwner) {
                when (it) {
                    is ApiResponse.Failure -> {}
                    ApiResponse.Loading -> {}
                    is ApiResponse.Success -> {
                        activityList = it.data.filter { response -> response.event!!.activityId != null }
                        adapter.refresh(it.data)
                        val activityTitleList: List<String> = listOf("Все активности") + it.data.map {item ->
                            item.event!!.activityTitle
                        }.filterNotNull()

                        Log.d("activitylist: ", activityList.toString())

                        taskActivityFilter.visibility = View.VISIBLE

                        val eventActivityAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, activityTitleList)
                        taskActivityFilterSelect.setAdapter(eventActivityAdapter)
                    }
                }
            }

            taskEventsFilterSelect.setOnItemClickListener { parent, view, positions, id ->
                if (taskList.isNotEmpty()) {
                    if (positions > 0) {
                        val eventId = taskList[positions - 1].event!!.eventId
                        model.selectEventId(eventId!!)
                    } else {
                        model.selectActivityId(-1)
                        model.selectEventId(-1)
                        model.taskListShowWhereAssignee(object: CoroutinesErrorHandler {
                            override fun onError(message: String) {
                                Log.d("api", message)
                            }
                        })
                    }
                }
            }

            taskActivityFilterSelect.setOnItemClickListener { parent, view, positions, id ->
                if (activityList.isNotEmpty()) {
                    if (positions > 0) {
                        val activityId = activityList[positions - 1].event!!.activityId
                        model.selectActivityId(activityId!!)
                        Log.d("EventID DDDDDDD", activityId.toString())
                    } else {
                        model.selectActivityId(-1)
                        model.taskListShowInEvent(model.selectedEventId.value!!, subEventTasksGet = true, personalTasksGet = true, coroutinesErrorHandler = object: CoroutinesErrorHandler {
                            override fun onError(message: String) {
                                Log.d("api", message)
                            }
                        })
                    }
                }

            }

        }
    }



//    private fun taskTimeFilter(tasks: List<TaskResponse>): List<TaskResponse> {
//
//        if (model.isCurrent.value!!)
//            return tasks.filter { it.deadline!!.isAfter(LocalDateTime.now()) }
//        else
//            return tasks.filter { it.deadline!!.isBefore(LocalDateTime.now()) }
//    }

    override fun onTaskClick(taskId: Int) {
        val bundle = Bundle().apply { putInt("taskId", taskId) }
        val fragment = TaskDetailsFragment().apply { arguments = bundle }
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun showShortToast(text: String) {
        Toast.makeText(
            context,
            text,
            Toast.LENGTH_SHORT
        ).show()
    }

}
