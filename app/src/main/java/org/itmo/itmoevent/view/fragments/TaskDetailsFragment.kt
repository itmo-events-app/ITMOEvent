package org.itmo.itmoevent.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.itmo.itmoevent.R
import org.itmo.itmoevent.databinding.FragmentTaskDetailsBinding
import org.itmo.itmoevent.model.data.entity.Task
import org.itmo.itmoevent.network.model.TaskResponse
import org.itmo.itmoevent.network.util.ApiResponse
import org.itmo.itmoevent.viewmodel.CoroutinesErrorHandler
import org.itmo.itmoevent.viewmodel.TaskViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class TaskDetailsFragment : Fragment(R.layout.fragment_task_details) {
    private lateinit var binding: FragmentTaskDetailsBinding
    private val model: TaskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentTaskDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val taskId = arguments?.getInt("taskId",-1)
        model.taskGet(taskId!!, object: CoroutinesErrorHandler {
            override fun onError(message: String) {
                Log.d("api", message)
            }
        })

        model.taskResponse.observe(viewLifecycleOwner) {
            when (it) {
                is ApiResponse.Failure -> {}
                ApiResponse.Loading -> {}
                is ApiResponse.Success -> {
                    val task = it.data
                    binding.run {
                        taskName.text = task.title
                        taskDescription.text = task.description
                        val currentStatus = when(task.taskStatus) {
                            TaskResponse.TaskStatus.NEW -> "Новая"
                            TaskResponse.TaskStatus.IN_PROGRESS -> "В работе"
                            TaskResponse.TaskStatus.EXPIRED -> "Просрочено"
                            TaskResponse.TaskStatus.DONE -> "Выполнено"
                            else -> ""
                        }

                        taskStatusSelect.setText(currentStatus, false)

                        val formatter = DateTimeFormatter.ofPattern("dd MMMM, HH:mm")
                        var text = "Выполнить до " + task.deadline!!.format(formatter)
                        taskDeadline.text = text
                        text = "Уведомим " + task.reminder!!.format(formatter)
                        taskNotificationTime.text = text
                        text = task.place!!.name + ", " + task.place.address
                        taskPlace.text = text
                        taskStatusSelect.setOnItemClickListener { parent, view, positions, id ->
                            val newStatus = "\"" + when (positions) {
                                0 -> TaskResponse.TaskStatus.NEW
                                1 -> TaskResponse.TaskStatus.IN_PROGRESS
                                2 -> TaskResponse.TaskStatus.EXPIRED
                                else -> TaskResponse.TaskStatus.DONE
                            }.toString() + "\""

                            model.taskSetStatus(task.id!!, newStatus, object: CoroutinesErrorHandler {
                                override fun onError(message: String) {
                                    Log.d("api", message)
                                }
                            })
                        }
                    }
                }
            }
        }


    }

    fun getPlace(placeId: Int): String {
        //TODO получить место
        return "Место"
    }
}