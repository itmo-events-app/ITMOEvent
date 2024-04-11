package org.itmo.itmoevent.view.auth.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import org.itmo.itmoevent.databinding.FragmentForgotPasswordBinding
import org.itmo.itmoevent.view.MainActivity
import org.itmo.itmoevent.view.auth.replaceFragment

class ForgotPasswordFragment : Fragment() {
    private lateinit var binding: FragmentForgotPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
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