package org.itmo.itmoevent.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import org.itmo.itmoevent.R
import org.itmo.itmoevent.databinding.FragmentUserListBinding
import org.itmo.itmoevent.model.data.entity.User
import org.itmo.itmoevent.view.adapters.UserAdapter


class UserListFragment : Fragment() {

    private lateinit var binding: FragmentUserListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = UserAdapter()
        binding.userlistRecycler.adapter = adapter
        binding.userlistRecycler.layoutManager = LinearLayoutManager(context)

        //TODO получение листа -> livedata
        val useList = listOf<User>()
        adapter.refresh(useList)
    }

    companion object {

        @JvmStatic
        fun newInstance() = UserListFragment()
    }
}