package org.itmo.itmoevent.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.itmo.itmoevent.R
import org.itmo.itmoevent.databinding.ActivityMainBinding
import org.itmo.itmoevent.view.fragments.EventSectionFragment
import org.itmo.itmoevent.view.fragments.ManagementSectionFragment
import org.itmo.itmoevent.view.fragments.ProfileSectionFragment
import org.itmo.itmoevent.view.fragments.TaskSectionFragment

class MainActivity : AppCompatActivity() {

    private var viewBinding: ActivityMainBinding? = null
    private val navFragmentsMap: Map<Int, Fragment> = mapOf(
        R.id.nav_item_events to EventSectionFragment(),
        R.id.nav_item_tasks to TaskSectionFragment(),
        R.id.nav_item_manage to ManagementSectionFragment(),
        R.id.nav_item_profile to ProfileSectionFragment()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding!!.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.main_fragment_container, navFragmentsMap[R.id.nav_item_events]!!)
                .addToBackStack(null)
                .commit()

            viewBinding?.run {
                mainBottomNavBar.setOnItemSelectedListener { item ->
                    navFragmentsMap[item.itemId]?.run {
                        supportFragmentManager.beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.main_fragment_container, this)
                            .addToBackStack(null)
                            .commit()
                    }
                    true
                }
            }

        }

    }

}
