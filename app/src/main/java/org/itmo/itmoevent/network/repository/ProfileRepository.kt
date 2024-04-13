package org.itmo.itmoevent.network.repository

import android.util.Log
import org.itmo.itmoevent.network.api.AuthControllerApi
import org.itmo.itmoevent.network.api.ProfileControllerApi
import org.itmo.itmoevent.network.model.LoginRequest
import org.itmo.itmoevent.network.model.NotificationSettingsRequest
import org.itmo.itmoevent.network.model.ProfileResponse
import org.itmo.itmoevent.network.model.UserChangeLoginRequest
import org.itmo.itmoevent.network.model.UserChangeNameRequest
import org.itmo.itmoevent.network.model.UserChangePasswordRequest
import org.itmo.itmoevent.network.util.apiRequestFlow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Path

class ProfileRepository(private val profileApi: ProfileControllerApi) {

    fun changeLogin(@Body userChangeLoginRequest: UserChangeLoginRequest) = apiRequestFlow {
        profileApi.changeLogin(userChangeLoginRequest)
    }

    fun changeName(@Body userChangeNameRequest: UserChangeNameRequest) = apiRequestFlow {
        profileApi.changeName(userChangeNameRequest)
    }

    fun changePassword(@Body userChangePasswordRequest: UserChangePasswordRequest) =
        apiRequestFlow {
            profileApi.changePassword(userChangePasswordRequest)
        }

    fun getUserEventPrivileges(@Path("id") id: Int) = apiRequestFlow {
        profileApi.getUserEventPrivileges(id)
    }

    fun getUserInfo() = apiRequestFlow {
        profileApi.getUserInfo()
    }

    fun getUserSystemPrivileges() = apiRequestFlow {
        profileApi.getUserSystemPrivileges()
    }

    fun updateNotifications(@Body notificationSettingsRequest: NotificationSettingsRequest) =
        apiRequestFlow {
            profileApi.updateNotifications(notificationSettingsRequest)
        }
}