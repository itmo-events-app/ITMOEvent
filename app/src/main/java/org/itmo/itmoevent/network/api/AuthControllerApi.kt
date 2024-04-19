package org.itmo.itmoevent.network.api

import org.itmo.itmoevent.network.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import org.itmo.itmoevent.network.model.LoginRequest
import org.itmo.itmoevent.network.model.NewPasswordRequest
import org.itmo.itmoevent.network.model.RecoveryPasswordRequest
import org.itmo.itmoevent.network.model.RegistrationRequestForAdmin
import org.itmo.itmoevent.network.model.RegistrationUserRequest

interface AuthControllerApi {
    /**
     * Утверждение заявки на регистрацию в системе
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
     * Отклонение заявки на регистрацию в системе
     * 
     * Responses:
     *  - 200: OK
     *
     * @param requestId ID заявки на регистрацию
     * @return [Unit]
     */
    @POST("declineRegister/{requestId}")
    suspend fun declineRegister(@Path("requestId") requestId: kotlin.Int): Response<Unit>

    /**
     * Получение списка всех заявок на регистрацию в системе
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
     * Запрос на смену пароля
     * 
     * Responses:
     *  - 200: OK
     *
     * @param newPasswordRequest 
     * @return [Unit]
     */
    @POST("newPassword")
    suspend fun newPassword(@Body newPasswordRequest: NewPasswordRequest): Response<Unit>

    /**
     * Отправка запроса на восстановление пароля
     * 
     * Responses:
     *  - 200: OK
     *
     * @param recoveryPasswordRequest 
     * @return [Unit]
     */
    @POST("recoveryPassword")
    suspend fun recoveryPassword(@Body recoveryPasswordRequest: RecoveryPasswordRequest): Response<Unit>

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

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param returnUrl 
     * @return [Unit]
     */
    @POST("sendVerificationEmail")
    suspend fun sendVerificationEmail(@Query("returnUrl") returnUrl: kotlin.String): Response<Unit>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param token 
     * @return [Unit]
     */
    @POST("validateEmailVerificationToken")
    suspend fun validateEmailVerificationToken(@Query("token") token: kotlin.String): Response<Unit>

    /**
     * Запрос на валидацию токена
     * 
     * Responses:
     *  - 200: OK
     *
     * @param token Токен восстановления пароля
     * @return [Unit]
     */
    @POST("validateRecoveryToken")
    suspend fun validateRecoveryToken(@Query("token") token: kotlin.String): Response<Unit>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [Unit]
     */
    @POST("verifyEmail")
    suspend fun verifyEmail(): Response<Unit>

}
