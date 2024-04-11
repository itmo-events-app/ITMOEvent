package org.itmo.itmoevent.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import org.itmo.itmoevent.model.data.entity.Notification
import org.itmo.itmoevent.model.data.entity.User
import org.itmo.itmoevent.model.repository.AuthRepository
import org.itmo.itmoevent.model.repository.NotificationRepository
import org.itmo.itmoevent.model.repository.UserRepository
import org.itmo.itmoevent.network.api.ProfileControllerApi
import org.itmo.itmoevent.network.model.LoginRequest
import org.itmo.itmoevent.network.model.ProfileResponse
import retrofit2.Call
import retrofit2.Response

class UserNotificationsViewModel(
    private val userRepository: UserRepository,
    private val notificationRepository: NotificationRepository,
    private val profileApi: ProfileControllerApi,
    private val authApiRepository: AuthRepository
): ViewModel() {


    val userLiveData: LiveData<ProfileResponse?> = liveData {
        //authApiRepository.login(LoginRequest("333666@niuitmo.ru", "PaSsWoRd1!"))
        val loaded = loadUser()
        emit(loaded.body())
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

    private suspend fun loadUser(): Response<ProfileResponse> {
        return profileApi.getUserInfo()
    }

    private suspend fun loadNotifications(): List<Notification>? {
        //TODO ОТ КУДА БРАТЬ ID ОНО ДОЛЖНО БЫТЬ ОБЩИМ ВО ВСЕМ ПРИЛОЖЕНИИ
        return notificationRepository.getUserNotificationsByUserId(0)
    }

    class UserNotificationsViewModelFactory(
        private val userRepository: UserRepository,
        private val notificationRepository: NotificationRepository,
        private val profileControllerApi: ProfileControllerApi,
        private val authApiRepository: AuthRepository
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserNotificationsViewModel::class.java)) {
                return UserNotificationsViewModel(
                    userRepository,
                    notificationRepository,
                    profileControllerApi,
                    authApiRepository
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}