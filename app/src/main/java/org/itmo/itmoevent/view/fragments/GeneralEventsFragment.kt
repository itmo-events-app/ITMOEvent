package org.itmo.itmoevent.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import org.itmo.itmoevent.EventApplication
import org.itmo.itmoevent.R
import org.itmo.itmoevent.databinding.FragmentGeneralEventsBinding
import org.itmo.itmoevent.view.adapters.EventAdapter
import org.itmo.itmoevent.viewmodel.MainEventsViewModel
import java.lang.IllegalStateException


class GeneralEventsFragment : Fragment(R.layout.fragment_general_events) {
    private var viewBinding: FragmentGeneralEventsBinding? = null

    private val model: MainEventsViewModel by viewModels {
        val application = requireActivity().application as? EventApplication
            ?: throw IllegalStateException("Application must be EventApplication implementation")
        MainEventsViewModel.MainEventsViewModelFactory(application.mainEventsRepository)
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

        val eventAdapter = EventAdapter()

        viewBinding?.run {
            generalEventsRv.adapter = eventAdapter
            generalEventsRv.layoutManager = LinearLayoutManager(context)
        }

        model.eventsLiveData.observe(this.viewLifecycleOwner) { eventList ->
            if (eventList == null) {
                Toast.makeText(context, "No events found", Toast.LENGTH_LONG).show()
            } else {
                eventAdapter.eventList = eventList
            }
        }

    }

}
