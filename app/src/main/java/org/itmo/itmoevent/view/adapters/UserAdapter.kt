package org.itmo.itmoevent.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import org.itmo.itmoevent.databinding.UserListItemBinding
import org.itmo.itmoevent.model.data.entity.UserRole

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    var userList: List<UserRole> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class UserViewHolder(val binding: UserListItemBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = UserListItemBinding.inflate(inflater, parent, false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.binding.run {
            userItemName.text = "${user.surname} ${user.name}"
            userItemLogin.text = user.roleName
        }
    }

}
