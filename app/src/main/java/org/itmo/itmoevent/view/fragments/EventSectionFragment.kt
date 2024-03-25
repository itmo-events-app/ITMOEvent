package org.itmo.itmoevent.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import org.itmo.itmoevent.R
import org.itmo.itmoevent.databinding.FragmentEventSectionBinding


class EventSectionFragment : Fragment(R.layout.fragment_event_section) {
    private var viewBinding: FragmentEventSectionBinding? = null
    private val tabFragmentList = listOf(GeneralEventsFragment(), UserEventsFragment())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentEventSectionBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            childFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.event_section_frag_container, tabFragmentList[0])
                .addToBackStack(null)
                .commit()

            viewBinding?.eventSectionTagLayout?.addOnTabSelectedListener(object :
                OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.position?.let {
                        childFragmentManager.beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.event_section_frag_container, tabFragmentList[it])
                            .addToBackStack(null)
                            .commit()
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }
            })
        }

    }

}
