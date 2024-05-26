package org.itmo.itmoevent.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EventSectionViewModel : ViewModel() {
    val activeSectionIndexLiveData = MutableLiveData(0)
}