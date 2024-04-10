package org.itmo.itmoevent.model.network

import org.itmo.itmoevent.model.data.dto.PrivilegeDto
import org.itmo.itmoevent.model.data.dto.RoleDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RoleApi {

    @GET("api/roles/organizational")
    suspend fun getOrganisationalRoles() : Response<List<RoleDto>>

    @GET("/api/profile/system-privileges")
    suspend fun getUserSystemPrivileges() : Response<List<PrivilegeDto>>

    @GET("/api/profile/event-privileges/{id}")
    suspend fun getUserEventPrivileges(@Path("id") eventId: Int) : Response<List<PrivilegeDto>>

}