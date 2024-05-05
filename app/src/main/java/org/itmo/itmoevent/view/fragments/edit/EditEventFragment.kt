package org.itmo.itmoevent.view.fragments.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import org.itmo.itmoevent.R
import org.itmo.itmoevent.databinding.FragmentEventEditBinding
import androidx.fragment.app.replace
import org.itmo.itmoevent.view.fragments.base.ViewBindingFragment

class EditEventFragment : ViewBindingFragment<FragmentEventEditBinding>() {

    private var eventId: Int? = null

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentEventEditBinding
        get() = { inflater, container, attach ->
            FragmentEventEditBinding.inflate(inflater, container, attach)
        }

    override fun setup(view: View, savedInstanceState: Bundle?) {
        val eventId = requireArguments().getInt(EVENT_ID_ARG)
        this.eventId = eventId

        if (savedInstanceState == null) {
            val argBundle =
                bundleOf(EditTasksFragment.EVENT_ID_ARG to eventId)

            childFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace<EditTasksFragment>(R.id.event_edit_frag_container, args = argBundle)
                .addToBackStack(null)
                .commit()
        }
    }

    companion object {
        const val EVENT_ID_ARG: String = "eventId"
    }

}