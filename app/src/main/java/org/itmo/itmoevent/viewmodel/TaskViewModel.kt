package org.itmo.itmoevent.viewmodel

import androidx.lifecycle.ViewModel
import org.itmo.itmoevent.network.repository.EventActivityRepository
import org.itmo.itmoevent.network.repository.EventRepository
import org.itmo.itmoevent.network.repository.TaskRepository

class TaskViewModel(
    private val taskRepository: TaskRepository,
    private val eventRepository: EventRepository,
    private val eventActivityRepository: EventActivityRepository
): ViewModel() {

}