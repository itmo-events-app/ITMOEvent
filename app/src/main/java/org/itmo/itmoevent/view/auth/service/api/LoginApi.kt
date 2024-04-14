package org.itmo.itmoevent.view.auth.service.api

import org.itmo.itmoevent.view.auth.service.models.LoginRequestDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
interface LoginApi {
    @FormUrlEncoded
    @POST("login")
    fun login(@Field("login") login: String, @Field("password") password: String): Call<String>
}