package org.itmo.itmoevent.model.network

import org.itmo.itmoevent.model.data.dto.PlaceDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PlaceApi {

    @GET("/api/places/{id}")
    suspend fun getPlaceById(@Path("id") placeId: Int) : Response<PlaceDto>

    @GET("/api/places")
    suspend fun getAllPlaces(@Query("page") page: Int, @Query("size") size: Int) : Response<List<PlaceDto>>

}
