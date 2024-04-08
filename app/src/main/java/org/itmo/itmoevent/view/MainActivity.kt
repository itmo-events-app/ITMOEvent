package org.itmo.itmoevent.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.replace
import org.itmo.itmoevent.R
import org.itmo.itmoevent.databinding.ActivityMainBinding
import org.itmo.itmoevent.view.fragments.EventFragment
import org.itmo.itmoevent.view.fragments.EventSectionFragment
import org.itmo.itmoevent.view.fragments.ManagementSectionFragment
import org.itmo.itmoevent.view.fragments.ProfileSectionFragment
import org.itmo.itmoevent.view.fragments.TaskSectionFragment
import org.itmo.itmoevent.viewmodel.EventItemViewModel

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

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.main_fragment_container, navFragmentsMap[R.id.nav_item_events]!!)
                .addToBackStack(BACK_STACK_TAB_TAG)
                .commit()

            viewBinding?.run {
                mainBottomNavBar.setOnItemSelectedListener { item ->
                    supportFragmentManager.popBackStack(
                        BACK_STACK_TAB_TAG,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                    navFragmentsMap[item.itemId]?.run {
                        supportFragmentManager.beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.main_fragment_container, this)
                            .addToBackStack(BACK_STACK_TAB_TAG)
                            .commit()
                    }
                    true
                }
            }

            eventItemViewModel.eventId.observe(this) {
                supportFragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.main_fragment_container, EventFragment())
                    .addToBackStack(BACK_STACK_DETAILS_TAG)
                    .commit()
            }

        }

    }

    companion object {
        private const val BACK_STACK_TAB_TAG: String = "tagSection"
        private const val BACK_STACK_DETAILS_TAG: String = "eventDetails"
    }

}
