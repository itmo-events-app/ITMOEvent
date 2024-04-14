package org.itmo.itmoevent.network.api

import org.itmo.itmoevent.network.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import org.itmo.itmoevent.network.model.LoginRequest
import org.itmo.itmoevent.network.model.RegistrationRequestForAdmin
import org.itmo.itmoevent.network.model.RegistrationUserRequest

interface AuthControllerApi {
    /**
     * Одобрение заявки на регистрацию
     * 
     * Responses:
     *  - 200: OK
     *
     * @param requestId ID заявки на регистрацию
     * @return [Unit]
     */
    @POST("approveRegister/{requestId}")
    suspend fun approveRegister(@Path("requestId") requestId: kotlin.Int): Response<Unit>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param requestId 
     * @return [Unit]
     */
    @POST("declineRegister/{requestId}")
    suspend fun declineRegister(@Path("requestId") requestId: kotlin.Int): Response<Unit>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [kotlin.collections.List<RegistrationRequestForAdmin>]
     */
    @GET("listRegisterRequests")
    suspend fun listRegisterRequests(): Response<kotlin.collections.List<RegistrationRequestForAdmin>>

    /**
     * Получение логина пользователя
     * 
     * Responses:
     *  - 200: OK
     *
     * @param loginRequest 
     * @return [kotlin.String]
     */
    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<kotlin.String>

    /**
     * Регистрация пользователя
     * 
     * Responses:
     *  - 200: OK
     *
     * @param registrationUserRequest 
     * @return [Unit]
     */
    @POST("register")
    suspend fun register(@Body registrationUserRequest: RegistrationUserRequest): Response<Unit>

}
