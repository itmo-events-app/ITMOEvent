package org.itmo.itmoevent.model.network

import org.itmo.itmoevent.model.data.entity.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {

    @GET("users/{id}/")
    suspend fun getUserById(@Path("id") userId: Int) : Response<User>

}