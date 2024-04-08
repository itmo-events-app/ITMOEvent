package org.itmo.itmoevent.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import org.itmo.itmoevent.model.data.entity.Notification
import org.itmo.itmoevent.model.data.entity.User
import org.itmo.itmoevent.model.repository.NotificationRepository
import org.itmo.itmoevent.model.repository.UserRepository

class UserNotificationsViewModel(
    private val userRepository: UserRepository,
    private val notificationRepository: NotificationRepository
): ViewModel() {


    val userLiveData: LiveData<User?> = liveData {
        val loaded = loadUser()
        emit(loaded)
    }
    val notificationsLiveData: LiveData<List<Notification>?> = liveData {
        val loaded = loadNotifications()
        emit(loaded)
    }

    private suspend fun loadUser(): User? {
        //TODO ОТ КУДА БРАТЬ ID ОНО ДОЛЖНО БЫТЬ ОБЩИМ ВО ВСЕМ ПРИЛОЖЕНИИ
        return userRepository.getUserById(0)
    }

    private suspend fun loadNotifications(): List<Notification>? {
        //TODO ОТ КУДА БРАТЬ ID ОНО ДОЛЖНО БЫТЬ ОБЩИМ ВО ВСЕМ ПРИЛОЖЕНИИ
        return notificationRepository.getUserNotificationsByUserId(0)
    }

    class UserNotificationsViewModelFactory(
        private val userRepository: UserRepository,
        private val notificationRepository: NotificationRepository
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserNotificationsViewModel::class.java)) {
                return UserNotificationsViewModel(
                    userRepository,
                    notificationRepository
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}