package org.itmo.itmoevent.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.GONE
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import org.itmo.itmoevent.EventApplication
import org.itmo.itmoevent.R
import org.itmo.itmoevent.databinding.FragmentEventBinding
import org.itmo.itmoevent.model.data.entity.EventDetails
import org.itmo.itmoevent.view.adapters.EventAdapter
import org.itmo.itmoevent.view.adapters.ParticipantAdapter
import org.itmo.itmoevent.view.adapters.UserAdapter
import org.itmo.itmoevent.view.util.DisplayDateFormats
import org.itmo.itmoevent.viewmodel.EventViewModel
import java.lang.IllegalStateException
import java.time.format.DateTimeFormatter

class EventFragment : Fragment(R.layout.fragment_event) {

    private var viewBinding: FragmentEventBinding? = null
    private var eventId: Int? = null
    private var model: EventViewModel? = null

    private val activitiesAdapter = EventAdapter(object : EventAdapter.OnEventListClickListener {
        override fun onEventClicked(eventId: Int) {
            showShortToast("Clicked!")
        }
    })
    private val orgAdapter = UserAdapter()
    private val participantsAdapter =
        ParticipantAdapter(object : ParticipantAdapter.OnParticipantMarkChangeListener {
            override fun changeMark(participantId: Int, isChecked: Boolean) {
                showShortToast("participant $participantId - $isChecked")
                model?.markEventParticipant(participantId, isChecked)
            }
        })


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentEventBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val eventId = requireArguments().getInt(EVENT_ID_ARG)
        this.eventId = eventId

        val model: EventViewModel by viewModels {
            val application = requireActivity().application as? EventApplication
                ?: throw IllegalStateException("Application must be EventApplication implementation")
            EventViewModel.EventViewModelFactory(eventId, application.eventDetailsRepository)
        }
        this.model = model

        viewBinding?.run {
            eventSubsectionAcivitiesRv.layoutManager = LinearLayoutManager(context)
            eventSubsectionAcivitiesRv.adapter = activitiesAdapter
            eventSubsectionOrganisatorsRv.layoutManager = LinearLayoutManager(context)
            eventSubsectionOrganisatorsRv.adapter = orgAdapter
            eventSubsectionParticipantsRv.layoutManager = LinearLayoutManager(context)
            eventSubsectionParticipantsRv.adapter = participantsAdapter

            eventDescHeader.setOnClickListener {
                switchVisibility(eventDescLong)
            }

            eventDetailsHeader.setOnClickListener {
                switchVisibility(eventDetailsGroup)
            }

            eventSubsectionOrganisatorsRoleSelect.roleEdit.run {
                this.setOnItemClickListener { _, _, _, _ ->
                    model.roleName.value = this.text.toString()
                }
            }

            eventSubsectionParticipantsMarkAll.setOnClickListener {
                model.markEventParticipantsAll()
            }

            eventEditBtn.setOnClickListener {

            }

//            eventToolbar.setOnMenuItemClickListener {
//
//            }

            eventSubsectionsTab.addOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when (tab?.position) {
                        0 -> show(eventSubsectionAcivitiesRv)
                        1 -> show(eventSubsectionOrgGroup)
                        2 -> show(eventSubsectionParticipantsGroup)
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    when (tab?.position) {
                        0 -> hide(eventSubsectionAcivitiesRv)
                        1 -> hide(eventSubsectionOrgGroup)
                        2 -> hide(eventSubsectionParticipantsGroup)
                    }
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {}

            })

            model.run {
                eventDetails.observe(this@EventFragment.viewLifecycleOwner) { details ->
                    if (details != null) {
                        bindDetailsToViews(details)
                    } else {
                        hide(eventContent)
                        showShortToast(getString(R.string.no_found_message))
                    }
                }

                roleOrganizersList.observe(this@EventFragment.viewLifecycleOwner) { users ->
                    orgAdapter.userList = users
                }

                isInitDataLoading.observe(this@EventFragment.viewLifecycleOwner) { isLoading ->
                    if (isLoading) {
                        show(eventProgressBarMain.root)
                        hide(eventContent)
                    } else {
                        show(eventContent)
                        hide(eventProgressBarMain.root)
                    }
                }

            }

        }

    }

    private fun bindDetailsToViews(details: EventDetails) {
        val formatter = DateTimeFormatter.ofPattern(DisplayDateFormats.DATE_EVENT_FULL)

        viewBinding?.run {
            details.event.run {
                eventTitle.text = title
                eventShortDesc.text = shortDesc
                eventDescLong.text = longDesc
                eventChipStatus.text = status
                eventChipTime.text = startDate.format(formatter)
                eventDetailsTimeHold.text =
                    getString(
                        R.string.event_duration,
                        startDate.format(formatter),
                        endDate.format(formatter)
                    )
                eventDetailsTimeRegister.text =
                    getString(
                        R.string.event_duration,
                        regStart.format(formatter),
                        regEnd.format(formatter)
                    )
                eventDetailsTimePrepare.text =
                    getString(
                        R.string.event_duration,
                        preparingStart.format(formatter),
                        preparingEnd.format(formatter)
                    )
                eventDetailsAge.text = participantAgeLowest.toString()
                eventDetailsParticipantsMax.text = participantLimit.toString()
            }

            details.place.run {
                eventChipPlace.text = name
                eventPlaceCard.placeItemTitle.text = name
                eventPlaceCard.placeItemDesc.text = description
                eventPlaceCard.placeItemFormat.text = format
            }

            activitiesAdapter.eventList = details.activities
            (eventSubsectionOrganisatorsRoleSelect.roleEdit as? MaterialAutoCompleteTextView)?.setSimpleItems(
                details.orgRolesMap.keys.toTypedArray()
            )
            participantsAdapter.participantList = details.participants
        }

    }

    private fun show(view: View) {
        view.visibility = VISIBLE
    }

    private fun hide(view: View) {
        view.visibility = GONE
    }

    private fun showShortToast(text: String) {
        Toast.makeText(
            context,
            text,
            Toast.LENGTH_LONG
        ).show()
    }

    private fun switchVisibility(view: View) {
        when (view.visibility) {
            VISIBLE -> view.visibility = GONE
            GONE -> view.visibility = VISIBLE
            INVISIBLE -> view.visibility = VISIBLE
        }
    }

    companion object {
        const val EVENT_ID_ARG: String = "eventId"
    }

}
