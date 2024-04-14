package org.itmo.itmoevent.network.repository

import org.itmo.itmoevent.network.api.PlaceControllerApi
import org.itmo.itmoevent.network.model.PlaceRequest
import org.itmo.itmoevent.network.util.apiRequestFlow
import retrofit2.http.Body
import retrofit2.http.Path

class PlaceRepository(private val placeApi: PlaceControllerApi) {
    fun getAllOrFilteredPlaces(page: Int? = 0, size: Int? = 5, name: String? = null) =
        apiRequestFlow {
            placeApi.getAllOrFilteredPlaces(page, size, name)
        }

    fun placeAdd(placeRequest: PlaceRequest) = apiRequestFlow {
        placeApi.placeAdd(placeRequest)
    }

    fun placeDelete(@Path("id") id: Int) = apiRequestFlow {
        placeApi.placeDelete(id)
    }

    fun placeEdit(@Path("id") id: Int, @Body placeRequest: PlaceRequest) = apiRequestFlow {
        placeApi.placeEdit(id, placeRequest)
    }

    fun placeGet(@Path("id") id: Int) = apiRequestFlow {
        placeApi.placeGet(id)
    }
}