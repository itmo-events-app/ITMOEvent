package org.itmo.itmoevent.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.itmo.itmoevent.R
import org.itmo.itmoevent.databinding.FragmentTaskDetailsBinding
import org.itmo.itmoevent.model.data.entity.Task
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class TaskDetailsFragment : Fragment(R.layout.fragment_task_details) {
    private lateinit var binding: FragmentTaskDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTaskDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val task = getTask()
        binding.run {
            taskName.text = task.name
            taskDescription.text = task.description
            val formatter = DateTimeFormatter.ofPattern("dd MMMM, HH:mm")
            var text = "Выполнить до " + task.deadline.format(formatter)
            binding.taskDeadline.text = text
            text = "Уведомим " + task.notificationDeadline.format(formatter)
            taskPlace.text = getPlace(0)
            taskStatusSelect.setOnItemClickListener { parent, view, positions, id ->
                val newStatus = (view as TextView).text.toString()
                //TODO ОТПРАВИТЬ СТАТУС НА БЭК
            }
        }
    }

    fun getTask(): Task {

        val taskId = arguments?.getInt("taskID",-1)
        //TODO получить таску по id

        return Task(1, 1, 2, "Task 1 description", "Pending", LocalDateTime.now().plusDays(2), 1, "Task 1", LocalDateTime.now().plusDays(1))
    }

    fun getPlace(placeId: Int): String {
        //TODO получить место
        return "Место"
    }
}