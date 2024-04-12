package org.itmo.itmoevent.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import org.itmo.itmoevent.EventApplication
import org.itmo.itmoevent.R
import org.itmo.itmoevent.databinding.FragmentUserEventsBinding
import org.itmo.itmoevent.view.adapters.EventRequestAdapter
import org.itmo.itmoevent.view.adapters.EventAdapter
import org.itmo.itmoevent.viewmodel.UserEventsViewModel
import java.lang.IllegalStateException


class UserEventsFragment : Fragment(R.layout.fragment_user_events) {
    private var viewBinding: FragmentUserEventsBinding? = null

//    private val model: UserEventsViewModel by viewModels {
//        val application = requireActivity().application as? EventApplication
//            ?: throw IllegalStateException("Application must be EventApplication implementation")
//        UserEventsViewModel.UserEventsViewModelFactory(
//            application.eventRequestRepository,
//            application.roleRepository,
//            application.eventRepository
//        )
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentUserEventsBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val requestsAdapter = EventRequestAdapter()
        val eventsAdapter = EventAdapter()

        viewBinding?.run {
            userEventsRequestsRv.layoutManager = LinearLayoutManager(context)
            userEventsRequestsRv.adapter = requestsAdapter

            userEventsRoleEdit.setOnItemClickListener { _, _, position, _ ->
                model.roleNameIndex.value = position
            }
            userEventsProgressBarRoleEvents.progressBar.visibility = GONE

            userEventsOrganizedRv.layoutManager = LinearLayoutManager(context)
            userEventsOrganizedRv.adapter = eventsAdapter
            eventsAdapter.eventList = listOf()
        }

        model.run {
            eventRequestList.observe(this@UserEventsFragment.viewLifecycleOwner) { eventRequests ->
                if (eventRequests.isNullOrEmpty()) {
                    Toast.makeText(
                        context,
                        getString(R.string.no_found_message),
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    requestsAdapter.eventRequests = eventRequests
                }
            }

            rolesList.observe(this@UserEventsFragment.viewLifecycleOwner) { roles ->
                if (roles.isNullOrEmpty()) {
                    Toast.makeText(
                        context,
                        getString(R.string.no_found_message),
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    (viewBinding?.userEventsRoleEdit as? MaterialAutoCompleteTextView)?.setSimpleItems(
                        roles.toTypedArray()
                    )
                }
            }

            roleEventList.observe(this@UserEventsFragment.viewLifecycleOwner) { events ->
                if (events.isNullOrEmpty()) {
                    Toast.makeText(
                        context,
                        getString(R.string.no_found_message),
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    eventsAdapter.eventList = events
                }
            }

            isEventListLoading.observe(this@UserEventsFragment.viewLifecycleOwner) { isLoading ->
                viewBinding?.run {
                    changeVisibilityDependOnLoadingStatus(
                        isLoading,
                        userEventsProgressBarRoleEvents.progressBar,
                        userEventsOrganizedRv
                    )
                }
            }

            isInitDataLoading.observe(this@UserEventsFragment.viewLifecycleOwner) { isLoading ->
                viewBinding?.run {
                    changeVisibilityDependOnLoadingStatus(
                        isLoading,
                        userEventsProgressBarMain.progressBar,
                        userEventsGroupContentAll
                    )
                }
            }

        }

    }

    private fun changeVisibilityDependOnLoadingStatus(
        isLoading: Boolean,
        progressBar: ProgressBar,
        view: View
    ) {
        var progressVisibility = GONE
        var contentVisibility = VISIBLE
        if (isLoading) {
            progressVisibility = VISIBLE
            contentVisibility = GONE
        }
        progressBar.visibility = progressVisibility
        view.visibility = contentVisibility
    }

}
