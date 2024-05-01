package org.itmo.itmoevent.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import org.itmo.itmoevent.R
import org.itmo.itmoevent.databinding.FragmentEventSectionBinding
import org.itmo.itmoevent.view.fragments.base.ViewBindingFragment
import org.itmo.itmoevent.viewmodel.EventSectionViewModel


class EventSectionFragment : ViewBindingFragment<FragmentEventSectionBinding>() {

    private val model: EventSectionViewModel by viewModels()
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentEventSectionBinding
        get() = { inflater, container, attach ->
            FragmentEventSectionBinding.inflate(inflater, container, attach)
        }

    private val tabFragmentsMap = mapOf(
        0 to GeneralEventsFragment(),
        1 to UserEventsFragment()
    )

    override fun setup(view: View, savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            childFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.event_section_frag_container, tabFragmentsMap[0]!!)
                .addToBackStack(null)
                .commit()

            viewBinding.eventSectionTagLayout.addOnTabSelectedListener(object :
                OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.position?.let {
                        model.activeSectionIndexLiveData.value = it
                        childFragmentManager.beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.event_section_frag_container, tabFragmentsMap[it]!!)
                            .addToBackStack(null)
                            .commit()
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })

            model.activeSectionIndexLiveData.observe(this.viewLifecycleOwner) { index ->
                viewBinding.eventSectionTagLayout.getTabAt(index)?.select()
            }

        }
    }

}
