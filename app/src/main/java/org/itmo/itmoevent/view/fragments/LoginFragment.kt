package org.itmo.itmoevent.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import org.itmo.itmoevent.R
import org.itmo.itmoevent.network.model.LoginRequest
import org.itmo.itmoevent.network.util.ApiResponse
import org.itmo.itmoevent.view.MainActivity
import org.itmo.itmoevent.viewmodel.AuthViewModel
import org.itmo.itmoevent.viewmodel.CoroutinesErrorHandler
import org.itmo.itmoevent.viewmodel.TokenViewModel

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private val viewModel: AuthViewModel by viewModels()
    private val tokenViewModel: TokenViewModel by activityViewModels()

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val loginTV = view.findViewById<TextView>(R.id.login_text)

        tokenViewModel.token.observe(viewLifecycleOwner) { token ->
            if (token != null)
                startActivity(Intent(activity, MainActivity::class.java))
        }

        viewModel.loginResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Failure -> loginTV.text = it.errorMessage
                ApiResponse.Loading -> loginTV.text = "Loading"
                is ApiResponse.Success -> {
                    tokenViewModel.saveToken(it.data)
                }
            }
        }

        view.findViewById<Button>(R.id.login_button).setOnClickListener {
            viewModel.login(
                LoginRequest("333666@niuitmo.ru", "PaSsWoRd1!"),
                object: CoroutinesErrorHandler {
                    override fun onError(message: String) {
                        loginTV.text = "Error! $message"
                    }
                }
            )
        }
    }
}