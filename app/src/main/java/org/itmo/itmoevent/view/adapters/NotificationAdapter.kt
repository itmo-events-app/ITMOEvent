package org.itmo.itmoevent.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.itmo.itmoevent.R
import org.itmo.itmoevent.databinding.NotificationsListItemBinding.bind
import org.itmo.itmoevent.model.data.entity.Notification


class NotificationAdapter : RecyclerView.Adapter<NotificationAdapter.NotificationHolder>() {

    private var notificationsList: List<Notification> = listOf()

    class NotificationHolder(item: View): RecyclerView.ViewHolder(item) {
        private val binding = bind(item)

        fun bind(notification: Notification) = with(binding) {
            binding.theme.text = notification.title
            binding.message.text = notification.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notifications_list_item, parent, false)
        return NotificationHolder(view)
    }

    override fun getItemCount(): Int {
        return notificationsList.size
    }

    override fun onBindViewHolder(holder: NotificationHolder, position: Int) {
        holder.bind(notificationsList[position])
    }

    fun refresh(list: List<Notification>) {
        notificationsList = list
        notifyDataSetChanged()
    }
}

//class NotificationAdapter : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {
//
//    var notificationList : List<Notification> = emptyList()
//        set(value) {
//            field = value
//            notifyDataSetChanged()
//        }
//
//    class NotificationViewHolder(val binding: NotificationsListItemBinding) : RecyclerView.ViewHolder(binding.root)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
//        val viewBinding = NotificationsListItemBinding.inflate(inflater, parent, false)
//        return NotificationViewHolder(viewBinding)
//    }
//
//    override fun getItemCount(): Int {
//        return notificationList.size
//    }
//
//    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
//        holder.binding.run {
//            val notification = notificationList[position]
//            theme.text = notification.title
//            message.text = notification.description
//            holder.itemView.setOnClickListener {
//
//            }
//        }
//    }
//}