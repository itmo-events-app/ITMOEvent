package org.itmo.itmoevent.model.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class EventNetworkService {

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

    companion object {
        private const val BASE_URL: String = "https://1c4b5df4-7222-41c0-a10b-8cdea728d4a1.mock.pstmn.io/"
    }
}
