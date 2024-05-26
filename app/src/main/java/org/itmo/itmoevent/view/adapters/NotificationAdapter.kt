package org.itmo.itmoevent.view.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import org.itmo.itmoevent.R
import org.itmo.itmoevent.databinding.NotificationsListItemBinding.bind
import org.itmo.itmoevent.model.data.entity.Notification
import org.itmo.itmoevent.model.data.entity.mapNotificationResponseToNotification
import org.itmo.itmoevent.network.model.NotificationResponse
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class NotificationAdapter(
    private val onNotificationClickListener: OnNotificationClickListener,
    private val listener: OnTaskClickListener
) : RecyclerView.Adapter<NotificationAdapter.NotificationHolder>() {

    private var notificationsList: List<Notification> = listOf()

    class NotificationHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = bind(item)

        fun bind(notification: Notification, onNotificationClickListener: OnNotificationClickListener, listener: OnTaskClickListener) = with(binding) {
            theme.text = notification.title
            var description = (notification.description?.take(40) ?: "")
            if (notification.description!!.length > 40) description += "..."
            message.text = description
            val formatter = DateTimeFormatter.ofPattern("dd MMMM, HH:mm")
            binding.sendTime.text = notification.sentTime!!.format(formatter)

            notificationCard.setBackgroundColor(0)


            notificationCard.setOnClickListener {
                notification.isOpen = !notification.isOpen
                if (notification.isOpen)
                    message.text = notification.description
                else
                    message.text = description

                notificationCard.setBackgroundColor(ContextCompat.getColor(notificationCard.context, R.color.grey_200))

                Log.d("Task", "Task ${notification.taskId}")
                notification.taskId?.let { it1 -> listener.onTaskClick(it1) }
                Log.d("TO READ", "is READ")
                onNotificationClickListener.onNotificationClicked(notification.id!!)


            }

            if (notification.seen == true) {
                Log.d("notification", notification.toString())
                notificationCard.setBackgroundColor(ContextCompat.getColor(notificationCard.context, R.color.grey_200))
            }

            notification.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.notifications_list_item, parent, false)
        return NotificationHolder(view)
    }

    override fun getItemCount(): Int {
        return notificationsList.size
    }

    override fun onBindViewHolder(holder: NotificationHolder, position: Int) {
        holder.bind(notificationsList[position], onNotificationClickListener, listener)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refresh(list: List<NotificationResponse>?) {
        if (list == null) {
            notificationsList = emptyList()
        } else {
            notificationsList = emptyList()
            notificationsList = list.map { mapNotificationResponseToNotification(it) }
        }
        notifyDataSetChanged()
    }

    interface OnNotificationClickListener {
        fun onNotificationClicked(notificationId: Int)
    }

}
