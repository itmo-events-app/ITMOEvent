package org.itmo.itmoevent.model.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class EventNetworkService {

    private val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()

    private val mainOkHttpClient = OkHttpClient()

    private val recipesOkHttpClient = mainOkHttpClient.newBuilder()
        .addInterceptor { chain ->
            val req = chain.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer $TEST_TOKEN")
                .build()
            chain.proceed(req)
        }
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(recipesOkHttpClient)
        .build()

    val eventApi = retrofit.create<EventApi>()
    val rolesApi = retrofit.create<RoleApi>()
    val userApi = retrofit.create<UserApi>()
    val notificationApi = retrofit.create<NotificationApi>()
    val taskApi = retrofit.create<TaskApi>()
    val eventActivityApi = retrofit.create<EventActivityApi>()
    val placeApi = retrofit.create<PlaceApi>()

    companion object {
        //        private const val BASE_URL: String = "https://f5539bef-d7f4-4553-84d9-19f916d17a00.mock.pstmn.io/"
        private const val BASE_URL: String = "http://95.216.146.187:8080"
        private const val TEST_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIzMzNkNjY2QG5pdWl0bW8ucnUiLCJpYXQiOjE3MTMxMTY5OTEsImV4cCI6MTcxMzEyMDU5MX0.c7EWHcqzjMmioOkZH0h8Z3AVOLmFFbmgxlp20fQHqkg"
    }
}
