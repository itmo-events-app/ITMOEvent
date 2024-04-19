package org.itmo.itmoevent.network.repository

import org.itmo.itmoevent.network.api.AuthControllerApi
import org.itmo.itmoevent.network.model.LoginRequest
import org.itmo.itmoevent.network.model.NewPasswordRequest
import org.itmo.itmoevent.network.model.RecoveryPasswordRequest
import org.itmo.itmoevent.network.model.RegistrationRequestForAdmin
import org.itmo.itmoevent.network.model.RegistrationUserRequest
import org.itmo.itmoevent.network.util.apiRequestFlow
import retrofit2.http.Body
import retrofit2.http.Path
import retrofit2.http.Query

class AuthRepository(private val authApi: AuthControllerApi) {

    fun approveRegister(requestId: Int) = apiRequestFlow {
        authApi.approveRegister(requestId)
    }

    fun declineRegister(requestId: Int) = apiRequestFlow {
        authApi.declineRegister(requestId)
    }

    fun listRegisterRequests() = apiRequestFlow {
        authApi.listRegisterRequests()
    }

    fun login(loginRequest: LoginRequest) = apiRequestFlow {
        authApi.login(loginRequest)
    }

    fun newPassword(newPasswordRequest: NewPasswordRequest) = apiRequestFlow {
        authApi.newPassword(newPasswordRequest)
    }

    fun recoveryPassword(recoveryPasswordRequest: RecoveryPasswordRequest) = apiRequestFlow {
        authApi.recoveryPassword(recoveryPasswordRequest)
    }

    fun register(registrationUserRequest: RegistrationUserRequest) = apiRequestFlow {
        authApi.register(registrationUserRequest)
    }

    fun sendVerificationEmail(returnUrl: String) = apiRequestFlow {
        authApi.sendVerificationEmail(returnUrl)
    }

    fun validateEmailVerificationToken(token: String) = apiRequestFlow {
        authApi.validateEmailVerificationToken(token)
    }

    fun validateRecoveryToken(token: String) = apiRequestFlow {
        authApi.validateRecoveryToken(token)
    }

    fun verifyEmail() = apiRequestFlow {
        authApi.verifyEmail()
    }
}
