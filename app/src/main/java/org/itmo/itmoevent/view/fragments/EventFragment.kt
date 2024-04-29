package org.itmo.itmoevent.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.squareup.picasso.Picasso
import org.itmo.itmoevent.R
import org.itmo.itmoevent.databinding.EventInfoBinding
import org.itmo.itmoevent.databinding.FragmentEventBinding
import org.itmo.itmoevent.databinding.PlaceItemBinding
import org.itmo.itmoevent.model.data.entity.Event
import org.itmo.itmoevent.model.data.entity.EventShort
import org.itmo.itmoevent.model.data.entity.Participant
import org.itmo.itmoevent.model.data.entity.Place
import org.itmo.itmoevent.model.data.entity.UserRole
import org.itmo.itmoevent.view.adapters.EventAdapter
import org.itmo.itmoevent.view.adapters.ParticipantAdapter
import org.itmo.itmoevent.view.adapters.UserAdapter
import org.itmo.itmoevent.view.fragments.base.BaseFragment
import org.itmo.itmoevent.view.fragments.binding.ContentBinding
import org.itmo.itmoevent.view.fragments.binding.EventInfoContentBinding
import org.itmo.itmoevent.view.fragments.binding.PlaceItemContentBinding
import org.itmo.itmoevent.viewmodel.EventViewModel
import org.itmo.itmoevent.viewmodel.MainViewModel

class EventFragment : BaseFragment<FragmentEventBinding>() {

    private var eventId: Int? = null

    private val mainViewModel: MainViewModel by activityViewModels()

    private val model: EventViewModel by viewModels {
        EventViewModel.EventViewModelFactory(
            eventId!!,
            application.eventDetailsRepository,
            application.roleRepository
        )
    }

    private val eventInfoContentBinding: ContentBinding<EventInfoBinding, Event> by lazy {
        EventInfoContentBinding(requireActivity())
    }

    private val placeContentBinding: ContentBinding<PlaceItemBinding, Place> by lazy {
        PlaceItemContentBinding()
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentEventBinding
        get() = { inflater, container, attach ->
            FragmentEventBinding.inflate(inflater, container, attach)
        }

    private var activitiesAdapter: EventAdapter? = null
    private var orgAdapter: UserAdapter? = null
    private var participantsAdapter: ParticipantAdapter? = null


    override fun setup(view: View, savedInstanceState: Bundle?) {
        val eventId = requireArguments().getInt(EVENT_ID_ARG)
        this.eventId = eventId

        setupRecyclerViews()
        registerViewListeners()
        observeLiveData()
    }

    private fun setupRecyclerViews() {
        activitiesAdapter = activitiesAdapter ?: EventAdapter(::onActivityClicked)
        participantsAdapter = participantsAdapter ?: ParticipantAdapter(::onParticipantCheckChanged)
        orgAdapter = orgAdapter ?: UserAdapter()

        viewBinding.run {
            eventSubsectionAcivitiesRv.layoutManager = LinearLayoutManager(context)
            eventSubsectionAcivitiesRv.adapter = activitiesAdapter
            eventSubsectionOrganisatorsRv.layoutManager = LinearLayoutManager(context)
            eventSubsectionOrganisatorsRv.adapter = orgAdapter
            eventSubsectionParticipantsRv.layoutManager = LinearLayoutManager(context)
            eventSubsectionParticipantsRv.adapter = participantsAdapter
        }
    }

    private fun registerViewListeners() {
        viewBinding.run {
            eventInfo.eventDescHeader.setOnClickListener {
                switchVisibility(eventInfo.eventDescLong)
            }

            eventInfo.eventDetailsHeader.setOnClickListener {
                switchVisibility(eventInfo.eventDetailsGroup)
            }

            eventSubsectionOrganisatorsRoleSelect.roleEdit.run {
                setOnItemClickListener { _, _, _, _ ->
                    model.roleName.value = text.toString()
                }
            }

            eventSubsectionParticipantsMarkAll.setOnClickListener {
                model.markEventParticipantsAll()

                participantsAdapter?.run {
                    participantList = participantList.map {
                        it.visited = true
                        it
                    }
                }
            }

            eventSubsectionsTab.addOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    show(getTabViewByIndex(tab?.position ?: 0))
                    hide(eventSubsectionsEmptyList.root)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    hide(getTabViewByIndex(tab?.position ?: 0))
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {}

            })
        }
    }

    private fun onActivityClicked(id: Int) {
        mainViewModel.selectActivity(id)
    }

    private fun onParticipantCheckChanged(participantId: Int, isChecked: Boolean) {
        showShortToast("participant $participantId - $isChecked")
        model.markEventParticipant(participantId, isChecked)
    }

    private fun observeLiveData() {
        viewBinding.run {
            model.run {
                handleContentItemViewByLiveData(
                    placeLiveData, eventInfo.eventPlaceCard.root,
                    bindContent = ::bindPlace
                )

                handleContentItemViewByLiveData<List<EventShort>>(
                    activitiesLiveData, eventSubsectionAcivitiesRv,
                    eventSubsectionsProgressBar.root,
                    false,
                    { blockTabItem(TAB_ACTIVITIES_INDEX) }
                ) { activities ->
                    viewBinding.run {
                        activitiesAdapter?.eventList = activities
//                        //temp
//                        eventSubsectionAcivitiesRv.isVisible = activities.isNotEmpty()
//                        eventSubsectionsEmptyList.root.isVisible = activities.isEmpty()
                    }
                }

                handleContentItemViewByLiveData<List<Participant>>(
                    participantsLiveData, eventSubsectionParticipantsRv,
                    eventSubsectionsProgressBar.root,
                    false,
                    { blockTabItem(TAB_PARTICIPANTS_INDEX) }
                ) { participants ->
                    viewBinding.run {
                        participantsAdapter?.participantList = participants
                    }
                }

                handleContentItemViewByLiveData<List<UserRole>>(
                    orgsLiveData, eventSubsectionOrgGroup, eventSubsectionsProgressBar.root,
                    false,
                    { blockTabItem(TAB_ORGANIZERS_INDEX) }
                ) { }

                handleContentItemViewByLiveData(
                    eventInfoLiveData, eventContent, eventProgressBarMain.root,
                    bindContent = ::bindEventInfo
                )

                rolesList.observe(this@EventFragment.viewLifecycleOwner) { roles ->
                    (eventSubsectionOrganisatorsRoleSelect.roleEdit as? MaterialAutoCompleteTextView)?.setSimpleItems(
                        roles.toTypedArray()
                    )
                }

                roleOrganizersList.observe(this@EventFragment.viewLifecycleOwner) { orgs ->
                    orgAdapter?.userList = orgs ?: emptyList()
                }

                availableEditEventLiveData.observe(this@EventFragment.viewLifecycleOwner) {
                    show(eventEditBtn)
                }

                imageUrlLiveData.observe(this@EventFragment.viewLifecycleOwner) { url ->
                    Picasso.get().load(url)
                        .fit()
                        .error(R.color.blue_200)
                        .placeholder(R.color.grey_200)
                        .into(viewBinding.eventInfo.eventImage)
                }

            }

        }
    }

    private fun getTabViewByIndex(index: Int): View? =
        viewBinding.run {
            when (index) {
                TAB_ACTIVITIES_INDEX -> eventSubsectionAcivitiesRv
                TAB_ORGANIZERS_INDEX -> eventSubsectionOrgGroup
                TAB_PARTICIPANTS_INDEX -> eventSubsectionParticipantsGroup
                else -> null
            }
        }


    private fun bindEventInfo(event: Event) {
        eventInfoContentBinding.bindContentToView(viewBinding.eventInfo, event)
        if (event.placeId == null) {
            hide(viewBinding.eventInfo.eventChipPlace)
            hide(viewBinding.eventInfo.eventPlaceCard.root)
        }
    }

    private fun bindPlace(place: Place) {
        viewBinding.eventInfo.run {
            placeContentBinding.bindContentToView(eventPlaceCard, place)
            eventChipPlace.text = place.name

            eventPlaceCard.root.setOnClickListener {
                mainViewModel.selectPlace(place.id)
            }
        }
    }

    private fun blockTabItem(index: Int) {
        viewBinding.eventSubsectionsTab.getTabAt(index)?.view?.isClickable = false
    }

    private fun switchVisibility(view: View) {
        view.isVisible = !view.isVisible
    }

    companion object {
        const val EVENT_ID_ARG = "eventId"

        private const val TAB_ACTIVITIES_INDEX = 0
        private const val TAB_ORGANIZERS_INDEX = 1
        private const val TAB_PARTICIPANTS_INDEX = 2
    }

}
