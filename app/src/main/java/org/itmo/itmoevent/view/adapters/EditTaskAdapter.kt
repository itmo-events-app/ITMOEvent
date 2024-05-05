package org.itmo.itmoevent.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.itmo.itmoevent.databinding.EditTasksListItemBinding
import org.itmo.itmoevent.model.data.entity.task.Task

class EditTaskAdapter(private val clickListener: EditTasksClickListener) :
    RecyclerView.Adapter<EditTaskAdapter.EditTaskViewHolder>() {

    var tasks: List<Task> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class EditTaskViewHolder(val binding: EditTasksListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditTaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = EditTasksListItemBinding.inflate(inflater, parent, false)
        return EditTaskViewHolder(viewBinding)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: EditTaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.binding.run {
            editTasksItemTitle.text = task.title
            editTasksItemCopyBtn.setOnClickListener { clickListener.onCopyClicked(task.id) }
            editTasksItemDeleteBtn.setOnClickListener {
                clickListener.onDeleteClicked(task.id)
            }
            editTasksItemEditBtn.setOnClickListener { clickListener.onChangeClicked(task.id) }
        }
    }

    interface EditTasksClickListener {
        fun onCopyClicked(taskId: Int)
        fun onDeleteClicked(taskId: Int)
        fun onChangeClicked(taskId: Int)
    }

}