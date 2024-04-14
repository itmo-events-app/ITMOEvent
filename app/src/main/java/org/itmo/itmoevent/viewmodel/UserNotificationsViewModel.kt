package org.itmo.itmoevent.viewmodel

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.itmo.itmoevent.network.model.NotificationResponse
import org.itmo.itmoevent.network.model.NotificationSettingsRequest
import org.itmo.itmoevent.network.model.ProfileResponse
import org.itmo.itmoevent.network.model.UserChangeLoginRequest
import org.itmo.itmoevent.network.model.UserChangeNameRequest
import org.itmo.itmoevent.network.model.UserChangePasswordRequest
import org.itmo.itmoevent.network.repository.NotificationRepository
import org.itmo.itmoevent.network.repository.ProfileRepository
import org.itmo.itmoevent.network.util.ApiResponse
import javax.inject.Inject

@HiltViewModel
class UserNotificationsViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val notificationRepository: NotificationRepository,
): BaseViewModel() {

    // ProfileRepository
    private val _profileResponse = MutableLiveData<ApiResponse<ProfileResponse>>()
    val profileResponse = _profileResponse

    private val _changePasswordResponse = MutableLiveData<ApiResponse<Unit>>()
    val changePasswordResponse = _changePasswordResponse

    // NotificationRepository
    private val _allNotificationsResponse = MutableLiveData<ApiResponse<List<NotificationResponse>>>()
    val allNotificationsResponse = _allNotificationsResponse

    private val _allAsSeenNotificationsResponse = MutableLiveData<ApiResponse<List<NotificationResponse>>>()
    val allAsSeenNotificationsResponse = _allAsSeenNotificationsResponse

    private val _oneAsSeenNotificationResponse = MutableLiveData<ApiResponse<NotificationResponse>>()
    val oneAsSeenNotificationResponse = _oneAsSeenNotificationResponse


    fun changeLogin(userChangeLoginRequest: UserChangeLoginRequest, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        MutableLiveData(),
        coroutinesErrorHandler
    ) {
        profileRepository.changeLogin(userChangeLoginRequest)
    }

    fun changeName(userChangeNameRequest: UserChangeNameRequest, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        MutableLiveData(),
        coroutinesErrorHandler
    ) {
        profileRepository.changeName(userChangeNameRequest)
    }

    fun changePassword(userChangePasswordRequest: UserChangePasswordRequest, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _changePasswordResponse,
        coroutinesErrorHandler
    ) {
        profileRepository.changePassword(userChangePasswordRequest)
    }

    fun getUserInfo(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _profileResponse,
        coroutinesErrorHandler
    ) {
        profileRepository.getUserInfo()
    }


    fun updateNotifications(notificationSettingsRequest: NotificationSettingsRequest, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        MutableLiveData(),
        coroutinesErrorHandler
    ) {
        profileRepository.updateNotifications(notificationSettingsRequest)
    }

    fun getAllNotifications(page: Int, size: Int, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _allNotificationsResponse,
        coroutinesErrorHandler
    ) {
        notificationRepository.getAllNotifications(page, size)
    }

    fun setAllAsSeenNotifications(page: Int, size: Int, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _allAsSeenNotificationsResponse,
        coroutinesErrorHandler
    ) {
        notificationRepository.setAllAsSeenNotifications(page, size)
    }

    fun setOneAsSeenNotification(notificationId: Int, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _oneAsSeenNotificationResponse,
        coroutinesErrorHandler
    ) {
        notificationRepository.setOneAsSeenNotification(notificationId)
    }

//    val notificationsLiveData: LiveData<List<Notification>?> = liveData {
////        val loaded = loadNotifications()
//        //TODO FIX API
//        val loaded = listOf(
//            Notification(
//                0,
//                "Таскай стулья",
//                "Просим вас заняться перемещением 10 стульев, находящихся в комнате А, в комнату B. Пожалуйста, обеспечьте аккуратное расположение каждого стула в новом месте, чтобы создать уютную обстановку. Наше задание будет считаться завершенным только после того, как все стулья будут успешно перенесены и установлены в комнате B, готовые к использованию."
//            ),
//            Notification(1,
//                "Поднять платину",
//                "Плотину надо поднять, рычагом. Я его дам. Канал нужно завалить камнем, камень я не дам. Плотину надо поднять, рычагом. Я его дам. Канал нужно завалить камнем, камень я не дам. Плотину надо поднять, рычагом. Я его дам. Канал нужно завалить камнем, камень я не дам."
//            )
//        )
//        emit(loaded)
//    }


}