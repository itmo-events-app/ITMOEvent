package org.itmo.itmoevent.model.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.itmo.itmoevent.network.SessionManager
import org.itmo.itmoevent.network.api.AuthControllerApi
import org.itmo.itmoevent.network.api.ProfileControllerApi
import org.itmo.itmoevent.network.infrastructure.ApiClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class EventNetworkService(private val sessionManager: SessionManager?) {

    private val apiClient = ApiClient(authNames = arrayOf("BearerAuthentication"))
        .setBearerToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIzMzM2NjZAbml1aXRtby5ydSIsImlhdCI6MTcxMjg2MjU1MCwiZXhwIjoxNzEyODY2MTUwfQ.57alg7FT6cv24YF065_oiNU5U0ic4k2SnDk3RsLm15g")

    private val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm").create()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(OkHttpClient())
        .build()

    val eventApi = retrofit.create<EventApi>()
    val rolesApi = retrofit.create<RoleApi>()
    val userApi = retrofit.create<UserApi>()
    val notificationApi = retrofit.create<NotificationApi>()
    val taskApi = retrofit.create<TaskApi>()
    val eventActivityApi = retrofit.create<EventActivityApi>()

    //NEW
    val profileApi = apiClient.createService(ProfileControllerApi::class.java)
    val authApi = apiClient.createService(AuthControllerApi::class.java)

    companion object {
        private const val BASE_URL: String = "https://1c4b5df4-7222-41c0-a10b-8cdea728d4a1.mock.pstmn.io/"
    }
}
