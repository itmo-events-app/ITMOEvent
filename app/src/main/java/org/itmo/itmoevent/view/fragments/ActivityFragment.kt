package org.itmo.itmoevent.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import org.itmo.itmoevent.R
import org.itmo.itmoevent.databinding.EventInfoBinding
import org.itmo.itmoevent.databinding.FragmentActivBinding
import org.itmo.itmoevent.databinding.PlaceItemBinding
import org.itmo.itmoevent.model.data.entity.EventsActivity
import org.itmo.itmoevent.model.data.entity.Place
import org.itmo.itmoevent.model.data.entity.PlaceShort
import org.itmo.itmoevent.model.data.entity.task.TaskShort
import org.itmo.itmoevent.view.adapters.TaskItemAdapter
import org.itmo.itmoevent.view.fragments.base.BaseFragment
import org.itmo.itmoevent.view.fragments.binding.ActivityInfoContentBinding
import org.itmo.itmoevent.view.fragments.binding.ContentBinding
import org.itmo.itmoevent.view.fragments.binding.PlaceItemContentBinding
import org.itmo.itmoevent.viewmodel.EventActivityViewModel
import org.itmo.itmoevent.viewmodel.MainViewModel

class ActivityFragment : BaseFragment<FragmentActivBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentActivBinding
        get() = { inflater, container, attach ->
            FragmentActivBinding.inflate(inflater, container, attach)
        }

    private var activityId: Int? = null
    private val mainViewModel: MainViewModel by activityViewModels()
    private val placeContentBinding: ContentBinding<PlaceItemBinding, PlaceShort> =
        PlaceItemContentBinding()
    private val activityInfoContentBinding: ContentBinding<EventInfoBinding, EventsActivity> by lazy {
        ActivityInfoContentBinding(requireActivity())
    }
    private var tasksAdapter: TaskItemAdapter? = null


    override fun setup(view: View, savedInstanceState: Bundle?) {
        val activityId = requireArguments().getInt(ACTIVITY_ID_ARG)
        this.activityId = activityId

        val model: EventActivityViewModel by viewModels {
            EventActivityViewModel.ActivityViewModelModelFactory(
                activityId,
                application.eventActivityRepository,
                application.placeRepository,
                application.roleRepository,
                application.taskRepository
            )
        }

        tasksAdapter = tasksAdapter ?: TaskItemAdapter(::onTaskClicked)
        viewBinding.activityTasksRv.layoutManager = LinearLayoutManager(context)
        viewBinding.activityTasksRv.adapter = tasksAdapter

        viewBinding.run {
            handleContentItemViewByLiveData(
                model.activityInfoLiveData,
                activityContent,
                activityProgressBarMain.root,
                bindContent = ::bindActivityInfo
            )

            handleContentItemViewByLiveData(
                model.placeLiveData,
                activityInfo.eventPlaceCard.root,
                bindContent = ::bindPlace
            )

            handleContentItemViewByLiveData<List<TaskShort>>(
                model.tasksLiveData, activityTasksGroup,
                bindContent = { tasks ->
                    tasksAdapter?.tasks = tasks
                }
            )

            model.imageUrlLiveData.observe(this@ActivityFragment.viewLifecycleOwner) { url ->
                Picasso.get().load(url)
                    .fit()
                    .error(R.color.blue_200)
                    .placeholder(R.color.grey_200)
                    .into(activityInfo.eventImage)
            }
        }
    }

    private fun onTaskClicked(taskId: Int, eventId: Int) {
        mainViewModel.selectTask(taskId, eventId)
    }

    private fun bindPlace(place: PlaceShort) {
        viewBinding.activityInfo.run {
            placeContentBinding.bindContentToView(eventPlaceCard, place)
            eventChipPlace.text = place.name

            eventPlaceCard.root.setOnClickListener {
                mainViewModel.selectPlace(place.id)
            }
        }
    }

    private fun bindActivityInfo(activity: EventsActivity) {
        activityInfoContentBinding.bindContentToView(viewBinding.activityInfo, activity)

        if (activity.placeId == null) {
            hide(viewBinding.activityInfo.eventChipPlace)
            hide(viewBinding.activityInfo.eventPlaceCard.root)
        }
    }

    companion object {
        const val ACTIVITY_ID_ARG: String = "activityId"
    }

}
