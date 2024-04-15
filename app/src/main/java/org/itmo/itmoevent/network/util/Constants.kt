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
                val errorString = error.string()
                Log.println(Log.VERBOSE, "api", errorString)

                // Попытка разбора JSON-строки ошибки
                try {
                    val errorJson = JSONObject(errorString)
                    if (errorJson.has("errors")) {
                        val errorsArray = errorJson.getJSONArray("errors")
                        for (i in 0 until errorsArray.length()) {
                            val errorMessage = errorsArray.getString(i)
                            emit(ApiResponse.Failure(errorMessage, 400))
                        }
                    }
                } catch (e: JSONException) {
                    // Попытка разбора строки ошибки в формате "код_ошибки СТАТУС_ОШИБКИ "Сообщение_об_ошибке""
                    val pattern = Regex("""(\d+) ([A-Z_]+) "(.+)"""")
                    val matchResult = pattern.find(errorString)
                    if (matchResult != null && matchResult.groupValues.size >= 4) {
                        val status = matchResult.groupValues[2]
                        val errorMessage = matchResult.groupValues[3]
                        emit(ApiResponse.Failure(errorMessage, status.toIntOrNull() ?: 400))
                    } else {
                        // Если строка ошибки не соответствует ни одному из форматов, отправляем общее сообщение об ошибке
                        emit(ApiResponse.Failure("Unknown error", 400))
                    }
                }
            }
        }
    } catch (e: Exception) {
        emit(ApiResponse.Failure(e.message ?: e.toString(), 400))
    }
}.flowOn(Dispatchers.IO)