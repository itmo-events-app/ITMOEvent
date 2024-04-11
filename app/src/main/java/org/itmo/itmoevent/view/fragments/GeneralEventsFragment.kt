package org.itmo.itmoevent.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import org.itmo.itmoevent.EventApplication
import org.itmo.itmoevent.R
import org.itmo.itmoevent.databinding.FragmentGeneralEventsBinding
import org.itmo.itmoevent.view.adapters.EventAdapter
import org.itmo.itmoevent.viewmodel.EventItemViewModel
import org.itmo.itmoevent.viewmodel.MainEventsViewModel
import org.itmo.itmoevent.viewmodel.MainEventsViewModel.Function
import org.itmo.itmoevent.viewmodel.MainEventsViewModel.Function.*
import java.lang.IllegalStateException


class GeneralEventsFragment : Fragment(R.layout.fragment_general_events),
    EventAdapter.OnEventListClickListener {
    private var viewBinding: FragmentGeneralEventsBinding? = null
    private val eventItemViewModel: EventItemViewModel by activityViewModels()

    private val model: MainEventsViewModel by viewModels {
        val application = requireActivity().application as? EventApplication
            ?: throw IllegalStateException("Application must be EventApplication implementation")
        MainEventsViewModel.MainEventsViewModelFactory(
            application.roleRepository,
            application.eventRepository
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentGeneralEventsBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val eventAdapter = EventAdapter(this)

        viewBinding?.run {
            generalEventsRv.adapter = eventAdapter
            generalEventsRv.layoutManager = LinearLayoutManager(context)

            model.eventsLiveData.observe(this@GeneralEventsFragment.viewLifecycleOwner) { eventList ->
                if (eventList == null) {
                    Toast.makeText(
                        context,
                        getString(R.string.no_found_message),
                        Toast.LENGTH_LONG
                    )
                        .show()
                } else {
                    eventAdapter.eventList = eventList
                }
            }

            model.isEventListLoading.observe(this@GeneralEventsFragment.viewLifecycleOwner) { isLoading ->
                generalEventsRv.isVisible = !isLoading
                genEventsProgressBar.root.isVisible = isLoading
            }

            model.disabledFunctionsLiveData.observe(this@GeneralEventsFragment.viewLifecycleOwner) { disabledFunctions ->
                val funcViewMap = getFuncViewMap(this)
                if (disabledFunctions == null) {
                    showShortToast("Не удалось загрузить привилегии")
                } else {
                    disabledFunctions.forEach { func ->
                        val viewToHide = funcViewMap[func]
                        if (viewToHide != null) {
                            viewToHide.isVisible = false
                        }
                    }
                }
            }

            model.isPrivilegesLoading.observe(this@GeneralEventsFragment.viewLifecycleOwner) { isLoading ->
                generalEventsRv.isVisible = !isLoading
            }
        }
    }

    private fun showShortToast(text: String) {
        Toast.makeText(
            context,
            text,
            Toast.LENGTH_LONG
        ).show()
    }

    private fun getFuncViewMap(binding: FragmentGeneralEventsBinding): Map<Function, View> = mapOf(
        SEE_EVENTS to binding.generalEventsRv,
        FILTER_EVENTS to binding.genEventsFilterCard
    )

    override fun onEventClicked(eventId: Int) {
        eventItemViewModel.selectEventItem(eventId)
    }

}
