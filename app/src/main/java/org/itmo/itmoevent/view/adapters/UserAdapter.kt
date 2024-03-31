package org.itmo.itmoevent.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.itmo.itmoevent.R
import org.itmo.itmoevent.databinding.UserItemBinding.bind
import org.itmo.itmoevent.model.data.entity.User

class UserAdapter: RecyclerView.Adapter<UserAdapter.UserHolder>() {

    private var userList: List<User> = listOf()

    class UserHolder(item: View): RecyclerView.ViewHolder(item) {
        private val binding = bind(item)

        fun bind(user: User) = with(binding) {
            var text = "${user.name} ${user.surname}"
            binding.fio.text = text
            //TODO
            text = "Какая-то роль" // Логику ролей нужно смотреть
            binding.role.text =text
            itemView.setOnClickListener {
                //TODO
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return UserHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.bind(userList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refresh(list: List<User>) {
        userList = list
        notifyDataSetChanged()
    }
}