package org.itmo.itmoevent.network.repository

import android.util.Log
import org.itmo.itmoevent.network.api.AuthControllerApi
import org.itmo.itmoevent.network.api.ProfileControllerApi
import org.itmo.itmoevent.network.model.LoginRequest
import org.itmo.itmoevent.network.model.ProfileResponse
import org.itmo.itmoevent.network.util.apiRequestFlow
import retrofit2.Response

class ProfileRepository(private val profileApi: ProfileControllerApi) {

    fun getUserInfo() = apiRequestFlow {
        profileApi.getUserInfo()
    }
}