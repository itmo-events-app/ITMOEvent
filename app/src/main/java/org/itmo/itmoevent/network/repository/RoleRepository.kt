package org.itmo.itmoevent.network.repository

import org.itmo.itmoevent.network.api.RoleControllerApi
import org.itmo.itmoevent.network.model.RoleRequest
import org.itmo.itmoevent.network.util.apiRequestFlow
import retrofit2.http.Body
import retrofit2.http.Path
import retrofit2.http.Query

class RoleRepository(private val roleApi: RoleControllerApi) {

    fun assignAssistantRole(@Path("userId") userId: Int, @Path("eventId") eventId: Int) =
        apiRequestFlow {
            roleApi.assignAssistantRole(userId, eventId)
        }

    fun assignOrganizationalRole(
        @Path("userId") userId: Int,
        @Path("eventId") eventId: Int,
        @Path("roleId") roleId: Int
    ) = apiRequestFlow {
        roleApi.assignOrganizationalRole(userId, eventId, roleId)
    }

    fun assignOrganizerRole(@Path("userId") userId: Int, @Path("eventId") eventId: Int) =
        apiRequestFlow {
            roleApi.assignOrganizerRole(userId, eventId)
        }

    fun assignSystemRole(@Path("userId") userId: Int, @Path("roleId") roleId: Int) =
        apiRequestFlow {
            roleApi.assignSystemRole(userId, roleId)
        }

    fun createRole(@Body roleRequest: RoleRequest) = apiRequestFlow {
        roleApi.createRole(roleRequest)
    }

    fun deleteRole(@Path("id") id: Int) = apiRequestFlow {
        roleApi.deleteRole(id)
    }

    fun editRole(@Path("id") id: Int, @Body roleRequest: RoleRequest) = apiRequestFlow {
        roleApi.editRole(id, roleRequest)
    }

    fun getAllPrivileges(@Query("type") type: RoleControllerApi.TypeGetAllPrivileges? = null) =
        apiRequestFlow {
            roleApi.getAllPrivileges(type)
        }

    fun getAllRoles() = apiRequestFlow {
        roleApi.getAllRoles()
    }

    fun getEventsByRole(@Path("id") id: Int) = apiRequestFlow {
        roleApi.getEventsByRole(id)
    }

    fun getOrganizationalRoleById(@Path("roleId") roleId: Int, @Query("eventId") eventId: Int) =
        apiRequestFlow {
            roleApi.getOrganizationalRoleById(roleId, eventId)
        }

    fun getOrganizationalRoles(@Query("eventId") eventId: Int) = apiRequestFlow {
        roleApi.getOrganizationalRoles(eventId)
    }

    fun getRoleById(@Path("id") id: Int) = apiRequestFlow {
        roleApi.getRoleById(id)
    }

    fun revokeAssistantRole(@Path("userId") userId: Int, @Path("eventId") eventId: Int) =
        apiRequestFlow {
            roleApi.revokeAssistantRole(userId, eventId)
        }

    fun revokeOrganizationalRole(
        @Path("userId") userId: Int,
        @Path("eventId") eventId: Int,
        @Path("roleId") roleId: Int
    ) = apiRequestFlow {
        roleApi.revokeOrganizationalRole(userId, eventId, roleId)
    }

    fun revokeOrganizerRole(@Path("userId") userId: Int, @Path("eventId") eventId: Int) =
        apiRequestFlow {
            roleApi.revokeOrganizerRole(userId, eventId)
        }

    fun revokeSystemRole(@Path("userId") userId: Int) = apiRequestFlow {
        roleApi.revokeSystemRole(userId)
    }

    fun searchByName(@Query("name") name: String) = apiRequestFlow {
        roleApi.searchByName(name)
    }
}
