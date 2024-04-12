package org.itmo.itmoevent.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.itmo.itmoevent.R
import org.itmo.itmoevent.databinding.FragmentProfileSectionBinding
import org.itmo.itmoevent.network.util.ApiResponse
import org.itmo.itmoevent.view.adapters.NotificationAdapter
import org.itmo.itmoevent.viewmodel.CoroutinesErrorHandler
import org.itmo.itmoevent.viewmodel.TokenViewModel
import org.itmo.itmoevent.viewmodel.UserNotificationsViewModel


@AndroidEntryPoint
class ProfileSectionFragment : Fragment(R.layout.fragment_profile_section) {
    private lateinit var binding: FragmentProfileSectionBinding
    private lateinit var adapter: NotificationAdapter

    private val viewModel: UserNotificationsViewModel by viewModels()
    private val tokenViewModel: TokenViewModel by activityViewModels()

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
        }

        viewModel.notificationsLiveData.observe(this.viewLifecycleOwner) {
            adapter.refresh(it)
        }

        viewModel.getUserInfo(object: CoroutinesErrorHandler {
            override fun onError(message: String) {
                Log.d("api", message)
            }
        })

        viewModel.profileResponse.observe(this.viewLifecycleOwner){
            when(it){
                is ApiResponse.Failure -> Log.d("aoi_error", it.toString())
                ApiResponse.Loading -> Log.d("api_loading", it.toString())
                is ApiResponse.Success -> {
                    binding.fio.text = "${it.data.name} ${it.data.surname}"
                    binding.emailText.text = it.data.userInfo!![0].login
                }
            }
        }


        binding.run {
            exitButton.setOnClickListener {
                tokenViewModel.deleteToken()
            }


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
                editName.visibility = View.VISIBLE
                editSurname.visibility =View.VISIBLE
                editEmailText.visibility = View.VISIBLE
                fio.visibility = View.GONE
                emailText.visibility  =View.GONE
                editProfileButtonConfirm.visibility = View.VISIBLE
                editProfileButton.visibility = View.GONE
            }

            editProfileButtonConfirm.setOnClickListener {
                //TODO допилить условия для проверки имени и почты
                if (true) {
                    val newFio = editName.text.toString() + " " + editSurname.text.toString()
                    fio.text = newFio
                    emailText.text = editEmailText.text
                }
                editName.visibility = View.GONE
                editSurname.visibility = View.GONE
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
