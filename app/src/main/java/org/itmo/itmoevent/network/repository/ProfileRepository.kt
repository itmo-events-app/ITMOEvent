package org.itmo.itmoevent.network.repository

import org.itmo.itmoevent.network.api.ProfileControllerApi
import org.itmo.itmoevent.network.model.NotificationSettingsRequest
import org.itmo.itmoevent.network.model.ProfileResponse
import org.itmo.itmoevent.network.model.PrivilegeResponse
import org.itmo.itmoevent.network.model.PrivilegeWithHasOrganizerRolesResponse
import org.itmo.itmoevent.network.model.UserChangeLoginRequest
import org.itmo.itmoevent.network.model.UserChangeNameRequest
import org.itmo.itmoevent.network.model.UserChangePasswordRequest
import org.itmo.itmoevent.network.util.apiRequestFlow
import retrofit2.http.Body
import retrofit2.http.Path
import retrofit2.http.Query

class ProfileRepository(private val profileApi: ProfileControllerApi) {

    fun changeLogin(userChangeLoginRequest: UserChangeLoginRequest) = apiRequestFlow {
        profileApi.changeLogin(userChangeLoginRequest)
    }

    fun changeName(userChangeNameRequest: UserChangeNameRequest) = apiRequestFlow {
        profileApi.changeName(userChangeNameRequest)
    }

    fun changePassword(userChangePasswordRequest: UserChangePasswordRequest) =
        apiRequestFlow {
            profileApi.changePassword(userChangePasswordRequest)
        }

    fun getAllUsers(searchQuery: String = "", page: Int = 0, size: Int = 10) = apiRequestFlow {
        profileApi.getAllUsers(searchQuery, page, size)
    }

    fun getUserEventPrivileges(id: Int) = apiRequestFlow {
        profileApi.getUserEventPrivileges(id)
    }

    fun getUserInfo() = apiRequestFlow {
        profileApi.getUserInfo()
    }

    fun getUserSystemPrivileges() = apiRequestFlow {
        profileApi.getUserSystemPrivileges()
    }

    fun updateNotifications(notificationSettingsRequest: NotificationSettingsRequest) =
        apiRequestFlow {
            profileApi.updateNotifications(notificationSettingsRequest)
        }
}
