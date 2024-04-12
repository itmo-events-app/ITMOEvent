package org.itmo.itmoevent.viewmodel

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.itmo.itmoevent.network.repository.AuthRepository
import org.itmo.itmoevent.network.model.LoginRequest
import org.itmo.itmoevent.network.model.RegistrationRequestForAdmin
import org.itmo.itmoevent.network.model.RegistrationUserRequest
import org.itmo.itmoevent.network.util.ApiResponse
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
): BaseViewModel() {

    private val _loginResponse = MutableLiveData<ApiResponse<String>>()
    val loginResponse = _loginResponse


    fun login(loginRequest: LoginRequest, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _loginResponse,
        coroutinesErrorHandler
    ) {
        authRepository.login(loginRequest)
    }

    fun register(registrationUserRequest: RegistrationUserRequest, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        MutableLiveData(),
        coroutinesErrorHandler
    ) {
        authRepository.register(registrationUserRequest)
    }
}