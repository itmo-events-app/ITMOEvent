package org.itmo.itmoevent.view.auth.fragments

import android.content.Intent
import android.content.res.ColorStateList
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
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.itmo.itmoevent.R
import org.itmo.itmoevent.databinding.FragmentLoginBinding
import org.itmo.itmoevent.network.model.LoginRequest
import org.itmo.itmoevent.network.util.ApiResponse
import org.itmo.itmoevent.view.MainActivity
import org.itmo.itmoevent.view.auth.replaceFragment
import org.itmo.itmoevent.viewmodel.AuthViewModel
import org.itmo.itmoevent.viewmodel.CoroutinesErrorHandler
import org.itmo.itmoevent.viewmodel.TokenViewModel

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    private val viewModel: AuthViewModel by viewModels()
    private val tokenViewModel: TokenViewModel by activityViewModels()

    private var email: String = ""
    private var password: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressBar.visibility = View.VISIBLE
        binding.loginLayoutForm.visibility = View.GONE
        val redColor = ContextCompat.getColor(requireContext(), R.color.red_300)
        val colorStateList = ColorStateList.valueOf(redColor)

        //TODO пример, как проверять, есть токен, можно потом переделать
        tokenViewModel.token.observe(viewLifecycleOwner) { token ->
           if (token != null) {
               startActivity(Intent(activity, MainActivity::class.java))
           } else {
               binding.progressBar.visibility = View.GONE
               binding.loginLayoutForm.visibility = View.VISIBLE
           }
        }

        viewModel.loginResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Failure -> {
                    Log.d("api_error", it.errorMessage)
                    showShortToast(it.errorMessage)

                }
                ApiResponse.Loading -> Log.d("api_loading", it.toString())
                is ApiResponse.Success -> {
                    tokenViewModel.saveToken(it.data)
                    val intent = Intent(activity as AppCompatActivity, MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
            }
        }

        binding.changeButton.setOnClickListener {
            val activity = requireActivity() as AppCompatActivity
            activity.replaceFragment(RegistrationFragment())
        }

        binding.loginButton.setOnClickListener {
            if (!checkLogin(email) || !checkPassword(password)) {
                if (!checkLogin(email)) {
                    binding.email.error = "Некорректная почта"
                } else {
                    binding.email.error = null
                }
//                if (!checkPassword(password)) {
//                    binding.password.error = "Некорректный пароль"
//                } else {
//                    binding.password.error = null
//                }
            } else {
                Toast.makeText(context, "Загрузка", Toast.LENGTH_SHORT).show()
                viewModel.login(
//                LoginRequest("333d666@niuitmo.ru", "PaSsWoRd1!"),
                    LoginRequest(email, password),
                    object: CoroutinesErrorHandler {
                        override fun onError(message: String) {
                            Log.d("api_error", message)
                        }
                    }
                )
            }
        }

        binding.forgotPassword.setOnClickListener {
            val activity = requireActivity() as AppCompatActivity
            activity.replaceFragment(ForgotPasswordFragment())
        }


        binding.email.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                email = s.toString() // Обновление значения email при изменении текста
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.password.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                password = s.toString() // Обновление значения email при изменении текста
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

    private fun checkPassword(password: String): Boolean {
        return password.matches("^(?=.*[A-ZА-Я])(?=.*[!@#$%^&*])(?=\\S+$).{8,}$".toRegex())
    }
}
