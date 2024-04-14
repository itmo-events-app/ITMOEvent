package org.itmo.itmoevent.network.auth

import android.util.Log
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.itmo.itmoevent.network.api.AuthControllerApi
import org.itmo.itmoevent.network.api.ProfileControllerApi
import org.itmo.itmoevent.network.util.TokenManager
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor)
            .addInterceptor{ chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization: Bearer", token!!)
                .build()
                chain.proceed(newRequest)
            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://95.216.146.187:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        val service = retrofit.create(ProfileControllerApi::class.java)
        return service.getUserInfo().isSuccessful
    }


}