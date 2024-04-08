package org.itmo.itmoevent.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import org.itmo.itmoevent.EventApplication
import org.itmo.itmoevent.R
import org.itmo.itmoevent.databinding.FragmentProfileSectionBinding
import org.itmo.itmoevent.model.data.entity.Notification
import org.itmo.itmoevent.view.adapters.NotificationAdapter
import org.itmo.itmoevent.view.adapters.UserAdapter
import org.itmo.itmoevent.viewmodel.UserNotificationsViewModel
import java.lang.IllegalStateException


class ProfileSectionFragment : Fragment(R.layout.fragment_profile_section) {
    private lateinit var binding: FragmentProfileSectionBinding
    private lateinit var adapter: NotificationAdapter

    private val model: UserNotificationsViewModel by viewModels {
        val application = requireActivity().application as? EventApplication
            ?: throw IllegalStateException("Application must be EventApplication implementation")
        UserNotificationsViewModel.UserNotificationsViewModelFactory(
            application.userRepository,
            application.notificationRepository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileSectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = NotificationAdapter()
        binding.run {
            notificationRecycler.adapter = adapter
            notificationRecycler.layoutManager = LinearLayoutManager(context)
            //TODO ЭТО ДЛЯ ТЕСТА
            adapter.refresh(listOf(
                Notification(
                    0,
                    "Таскай стулья",
                    "Просим вас заняться перемещением 10 стульев, находящихся в комнате А, в комнату B. Пожалуйста, обеспечьте аккуратное расположение каждого стула в новом месте, чтобы создать уютную обстановку. Наше задание будет считаться завершенным только после того, как все стулья будут успешно перенесены и установлены в комнате B, готовые к использованию."
                    ),
                Notification(1,
                    "Поднять платину",
                    "Плотину надо поднять, рычагом. Я его дам. Канал нужно завалить камнем, камень я не дам. Плотину надо поднять, рычагом. Я его дам. Канал нужно завалить камнем, камень я не дам. Плотину надо поднять, рычагом. Я его дам. Канал нужно завалить камнем, камень я не дам."
                    )
            ))
        }

        //model.notificationsLiveData.observe(this.viewLifecycleOwner) {
        //    adapter.refresh(it)
        //}

        binding.run {
            //Кнопка смены пароля
            editPasswordButton.setOnClickListener {
                editCurrentPassword.visibility = View.VISIBLE
                editNewPassword.visibility = View.VISIBLE
                editNewPasswordTwice.visibility = View.VISIBLE
                editPasswordButtonConfirm.visibility = View.VISIBLE
                editPasswordButton.visibility = View.GONE
            }

            editPasswordButtonConfirm.setOnClickListener {
                editCurrentPassword.visibility = View.GONE
                editNewPassword.visibility = View.GONE
                editNewPasswordTwice.visibility = View.GONE
                editPasswordButtonConfirm.visibility = View.GONE
                editPasswordButton.visibility = View.VISIBLE
                //TODO Поменять пароль на бэке или где он там
            }

            editProfileButton.setOnClickListener {
                editFio.visibility = View.VISIBLE
                editEmailText.visibility = View.VISIBLE
                fio.visibility = View.GONE
                emailText.visibility  =View.GONE
                editProfileButtonConfirm.visibility = View.VISIBLE
                editProfileButton.visibility = View.GONE
            }

            editProfileButtonConfirm.setOnClickListener {
                //TODO допилить условия для проверки имени и почты
                if (true) {
                    fio.text = editFio.text
                    emailText.text = editEmailText.text
                }
                editFio.visibility = View.GONE
                editEmailText.visibility = View.GONE
                fio.visibility = View.VISIBLE
                emailText.visibility  =View.VISIBLE
                editProfileButton.visibility = View.VISIBLE
                editProfileButtonConfirm.visibility = View.GONE
                //TODO ОТПРАВИТЬ НА БЭК
            }
        }
    }
}
