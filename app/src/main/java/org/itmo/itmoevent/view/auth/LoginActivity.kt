package org.itmo.itmoevent.view.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint

import org.itmo.itmoevent.R
import org.itmo.itmoevent.databinding.ActivityLoginBinding
import org.itmo.itmoevent.view.MainActivity
import org.itmo.itmoevent.view.auth.fragments.LoginFragment
import org.itmo.itmoevent.viewmodel.TokenViewModel


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(LoginFragment())
    }
}

fun AppCompatActivity.replaceFragment(fragment: Fragment){
    val fragmentManager = supportFragmentManager
    val transaction = fragmentManager.beginTransaction()
    transaction.replace(R.id.container,fragment)
    //transaction.addToBackStack(null)
    transaction.commit()
}