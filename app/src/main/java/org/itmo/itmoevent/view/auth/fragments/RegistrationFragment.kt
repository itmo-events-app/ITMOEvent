package org.itmo.itmoevent.view.auth.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import org.itmo.itmoevent.R
import org.itmo.itmoevent.databinding.FragmentLoginBinding
import org.itmo.itmoevent.databinding.FragmentRegistrationBinding
import org.itmo.itmoevent.view.auth.replaceFragment


class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
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

        binding.changeButton.setOnClickListener {
            val activity = requireActivity() as AppCompatActivity
            activity.replaceFragment(LoginFragment())
        }
    }
}