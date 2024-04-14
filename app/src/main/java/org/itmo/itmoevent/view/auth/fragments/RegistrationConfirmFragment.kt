package org.itmo.itmoevent.view.auth.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import org.itmo.itmoevent.R
import org.itmo.itmoevent.databinding.FragmentForgotPasswordConfirmationBinding
import org.itmo.itmoevent.databinding.FragmentRegistrationConfirmBinding
import org.itmo.itmoevent.view.auth.replaceFragment


class RegistrationConfirmFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationConfirmBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegistrationConfirmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.forgotPasswordButton.setOnClickListener {
            val activity = requireActivity() as AppCompatActivity
            activity.replaceFragment(LoginFragment())
        }
    }
}