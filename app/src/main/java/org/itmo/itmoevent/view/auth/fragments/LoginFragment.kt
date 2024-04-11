package org.itmo.itmoevent.view.auth.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.itmo.itmoevent.databinding.FragmentLoginBinding
import org.itmo.itmoevent.view.MainActivity
import org.itmo.itmoevent.view.auth.replaceFragment
import org.itmo.itmoevent.view.auth.service.UserDatastore
import org.itmo.itmoevent.view.auth.service.models.LoginRequestDto
import org.itmo.itmoevent.view.auth.service.models.LoginRetrofitClient
import org.itmo.itmoevent.view.auth.service.models.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

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

        binding.changeButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                UserDatastore.tokenFlow(requireContext()).collect { token ->
                    if (token != null) {
                        // Значение токена существует, можно его использовать
                        // Например, обновить UI с использованием токена
                        Log.d("TAG", token.toString())

                    } else {
                        // Значение токена отсутствует
                        // Например, показать форму входа или запросить токен

                        Log.d("TAG", "Ничего нет!")
                    }
                }
            }
            val activity = requireActivity() as AppCompatActivity
            activity.replaceFragment(RegistrationFragment())
        }

        binding.loginButton.setOnClickListener {
            val request = LoginRequestDto("333666@niuitmo.ru", "PaSsWoRd1!")
            LoginRetrofitClient.loginService.login(request.login, request.password).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Log.i("DEBUG", response.code().toString())
                    if (response.isSuccessful) {
                        val token = response.body()
                        Log.d("TAG", token.toString())

                        viewLifecycleOwner.lifecycleScope.launch {
                            UserDatastore.saveToken(requireContext(), token)
                            UserDatastore.saveEmail(requireContext(), request.login)
                            UserDatastore.savePassword(requireContext(), request.password)
                        }

                        val intent = Intent(activity as AppCompatActivity, MainActivity::class.java)
                        startActivity(intent)

                    } else {
                        Log.d("TAG", "Error")
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d("TAG", "Ошибка сети: ${t.message}")
                }
            })



        }

        binding.forgotPassword.setOnClickListener {

            val activity = requireActivity() as AppCompatActivity
            activity.replaceFragment(ForgotPasswordFragment())
        }
    }

}
