package org.itmo.itmoevent.network.util

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withTimeoutOrNull
import retrofit2.Response

fun<T> apiRequestFlow(call: suspend () -> Response<T>): Flow<ApiResponse<T>> = flow {
    emit(ApiResponse.Loading)

    withTimeoutOrNull(20000L) {
        val response = call()

        try {
            if (response.isSuccessful) {
                response.body()?.let { data ->
                    Log.println(Log.VERBOSE, "api", data.toString())
                    emit(ApiResponse.Success(data))
                }
            } else {
                response.errorBody()?.let { error ->
                    error.close()
                    Log.println(Log.VERBOSE,"api", error.string())
                    emit(ApiResponse.Failure(error.string(), response.code()))
                }
            }
        } catch (e: Exception) {
            emit(ApiResponse.Failure(e.message ?: e.toString(), 400))
        }
    } ?: emit(ApiResponse.Failure("Timeout! Please try again.", 408))
}.flowOn(Dispatchers.IO)