package org.itmo.itmoevent.view.auth.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
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

        //TODO пример, как проверять, есть токен, можно потом переделать
//        tokenViewModel.token.observe(viewLifecycleOwner) { token ->
//            if (token != null)
//                startActivity(Intent(activity, MainActivity::class.java))
//        }

        viewModel.loginResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Failure -> Log.d("api_error", it.errorMessage)
                ApiResponse.Loading -> Log.d("api_loading", it.toString())
                is ApiResponse.Success -> {
                    tokenViewModel.saveToken(it.data)
                    val intent = Intent(activity as AppCompatActivity, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        binding.changeButton.setOnClickListener {
            val activity = requireActivity() as AppCompatActivity
            activity.replaceFragment(RegistrationFragment())
        }

        binding.loginButton.setOnClickListener {
            viewModel.login(
                LoginRequest("333d666@niuitmo.ru", "PaSsWoRd1!"),
                object: CoroutinesErrorHandler {
                    override fun onError(message: String) {
                        Log.d("api_error", message)
                    }
                }
            )
        }
    }

}
