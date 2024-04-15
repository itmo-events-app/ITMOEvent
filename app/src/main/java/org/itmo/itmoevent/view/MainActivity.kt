package org.itmo.itmoevent.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.itmo.itmoevent.EventApplication
import dagger.hilt.android.AndroidEntryPoint
import org.itmo.itmoevent.R
import org.itmo.itmoevent.databinding.ActivityMainBinding
import org.itmo.itmoevent.view.fragments.EventFragment
import org.itmo.itmoevent.view.fragments.EventSectionFragment
import org.itmo.itmoevent.view.fragments.ManagementSectionFragment
import org.itmo.itmoevent.view.fragments.ProfileSectionFragment
import org.itmo.itmoevent.view.fragments.TaskSectionFragment
import org.itmo.itmoevent.viewmodel.EventItemViewModel
import java.lang.IllegalStateException

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var viewBinding: ActivityMainBinding? = null
    private val navFragmentsMap: Map<Int, Fragment> = mapOf(
        R.id.nav_item_events to EventSectionFragment(),
        R.id.nav_item_tasks to TaskSectionFragment(),
        R.id.nav_item_manage to ManagementSectionFragment(),
        R.id.nav_item_profile to ProfileSectionFragment()
    )

    private val eventItemViewModel: EventItemViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding!!.root)

        val application = application as? EventApplication
            ?: throw IllegalStateException("Application must be EventApplication implementation")
        val roleRepository = application.roleRepository

        lifecycleScope.launch {
            val privileges = roleRepository.loadSystemPrivileges()
            if (privileges == null) {
                Toast.makeText(
                    this@MainActivity,
                    "Не удалось получить системные привилегии",
                    Toast.LENGTH_SHORT
                ).show()
                this@MainActivity.finish()
            } else {

                supportFragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.main_fragment_container, navFragmentsMap[R.id.nav_item_events]!!)
                    .addToBackStack(BACK_STACK_TAB_TAG)
                    .commit()


                if (savedInstanceState == null) {
                    viewBinding?.run {
                        mainBottomNavBar.setOnItemSelectedListener { item ->
                            val selectedFragment = navFragmentsMap[item.itemId]

                            val currentFragment = supportFragmentManager.findFragmentById(R.id.main_fragment_container)

                            if (selectedFragment != currentFragment) {
                                val transaction = supportFragmentManager.beginTransaction()
                                    .setReorderingAllowed(true)
                                    .replace(R.id.main_fragment_container, selectedFragment!!)

                                if (currentFragment != null) {
                                    transaction.addToBackStack(null)
                                }

                                transaction.commit()
                            }

                            true
                        }
                    }

                    eventItemViewModel.eventId.observe(this@MainActivity) {
                        val argBundle =
                            bundleOf(EventFragment.EVENT_ID_ARG to eventItemViewModel.eventId.value)
                        supportFragmentManager.beginTransaction()
                            .setReorderingAllowed(true)
                            .replace<EventFragment>(R.id.main_fragment_container, args = argBundle)
                            .addToBackStack(BACK_STACK_DETAILS_TAG)
                            .commit()
                    }
                }

            }
        }


    }

    companion object {
        private const val BACK_STACK_TAB_TAG: String = "tagSection"
        private const val BACK_STACK_DETAILS_TAG: String = "eventDetails"
    }

}
