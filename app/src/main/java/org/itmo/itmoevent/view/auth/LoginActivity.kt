package org.itmo.itmoevent.view.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

import org.itmo.itmoevent.R
import org.itmo.itmoevent.databinding.ActivityLoginBinding
import org.itmo.itmoevent.view.MainActivity
import org.itmo.itmoevent.view.auth.fragments.LoginFragment
import org.itmo.itmoevent.view.auth.service.UserDatastore


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        lifecycleScope.launch {
            // Сбор данных из tokenFlow
            UserDatastore.tokenFlow(this@LoginActivity).collect { token ->
                if (token != null && token.isNotBlank()) {
                    // Значение токена существует, можно его использовать
                    Log.d("YourActivity", "Token exists: $token")
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    // Закрываем текущую активность, чтобы пользователь не видел ее
                    finish()
                } else {
                    // Значение токена отсутствует или пустое
                    Log.d("YourActivity", "Token is null or empty")
                    // Установка макета, так как токен отсутствует или пустой
                    setContentView(binding.root)
                    replaceFragment(LoginFragment())
                }
            }
        }


    }
}

fun AppCompatActivity.replaceFragment(fragment: Fragment){
    val fragmentManager = supportFragmentManager
    val transaction = fragmentManager.beginTransaction()
    transaction.replace(R.id.container,fragment)
    //transaction.addToBackStack(null)
    transaction.commit()
}