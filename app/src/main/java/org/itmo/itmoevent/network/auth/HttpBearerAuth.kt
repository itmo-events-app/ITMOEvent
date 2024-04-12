package org.itmo.itmoevent.network.auth

import android.util.Log
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Response
import org.itmo.itmoevent.network.util.TokenManager

class HttpBearerAuth(
    var tokenManager: TokenManager
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            tokenManager.getToken().first()
        }

        Log.d("interceptor", token!!)
        val request = chain.request().newBuilder()
        request.addHeader("Authorization", "Bearer $token")
        return chain.proceed(request.build())
    }

//    @Throws(IOException::class)
//    override fun intercept(chain: Chain): Response {
//        var request = chain.request()
//
//        // If the request already have an authorization (eg. Basic auth), do nothing
//        if (request.header("Authorization") == null && bearerToken.isNotBlank()) {
//            request = request.newBuilder()
//                .addHeader("Authorization", headerValue())
//                .build()
//        }
//        return chain.proceed(request)
//    }

//    private fun headerValue(): String {
//        return if (schema.isNotBlank()) {
//            "${upperCaseBearer()} $bearerToken"
//        } else {
//            bearerToken
//        }
//    }
//
//    private fun upperCaseBearer(): String {
//        return if (schema.lowercase().equals("bearer")) "Bearer" else schema
//    }

}
