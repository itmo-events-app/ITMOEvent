package org.itmo.itmoevent.view.adapters

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import org.itmo.itmoevent.R
import org.itmo.itmoevent.databinding.TaskItemBinding
import org.itmo.itmoevent.network.model.TaskResponse
import java.time.format.DateTimeFormatter

class TaskAdapter(private val listener: OnTaskClickListener): RecyclerView.Adapter<TaskAdapter.TaskHolder>() {

    private var taskList: List<TaskResponse> = listOf()

    class TaskHolder(item: View): RecyclerView.ViewHolder(item) {
        private val binding = TaskItemBinding.bind(item)

        fun bind(task: TaskResponse, listener: OnTaskClickListener) = with(binding) {
            taskName.text = task.title
            val formatter = DateTimeFormatter.ofPattern("dd MMMM, HH:mm")
            val text = "До " + task.deadline!!.format(formatter)
            taskEventName.visibility = View.VISIBLE
            taskEventActivityName.visibility = View.VISIBLE
            if (task.event == null) {
                taskEventName.text = ""
                taskEventName.visibility = View.GONE
                taskEventActivityName.visibility = View.GONE
            } else {
                taskEventName.text = task.event.eventTitle
                if (task.event.activityTitle == null) {
                    taskEventActivityName.visibility = View.GONE
                } else {
                    taskEventActivityName.text = task.event.activityTitle
                }
            }

            val currentStatus = when(task.taskStatus) {
                TaskResponse.TaskStatus.NEW -> "Новая"
                TaskResponse.TaskStatus.IN_PROGRESS -> "В работе"
                TaskResponse.TaskStatus.EXPIRED -> "Просрочено"
                TaskResponse.TaskStatus.DONE -> "Выполнено"
                else -> ""
            }

            val statusColor = when(task.taskStatus) {
                TaskResponse.TaskStatus.DONE -> R.color.green_400
                TaskResponse.TaskStatus.EXPIRED -> R.color.red_400
                else -> R.color.blue_400
            }

            taskStatus.setTextColor(ContextCompat.getColor(itemView.context, statusColor))


            taskDeadline.text = text
            taskStatus.text = currentStatus

            taskCardClicker.setOnClickListener {
                listener.onTaskClick(task.id!!)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskHolder(view)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        val task = taskList[position]
        holder.bind(task, listener)

    }

    @SuppressLint("NotifyDataSetChanged")
    fun refresh(list: List<TaskResponse>?) {
        taskList = list ?: emptyList()
        notifyDataSetChanged()
    }
}