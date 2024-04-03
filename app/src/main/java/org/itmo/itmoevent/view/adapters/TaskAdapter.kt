package org.itmo.itmoevent.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.itmo.itmoevent.R
import org.itmo.itmoevent.databinding.TaskItemBinding
import org.itmo.itmoevent.databinding.UserItemBinding
import org.itmo.itmoevent.model.data.entity.Task
import java.time.format.DateTimeFormatter

class TaskAdapter: RecyclerView.Adapter<TaskAdapter.TaskHolder>() {

    private var taskList: List<Task> = listOf()

    class TaskHolder(item: View): RecyclerView.ViewHolder(item) {
        private val binding = TaskItemBinding.bind(item)

        fun bind(task: Task) = with(binding) {
            binding.taskName.text = task.name
            val formatter = DateTimeFormatter.ofPattern("dd MMMM, HH:mm")
            val text = "До " + task.deadline.format(formatter)
            binding.taskDeadline.text = text
            binding.taskStatus.text = task.status
            binding.taskEventName.text //TODO НЕСОСТЫКОВКА БД С ТРЕБОВАНИЯМИ
            binding.taskEventActivityName.text //TODO НЕСОСТЫКОВКА БД С ТРЕБОВАНИЯМИ
            itemView.setOnClickListener {
                //TODO Переход на страницу таски
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
        holder.bind(taskList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refresh(list: List<Task>) {
        taskList = list
        notifyDataSetChanged()
    }
}