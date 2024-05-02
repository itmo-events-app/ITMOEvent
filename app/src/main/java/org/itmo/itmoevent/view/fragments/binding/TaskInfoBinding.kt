package org.itmo.itmoevent.view.fragments.binding

import org.itmo.itmoevent.databinding.FragmentTaskBinding
import org.itmo.itmoevent.model.data.entity.task.Task
import org.itmo.itmoevent.view.util.DisplayDateFormats
import java.time.format.DateTimeFormatter

class TaskInfoBinding : ContentBinding<FragmentTaskBinding, Task> {
    override fun bindContentToView(viewBinding: FragmentTaskBinding, content: Task) {
        viewBinding.run {
            val formatter =
                DateTimeFormatter.ofPattern(DisplayDateFormats.DATE_EVENT_FULL)
            taskTitle.text = content.title
            taskDesc.text = content.desc
            taskStatus.text = content.taskStatus.name
            taskEvent.text = content.eventTitle
            taskActivity.text = content.activityTitle
            taskAssigned.text = getAssigneeString(content.assigneeName, content.assigneeSurname)
            taskDateEnd.text = content.deadline.format(formatter)
            taskDateCreation.text = content.creationTime.format(formatter)
            taskDateRemind.text = content.reminder.format(formatter)
        }
    }

    private fun getAssigneeString(name: String?, surname: String?) =
        if (name == null) {
            "Не выбрано"
        } else {
            "$surname $name"
        }
}