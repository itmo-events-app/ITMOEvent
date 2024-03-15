package org.itmo.itmoevent.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.itmo.itmoevent.R
import org.itmo.itmoevent.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            openFragment(ProfileMenu.newInstance(), R.id.fragment_holder)
        }
    }

    private fun openFragment(fragment: Fragment, holderId: Int) {
        supportFragmentManager.
        beginTransaction().
        replace(holderId, fragment).
        addToBackStack("profile_menu").
        commit()


    }
}