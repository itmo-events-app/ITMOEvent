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
import org.itmo.itmoevent.EventApplication
import org.itmo.itmoevent.R
import org.itmo.itmoevent.databinding.FragmentTaskSectionBinding
import org.itmo.itmoevent.model.data.entity.Task
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

//        model.taskLiveData.observe(this.viewLifecycleOwner) {
//            adapter.refresh(it)
//        }

        binding.run {

            taskRecycler.adapter = adapter
            taskRecycler.layoutManager = LinearLayoutManager(context)
//            adapter.refresh(getTaskList())

            current.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.blue_300))

            current.setOnClickListener {
                toggleButton.check(R.id.current)
                current.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.blue_300))
                current.setTextColor(Color.WHITE)
                past.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
                past.setTextColor(Color.BLACK)
//                adapter.refresh(getTaskList())
            }

            past.setOnClickListener {
                toggleButton.check(R.id.past)
                past.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.blue_300))
                past.setTextColor(Color.WHITE)
                current.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
                current.setTextColor(Color.BLACK)
//                adapter.refresh(getTaskList())
            }

            // TODO НОРМАЛЬНО ПОЛУЧАТЬ ИВЕНТЫ
            //val events = listOf("все","Ивент 1", "Ивент 2", "Ивент 3")
            //val eventsAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, events)

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
                        adapter.refresh(it.data)

                        val eventTitleList: List<String> = it.data.map {
                            it.event!!.eventTitle
                        }.filterNotNull()

                        val eventsAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, eventTitleList)
                        taskEventsFilterSelect.setAdapter(eventsAdapter)
                        taskActivityFilter.visibility = View.GONE
                    }
                }
            }

            taskEventsFilterSelect.setOnItemClickListener { parent, view, positions, id ->
                val event = (view as TextView).text.toString() //фильтр ивента тут
                if (positions == 0) {
                    taskActivityFilter.visibility = View.GONE
                } else {
                    taskActivityFilter.visibility = View.VISIBLE
                    val eventActivity = listOf("все","Активность 1", "Активность 2", "Активность 3")
                    val eventActivityAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, eventActivity)

                    taskActivityFilterSelect.setOnItemClickListener {parent, view, positions, id ->
                        val activity = (view as TextView).text.toString() //фильтр активности тут
                        //TODO ПОЛУЧИТЬ СПИСОК С ФИЛЬТРОМ
                        Toast.makeText(requireContext(), activity, Toast.LENGTH_SHORT).show()
                    }

                }
                val tasks = getTaskList()
                //TODO ПОЛУЧИТЬ СПИСОК С ФИЛЬТРОМ
                Toast.makeText(requireContext(), event, Toast.LENGTH_SHORT).show()

            }
        }
    }



    fun getTaskList(): List<Task> {
        val tasks = listOf(
            Task(1, 1, 2, "Task 1 description", "Pending", LocalDateTime.now().plusDays(2), 1, "Task 1", LocalDateTime.now().plusDays(1)),
            Task(2, 2, 1, "Task 2 description", "Completed", LocalDateTime.now().minusDays(3), 2, "Task 2", LocalDateTime.now().minusDays(2)),
            Task(3, 1, 2, "Task 3 description", "In Progress", LocalDateTime.now().plusDays(1), 3, "Task 3", LocalDateTime.now().plusDays(1)),
            Task(4, 2, 1, "Task 4 description", "Pending", LocalDateTime.now().minusDays(4), 4, "Task 4", LocalDateTime.now().minusDays(3)),
            Task(5, 1, 2, "Task 5 description", "Completed", LocalDateTime.now().plusDays(2), 5, "Task 5", LocalDateTime.now().plusDays(2))
        )

        if (binding.toggleButton.checkedButtonId == binding.current.id)
            return tasks.filter { it.deadline.isAfter(LocalDateTime.now()) }
        else
            return tasks.filter { it.deadline.isBefore(LocalDateTime.now()) }
    }

    override fun onTaskClick(taskId: Int) {
        val bundle = Bundle().apply { putInt("taskId", taskId) }
        val fragment = TaskDetailsFragment().apply { arguments = bundle }
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }


}
