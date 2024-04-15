package org.itmo.itmoevent.view.auth.fragments

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.itmo.itmoevent.databinding.FragmentRegistrationBinding
import org.itmo.itmoevent.network.model.LoginRequest
import org.itmo.itmoevent.network.model.RegistrationUserRequest
import org.itmo.itmoevent.network.util.ApiResponse
import org.itmo.itmoevent.view.MainActivity
import org.itmo.itmoevent.view.auth.replaceFragment
import org.itmo.itmoevent.viewmodel.AuthViewModel
import org.itmo.itmoevent.viewmodel.CoroutinesErrorHandler

@AndroidEntryPoint
class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    private val viewModel: AuthViewModel by viewModels()

    private var firstName: String = ""
    private var name: String = ""
    private var email: String = ""
    private var password: String = ""
    private var passwordCheck: String = ""



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.registerResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Failure -> {
                    showShortToast(it.errorMessage)
                }
                ApiResponse.Loading -> Log.d("api_loading", it.toString())
                is ApiResponse.Success -> {
                    val activity = requireActivity() as AppCompatActivity
                    activity.replaceFragment(RegistrationConfirmFragment())
                }
            }
        }

        binding.changeButton.setOnClickListener {
            val activity = requireActivity() as AppCompatActivity
            activity.replaceFragment(LoginFragment())
        }

        binding.signup.setOnClickListener {
            if (!checkLogin(email) || !checkName(firstName) || !checkName(name) || !checkPassword(password)
                || (password != passwordCheck)) {
                if (!checkLogin(email)) {
                    binding.email.error = "Некорректная почта"
                } else {
                    binding.email.error = null
                }
                if (!checkName(firstName)) {
                    binding.firstname.error = "Некорректная фамилия"
                } else {
                    binding.firstname.error = null
                }
                if (!checkName(name)) {
                    binding.name.error = "Некорректное имя"
                } else {
                    binding.name.error = null
                }
                if (!checkPassword(password) || password == "") {
                    if (password == "") {
                        binding.password.error = "Пустое поле"
                    } else {
                        binding.password.error = "Некорректный пароль"
                    }

                } else {
                    binding.password.error = null
                }
                if (password != passwordCheck || passwordCheck == "") {
                    if (password == "") {
                        binding.checkPassword.error = "Пустое поле"
                    } else {
                        binding.checkPassword.error = "Некорректное потдтверждение пароля"
                    }

                } else {
                    binding.checkPassword.error = null
                }


            } else {
                viewModel.register(
                    RegistrationUserRequest(name, firstName, email, RegistrationUserRequest.Type.EMAIL,
                        password, passwordCheck),
                    object: CoroutinesErrorHandler {
                        override fun onError(message: String) {
                            Log.d("api_error", message)
                        }
                    }
                )
            }


        }

        binding.firstname.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                firstName = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.name.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                name = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.email.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                email = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.password.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                password = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.checkPassword.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                passwordCheck = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun showShortToast(text: String) {
        Toast.makeText(
            context,
            text,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun checkLogin(login: String): Boolean {
        return login.matches("^\\w[\\w\\-.]*@(niu|idu.)?itmo\\.ru".toRegex())
    }

    private fun checkName(name: String): Boolean {
        return name.matches("[а-яА-Я]+".toRegex())
    }

    private fun checkPassword(password: String): Boolean {
        return password.matches("^(?=.*[A-ZА-Я])(?=.*[!@#$%^&*])(?=\\S+$).{8,}$".toRegex())
    }

}