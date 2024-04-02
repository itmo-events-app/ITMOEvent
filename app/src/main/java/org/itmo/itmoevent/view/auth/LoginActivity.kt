package org.itmo.itmoevent.view.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

import org.itmo.itmoevent.R
import org.itmo.itmoevent.databinding.ActivityLoginBinding
import org.itmo.itmoevent.view.auth.fragments.LoginFragment
import org.itmo.itmoevent.view.auth.fragments.RegistrationFragment


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