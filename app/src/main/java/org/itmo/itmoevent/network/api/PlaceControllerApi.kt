package org.itmo.itmoevent.network.api

import org.itmo.itmoevent.network.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import org.itmo.itmoevent.network.model.PlaceRequest
import org.itmo.itmoevent.network.model.PlaceResponse

interface PlaceControllerApi {
    /**
     * Фильтрация списка площадок
     * 
     * Responses:
     *  - 200: OK
     *
     * @param page Номер страницы, с которой начать показ площадок (optional, default to 0)
     * @param size Число площадок на странице (optional, default to 5)
     * @param name Имя площадки (optional)
     * @return [kotlin.collections.List<PlaceResponse>]
     */
    @GET("api/places")
    suspend fun getAllOrFilteredPlaces(@Query("page") page: kotlin.Int? = 0, @Query("size") size: kotlin.Int? = 5, @Query("name") name: kotlin.String? = null): Response<kotlin.collections.List<PlaceResponse>>

    /**
     * Создание площадки
     * 
     * Responses:
     *  - 200: OK
     *
     * @param placeRequest 
     * @return [kotlin.Int]
     */
    @POST("api/places")
    suspend fun placeAdd(@Body placeRequest: PlaceRequest): Response<kotlin.Int>

    /**
     * Удаление площадки
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id ID площадки
     * @return [Unit]
     */
    @DELETE("api/places/{id}")
    suspend fun placeDelete(@Path("id") id: kotlin.Int): Response<Unit>

    /**
     * Редактирование площадки
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id ID площадки
     * @param placeRequest 
     * @return [PlaceResponse]
     */
    @PUT("api/places/{id}")
    suspend fun placeEdit(@Path("id") id: kotlin.Int, @Body placeRequest: PlaceRequest): Response<PlaceResponse>

    /**
     * Получение площадки по id
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id ID площадки
     * @return [PlaceResponse]
     */
    @GET("api/places/{id}")
    suspend fun placeGet(@Path("id") id: kotlin.Int): Response<PlaceResponse>

}
