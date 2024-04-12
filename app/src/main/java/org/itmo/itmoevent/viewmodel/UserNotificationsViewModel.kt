package org.itmo.itmoevent.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.itmo.itmoevent.model.data.entity.Notification
import org.itmo.itmoevent.network.model.NotificationResponse
import org.itmo.itmoevent.network.repository.ProfileRepository
import org.itmo.itmoevent.network.model.ProfileResponse
import org.itmo.itmoevent.network.util.ApiResponse
import javax.inject.Inject

@HiltViewModel
class UserNotificationsViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
): BaseViewModel() {

    private val _profileResponse = MutableLiveData<ApiResponse<ProfileResponse>>()
    val profileResponse = _profileResponse


    fun getUserInfo(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _profileResponse,
        coroutinesErrorHandler,
    ) {
        profileRepository.getUserInfo()
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


}