package org.itmo.itmoevent.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.itmo.itmoevent.databinding.ProfileMenuBinding


class ProfileMenu : Fragment() {

    private lateinit var binding: ProfileMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProfileMenuBinding.inflate(inflater)
        bind()

        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() = ProfileMenu()
    }

    private fun bind() {
        binding.jumpLineCreationRequests.textLine.text = "Запросы на создание мероприятий"
        binding.jumpLineOrganizedEvents.textLine.text = "Организуемыве мероприятия"
        binding.jumpPastEvents.textLine.text = "Прошедшие мероприятия"
        binding.jumpOrganizationalTasks.textLine.text = "Выполнение организаторских задач"
    }


}