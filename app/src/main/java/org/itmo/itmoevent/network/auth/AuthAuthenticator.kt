package org.itmo.itmoevent.network.auth

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.logging.HttpLoggingInterceptor
import org.itmo.itmoevent.config.NetworkSettings
import org.itmo.itmoevent.network.api.AuthControllerApi
import org.itmo.itmoevent.network.api.ProfileControllerApi
import org.itmo.itmoevent.network.infrastructure.Serializer
import org.itmo.itmoevent.network.util.TokenManager
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Inject
class AuthAuthenticator @Inject constructor(
    private val tokenManager: TokenManager,
): Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val token = runBlocking {
            tokenManager.getToken().first()
        }
        return runBlocking {
            val _token = checkToken(token)

            if (!_token) { //Couldn't refresh the token, so restart the login process
                tokenManager.deleteToken()
            }

            tokenManager.saveToken(token!!)
            Log.d("token_manager", token)
            response.request.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()

            //TODO Переделать, когда появится refreshToken
//            newToken.body()?.let {
//                tokenManager.saveToken(it.token)
//                response.request.newBuilder()
//                    .header("Authorization", "Bearer ${it.token}")
//                    .build()
//            }
        }
    }

    private suspend fun checkToken(token: String?): Boolean {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val converterFactories: List<Converter.Factory> = listOf(
            ScalarsConverterFactory.create(),
            Serializer.kotlinxSerializationJson.asConverterFactory("application/json".toMediaType()),
        )

        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor)
            .addInterceptor{ chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                .build()
                chain.proceed(newRequest)
            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(NetworkSettings.BASE_URL)
            .apply {
                converterFactories.forEach {
                    addConverterFactory(it)
                }
            }
            .client(okHttpClient)
            .build()

        val service = retrofit.create(ProfileControllerApi::class.java)
        return service.getUserInfo().isSuccessful
    }


}