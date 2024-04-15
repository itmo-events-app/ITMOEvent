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
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.GONE
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import org.itmo.itmoevent.EventApplication
import org.itmo.itmoevent.R
import org.itmo.itmoevent.databinding.FragmentEventBinding
import org.itmo.itmoevent.model.data.entity.Event
import org.itmo.itmoevent.model.data.entity.EventShort
import org.itmo.itmoevent.model.data.entity.Participant
import org.itmo.itmoevent.model.data.entity.Place
import org.itmo.itmoevent.model.data.entity.UserRole
import org.itmo.itmoevent.view.adapters.EventAdapter
import org.itmo.itmoevent.view.adapters.ParticipantAdapter
import org.itmo.itmoevent.view.adapters.UserAdapter
import org.itmo.itmoevent.view.util.DisplayDateFormats
import org.itmo.itmoevent.viewmodel.ContentItemLiveDataProvider
import org.itmo.itmoevent.viewmodel.ContentItemLiveDataProvider.ContentItemUIState.*
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

    private val tabItemsIndexViewMap by lazy {
        viewBinding?.run {
            mapOf(
                0 to eventSubsectionAcivitiesRv,
                1 to eventSubsectionOrgGroup,
                2 to eventSubsectionParticipantsGroup
            )
        }
    }


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
            EventViewModel.EventViewModelFactory(
                eventId,
                application.eventDetailsRepository,
                application.roleRepository
            )
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

            eventSubsectionsTab.addOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    show(tabItemsIndexViewMap?.get(tab?.position ?: 0))
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    hide(tabItemsIndexViewMap?.get(tab?.position ?: 0))
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {}

            })

            model.run {
                handleContentItemViewByLiveData<Place>(
                    placeLiveData,
                    eventPlaceCard.root
                ) { place ->
                    viewBinding?.run {
                        eventChipPlace.text = place.name
                        eventPlaceCard.placeItemTitle.text = place.name
                        eventPlaceCard.placeItemDesc.text = place.description
                        eventPlaceCard.placeItemFormat.text = place.format
                    }
                }


                handleContentItemViewByLiveData<List<EventShort>>(
                    activitiesLiveData,
                    eventSubsectionAcivitiesRv,
                    eventSubsectionsProgressBar.root,
                    false,
                    { blockTabItem(0) }
                ) { activities ->
                    viewBinding?.run {
                        activitiesAdapter.eventList = activities
                    }
                }

                handleContentItemViewByLiveData<List<Participant>>(
                    participantsLiveData,
                    eventSubsectionParticipantsRv,
                    eventSubsectionsProgressBar.root,
                    false,
                    { blockTabItem(2) }
                ) { participants ->
                    viewBinding?.run {
                        participantsAdapter.participantList = participants
                    }
                }

                handleContentItemViewByLiveData<List<UserRole>>(
                    orgsLiveData,
                    eventSubsectionOrgGroup,
                    eventSubsectionsProgressBar.root,
                    false,
                    { blockTabItem(1) }
                ) { }

                handleContentItemViewByLiveData<Event>(
                    eventInfoLiveData,
                    eventContent,
                    eventProgressBarMain.root
                ) { event ->
                    viewBinding?.run {
                        val formatter =
                            DateTimeFormatter.ofPattern(DisplayDateFormats.DATE_EVENT_FULL)
                        event.run {
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
                    }
                }

                rolesList.observe(this@EventFragment.viewLifecycleOwner) { roles ->
                    (eventSubsectionOrganisatorsRoleSelect.roleEdit as? MaterialAutoCompleteTextView)?.setSimpleItems(
                        roles.toTypedArray()
                    )
                }

                roleOrganizersList.observe(this@EventFragment.viewLifecycleOwner) { orgs ->
                    orgs?.let {
                        orgAdapter.userList = orgs
                    }
                }

            }
        }
    }

    private fun blockTabItem(index: Int) {
        viewBinding?.run {
            eventSubsectionsTab.getTabAt(index)?.view?.isClickable = false
        }
    }

    private fun <T> handleContentItemViewByLiveData(
        livedata: LiveData<ContentItemLiveDataProvider.ContentItemUIState>,
        contentView: View,
        progressBar: View? = null,
        needToShow: Boolean = true,
        ifDisabled: (() -> Unit)? = null,
        bindContent: (T) -> Unit
    ) {
        livedata.observe(this.viewLifecycleOwner) { state ->
            when (state) {
                is Success<*> -> {
                    val content = state.content!! as T
                    bindContent(content)
                    if (needToShow) {
                        show(contentView)
                        progressBar?.let {
                            hide(progressBar)
                        }
                    }
                }

                is Error -> {
                    if (needToShow) {
                        show(contentView)
                        progressBar?.let {
                            hide(progressBar)
                        }
                    }
                    state.errorMessage?.let {
                        showShortToast(it)
                    }
                }

                is Disabled -> {
                    if (needToShow) {
                        hide(contentView)
                    }
                    ifDisabled?.invoke()
                    showShortToast("Blocked")
                }

                is Loading -> {
                    if (needToShow) {
                        hide(contentView)
                        progressBar?.let {
                            show(progressBar)
                        }
                    }
                }

                is Start -> {
                }
            }
        }
    }

    private fun show(view: View?) {
        view?.visibility = VISIBLE
    }

    private fun hide(view: View?) {
        view?.visibility = GONE
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
