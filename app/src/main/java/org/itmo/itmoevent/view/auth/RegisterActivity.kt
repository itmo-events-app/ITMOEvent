package org.itmo.itmoevent.view.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import org.itmo.itmoevent.R
import org.itmo.itmoevent.databinding.ActivityRegisterBinding
import org.itmo.itmoevent.view.MainActivity
class RegisterActivity: AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.buttonRegister.setOnClickListener {
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()
            signUp(email, password)
        }

        binding.buttonToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Registration success, update UI with the registered user's information
                    Toast.makeText(baseContext, "Регистрация прошла успешно!",
                        Toast.LENGTH_SHORT).show()
                    // Optionally, you can navigate to another activity after registration
                    // For example:
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                } else {
                    // If registration fails, display a message to the user.
                    Toast.makeText(baseContext, "Неправильные данные",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}