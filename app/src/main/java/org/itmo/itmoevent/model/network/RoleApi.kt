package org.itmo.itmoevent.model.network

import org.itmo.itmoevent.model.data.dto.RoleDto
import retrofit2.Response
import retrofit2.http.GET

interface RoleApi {

    @GET("api/roles/organizational")
    suspend fun getOrganisationalRoles() : Response<List<RoleDto>>

}