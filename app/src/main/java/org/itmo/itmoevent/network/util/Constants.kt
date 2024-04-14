package org.itmo.itmoevent.network.util

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withTimeoutOrNull
import org.json.JSONException
import org.json.JSONObject
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
                    val errorJsonString = error.string()
                    Log.println(Log.VERBOSE, "api", errorJsonString)

                    try {
                        val errorJson = JSONObject(errorJsonString)
                        val errorsArray = errorJson.getJSONArray("errors")

                        if (errorsArray.length() > 0) {
                            val errorMessage = errorsArray.getString(0)
                            emit(ApiResponse.Failure(errorMessage, response.code()))
                        } else {
                            emit(ApiResponse.Failure("Unknown error", response.code()))
                        }
                    } catch (e: JSONException) {
                        emit(ApiResponse.Failure("Failed to parse error JSON", response.code()))
                    }
                }
            }
        } catch (e: Exception) {
            emit(ApiResponse.Failure(e.message ?: e.toString(), 400))
        }
    } ?: emit(ApiResponse.Failure("Timeout! Please try again.", 408))
}.flowOn(Dispatchers.IO)