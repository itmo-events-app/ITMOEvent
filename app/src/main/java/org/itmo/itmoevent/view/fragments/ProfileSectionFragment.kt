package org.itmo.itmoevent.view.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.itmo.itmoevent.R
import org.itmo.itmoevent.databinding.FragmentProfileSectionBinding
import org.itmo.itmoevent.network.model.NotificationSettingsRequest
import org.itmo.itmoevent.network.model.UserChangeLoginRequest
import org.itmo.itmoevent.network.model.UserChangeNameRequest
import org.itmo.itmoevent.network.model.UserChangePasswordRequest
import org.itmo.itmoevent.network.util.ApiResponse
import org.itmo.itmoevent.view.adapters.NotificationAdapter
import org.itmo.itmoevent.view.auth.LoginActivity
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

        adapter = NotificationAdapter(object : NotificationAdapter.OnNotificationClickListener {
            override fun onNotificationClicked(notificationId: Int) {
                showShortToast(notificationId.toString())
                viewModel.setOneAsSeenNotification(notificationId, object : CoroutinesErrorHandler {
                    override fun onError(message: String) {
                        Log.d("api", message)
                    }
                })
            }
        })
        binding.run {
            notificationRecycler.adapter = adapter
            notificationRecycler.layoutManager = LinearLayoutManager(context)
        }

        viewModel.getAllNotifications(0, 15, object : CoroutinesErrorHandler {
            override fun onError(message: String) {
                Log.d("api", message)
            }
        })

        viewModel.changePasswordResponse.observe(this.viewLifecycleOwner) {
            when (it) {
                is ApiResponse.Failure -> showLongToast(it.errorMessage)
                ApiResponse.Loading -> {}
                is ApiResponse.Success -> {
                    showLongToast("Пароль успешно изменен")
                }
            }
        }

        viewModel.allNotificationsResponse.observe(this.viewLifecycleOwner) {
            when (it) {
                is ApiResponse.Failure -> Toast.makeText(requireContext(), "Проблемы с соединением", Toast.LENGTH_SHORT).show()
                ApiResponse.Loading -> {
                    //TODO ЭКРАН ЗАГРУКЗКИ
                }
                is ApiResponse.Success -> {
                    Log.d("podsos", it.data.toString())
                    adapter.refresh(it.data)
                }
            }
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
                    binding.editName.setText(it.data.name)
                    binding.editSurname.setText(it.data.surname)
                    val email = it.data.userInfo!![0].login
                    binding.editEmailText.setText(email)
                    binding.emailText.text = email
                    binding.mailSwitch.isChecked = it.data.enableEmailNotifications!!
                }
            }
        }


        binding.run {
            exitButton.setOnClickListener {
                tokenViewModel.deleteToken()
                val intent = Intent(activity as AppCompatActivity, LoginActivity::class.java)
                startActivity(intent)
            }



            mailSwitch.setOnCheckedChangeListener {_,isChecked ->
                viewModel.updateNotifications(NotificationSettingsRequest(isChecked, false), object : CoroutinesErrorHandler {
                    override fun onError(message: String) {
                        Log.d("api", message)
                    }
                })
            }

            editPasswordButton.setOnClickListener {
                editCurrentPassword.visibility = View.VISIBLE
                editNewPassword.visibility = View.VISIBLE
                editNewPasswordTwice.visibility = View.VISIBLE
                editPasswordButtonConfirm.visibility = View.VISIBLE
                editPasswordButton.visibility = View.GONE
            }

            editPasswordButtonConfirm.setOnClickListener {
                editCurrentPassword.clearFocus()
                editNewPassword.clearFocus()
                editNewPasswordTwice.clearFocus()
                val oldPassword = editCurrentPassword.text.toString()
                val newPassword = editNewPassword.text.toString()
                val newPasswordTwice = editNewPasswordTwice.text.toString()
                viewModel.changePassword(UserChangePasswordRequest(oldPassword, newPassword, newPasswordTwice), object : CoroutinesErrorHandler {
                    override fun onError(message: String) {
                        Log.d("api", message)
                    }
                })
                editCurrentPassword.setText("")
                editNewPassword.setText("")
                editNewPasswordTwice.setText("")
                editCurrentPassword.visibility = View.GONE
                editNewPassword.visibility = View.GONE
                editNewPasswordTwice.visibility = View.GONE
                editPasswordButtonConfirm.visibility = View.GONE
                editPasswordButton.visibility = View.VISIBLE
            }

            editProfileButton.setOnClickListener {
                editName.visibility = View.VISIBLE
                editSurname.visibility =View.VISIBLE
                editEmailText.visibility = View.VISIBLE
                fio.visibility = View.INVISIBLE
                emailText.visibility  =View.INVISIBLE
                editProfileButtonConfirm.visibility = View.VISIBLE
                editProfileButton.visibility = View.GONE
            }

            editProfileButtonConfirm.setOnClickListener {
                editName.clearFocus()
                editSurname.clearFocus()
                editEmailText.clearFocus()
                var isOk = true
                val oldName = fio.text.toString().substringBefore(" ")
                val oldSurname = fio.text.toString().substringAfter(" ")
                val newName = editName.text.toString()
                val newSurname = editSurname.text.toString()
                val newLogin = editEmailText.text.toString()
                val newFio = "$newName $newSurname"

                if (fio.text.toString() != newFio)  {

                    if (!checkName(newName, newSurname)) {
                        isOk = false
                        editName.setText(oldName)
                        editSurname.setText(oldSurname)
                        showShortToast("Некорректное имя пользователя")
                    } else {
                        fio.text = newFio
                        viewModel.changeName(
                            UserChangeNameRequest(newName, newSurname),
                            object : CoroutinesErrorHandler {
                                override fun onError(message: String) {
                                    Log.d("api", message)
                                }
                            })
                    }
                }

                if (emailText.text.toString() != newLogin) {

                    if (!checkLogin(newLogin)) {
                        isOk = false
                        editEmailText.setText(emailText.text)
                        showShortToast("Некорректная почта")
                    } else {
                        emailText.text = editEmailText.text

                        viewModel.changeLogin(
                            UserChangeLoginRequest(
                                UserChangeLoginRequest.Type.EMAIL,
                                newLogin
                            ), object : CoroutinesErrorHandler {
                                override fun onError(message: String) {
                                    Log.d("api", message)
                                }
                            })
                    }
                }

                if (isOk) {
                    editName.visibility = View.GONE
                    editSurname.visibility = View.GONE
                    editEmailText.visibility = View.GONE
                    fio.visibility = View.VISIBLE
                    emailText.visibility = View.VISIBLE
                    editProfileButton.visibility = View.VISIBLE
                    editProfileButtonConfirm.visibility = View.GONE
                }
            }
        }
    }

    private fun showShortToast(text: String) {
        Toast.makeText(
            context,
            text,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showLongToast(text: String) {
        Toast.makeText(
            context,
            text,
            Toast.LENGTH_LONG
        ).show()
    }

    private fun checkName(name: String, surname: String): Boolean {
        return name.matches("[а-яА-Я]+".toRegex()) && surname.matches("[а-яА-Я]+".toRegex())
    }

    private fun checkLogin(login: String): Boolean {
        return login.matches("^\\w[\\w\\-.]*@(niu|idu.)?itmo\\.ru".toRegex())
    }

}
