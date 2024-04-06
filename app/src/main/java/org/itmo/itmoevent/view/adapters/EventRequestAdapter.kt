package org.itmo.itmoevent.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import org.itmo.itmoevent.databinding.EventsListCreateReqItemBinding
import org.itmo.itmoevent.model.data.entity.EventRequest

class EventRequestAdapter : RecyclerView.Adapter<EventRequestAdapter.RequestViewHolder>() {
    var eventRequests: List<EventRequest> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class RequestViewHolder(val viewBinding: EventsListCreateReqItemBinding) :
        ViewHolder(viewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = EventsListCreateReqItemBinding.inflate(inflater, parent, false)
        return RequestViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return eventRequests.size
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        val eventRequest = eventRequests[position]
        holder.viewBinding.eventReqItemName.text = eventRequest.title
    }

}
