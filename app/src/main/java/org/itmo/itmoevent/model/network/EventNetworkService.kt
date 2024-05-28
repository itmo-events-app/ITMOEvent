package org.itmo.itmoevent.model.network

import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.itmo.itmoevent.config.NetworkSettings
import org.itmo.itmoevent.network.util.TokenManager
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


class EventNetworkService(private val tokenManager: TokenManager) {

    private val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()

    private val loggingInterceptor: HttpLoggingInterceptor by lazy {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        logging
    }
    private val tokenInjectorInterceptor: Interceptor by lazy {
        Interceptor { chain ->
            val token = runBlocking {
                tokenManager.getToken().first()
            }
            val request = chain.request().newBuilder()
            request.addHeader("Authorization", "Bearer $token")
            chain.proceed(request.build())
        }
    }

    private val okHttpClient = OkHttpClient().newBuilder()
        .addInterceptor(tokenInjectorInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(NetworkSettings.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()

    val eventApi = retrofit.create<EventApi>()
    val rolesApi = retrofit.create<RoleApi>()
    val userApi = retrofit.create<UserApi>()
    val notificationApi = retrofit.create<NotificationApi>()
    val taskApi = retrofit.create<TaskApi>()
    val eventActivityApi = retrofit.create<EventActivityApi>()
    val placeApi = retrofit.create<PlaceApi>()


    companion object {
        private const val BASE_URL: String = "http://95.216.146.187:8080"
//        private const val BASE_URL: String = "http://192.168.81.31:8080"
    }
}
