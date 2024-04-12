package org.itmo.itmoevent.network.repository

import android.util.Log
import org.itmo.itmoevent.network.api.AuthControllerApi
import org.itmo.itmoevent.network.model.LoginRequest
import org.itmo.itmoevent.network.util.apiRequestFlow
import retrofit2.Response

class AuthRepository(private val authApi: AuthControllerApi) {

    fun login(request: LoginRequest) = apiRequestFlow  {
        authApi.login(request)
    }
}