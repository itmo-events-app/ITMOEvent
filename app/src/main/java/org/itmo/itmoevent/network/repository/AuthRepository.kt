package org.itmo.itmoevent.network.repository

import org.itmo.itmoevent.network.api.AuthControllerApi
import org.itmo.itmoevent.network.model.LoginRequest
import org.itmo.itmoevent.network.model.RegistrationUserRequest
import org.itmo.itmoevent.network.util.apiRequestFlow
import retrofit2.http.Body
import retrofit2.http.Path

class AuthRepository(private val authApi: AuthControllerApi) {

    fun approveRegister(@Path("requestId") requestId: Int) = apiRequestFlow {
        authApi.approveRegister(requestId)
    }

    fun declineRegister(@Path("requestId") requestId: Int) = apiRequestFlow {
        authApi.declineRegister(requestId)
    }

    fun listRegisterRequests() = apiRequestFlow {
        authApi.listRegisterRequests()
    }

    fun login(request: LoginRequest) = apiRequestFlow {
        authApi.login(request)
    }

    fun register(@Body registrationUserRequest: RegistrationUserRequest) = apiRequestFlow {
        authApi.register(registrationUserRequest)
    }
}