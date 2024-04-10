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
//        val loaded = loadNotifications()
        //TODO FIX API
        val loaded = listOf(
            Notification(
                0,
                "Таскай стулья",
                "Просим вас заняться перемещением 10 стульев, находящихся в комнате А, в комнату B. Пожалуйста, обеспечьте аккуратное расположение каждого стула в новом месте, чтобы создать уютную обстановку. Наше задание будет считаться завершенным только после того, как все стулья будут успешно перенесены и установлены в комнате B, готовые к использованию."
            ),
            Notification(1,
                "Поднять платину",
                "Плотину надо поднять, рычагом. Я его дам. Канал нужно завалить камнем, камень я не дам. Плотину надо поднять, рычагом. Я его дам. Канал нужно завалить камнем, камень я не дам. Плотину надо поднять, рычагом. Я его дам. Канал нужно завалить камнем, камень я не дам."
            )
        )
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