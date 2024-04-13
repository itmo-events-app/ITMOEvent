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


class NotificationAdapter(
    private val onNotificationClickListener: OnNotificationClickListener
) : RecyclerView.Adapter<NotificationAdapter.NotificationHolder>() {

    private var notificationsList: List<Notification> = listOf()

    class NotificationHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = bind(item)

        fun bind(notification: Notification, onNotificationClickListener: OnNotificationClickListener) = with(binding) {
            theme.text = notification.title
            val description = (notification.description?.take(40) ?: "") + "..."
            message.text = description

            notificationCard.setOnClickListener {
                notification.isOpen = !notification.isOpen
                if (notification.isOpen)
                    message.text = notification.description
                else
                    message.text = description
                if (!notification.seen!!) {
                    notification.seen = true


                }
                if (notification.seen!!) notificationCard.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.grey_200
                    )
                )

                onNotificationClickListener.onNotificationClicked(notification.id!!)
            }

            if (notification.seen!!) notificationCard.setBackgroundColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.grey_200
                )
            )
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
        holder.bind(notificationsList[position], onNotificationClickListener)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refresh(list: List<NotificationResponse>?) {
        if (list == null) {
            notificationsList = emptyList()
        } else {
            Log.d("FFFFF", "UUUUUUCK")
            notificationsList = list.map { mapNotificationResponseToNotification(it) }
        }
        notifyDataSetChanged()
    }

    interface OnNotificationClickListener {
        fun onNotificationClicked(notificationId: Int)
    }

}
