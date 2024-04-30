package org.itmo.itmoevent.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import org.itmo.itmoevent.R
import org.itmo.itmoevent.databinding.TaskListItem2Binding
import org.itmo.itmoevent.model.data.entity.TaskShort
import org.itmo.itmoevent.model.data.entity.enums.TaskStatus

class TaskItemAdapter(private val onTaskClickListener: (Int) -> Unit) :
    RecyclerView.Adapter<TaskItemAdapter.TaskViewHolder>() {

    var tasks: List<TaskShort> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class TaskViewHolder(val binding: TaskListItem2Binding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TaskListItem2Binding.inflate(inflater, parent, false)
        return TaskViewHolder(binding)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.binding.run {
            title.text = task.title
            chipExecutor.text = getAssigneeString(task.assigneeName, task.assigneeSurname)
            status.text = task.taskStatus.name
            statusRibbon.setBackgroundResource(getStatusColorResource(task.taskStatus))

            this.root.setOnClickListener {
                onTaskClickListener.invoke(task.id)
            }
        }
    }

    private fun getStatusColorResource(status: TaskStatus) =
        when (status) {
            TaskStatus.NEW -> R.color.grey_200
            TaskStatus.IN_PROGRESS -> R.color.blue_200
            TaskStatus.DONE -> R.color.green_300
            TaskStatus.EXPIRED -> R.color.red_200
        }

    private fun getAssigneeString(name: String?, surname: String?): String {
        return if (name == null) {
            "Без исполнителя"
        } else {
            "Исполнитель: $name $surname"
        }
    }
}