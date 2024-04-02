package org.itmo.itmoevent.view.auth.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import org.itmo.itmoevent.databinding.FragmentLoginBinding
import org.itmo.itmoevent.view.MainActivity
import org.itmo.itmoevent.view.auth.replaceFragment

import org.itmo.itmoevent.view.auth.service.UserManager
import org.itmo.itmoevent.view.auth.service.models.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


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
            val activity = requireActivity() as AppCompatActivity
            activity.replaceFragment(RegistrationFragment())
        }

        binding.loginButton.setOnClickListener {
            val intent = Intent(activity as AppCompatActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

}
