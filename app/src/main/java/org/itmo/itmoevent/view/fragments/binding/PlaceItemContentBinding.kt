package org.itmo.itmoevent.view.fragments.binding

import org.itmo.itmoevent.databinding.PlaceItemBinding
import org.itmo.itmoevent.model.data.entity.Place
import org.itmo.itmoevent.model.data.entity.PlaceShort

class PlaceItemContentBinding : ContentBinding<PlaceItemBinding, PlaceShort> {
    override fun bindContentToView(viewBinding: PlaceItemBinding, content: PlaceShort) {
        viewBinding.run {
            placeItemTitle.text = content.name
            placeItemDesc.text = content.address
            placeItemFormat.text = content.format
        }
    }

}