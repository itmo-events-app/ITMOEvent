package org.itmo.itmoevent.network.repository

import org.itmo.itmoevent.network.api.RoleControllerApi
import org.itmo.itmoevent.network.model.RoleRequest
import org.itmo.itmoevent.network.util.apiRequestFlow
import retrofit2.http.Body
import retrofit2.http.Path
import retrofit2.http.Query

class RoleRepository(private val roleApi: RoleControllerApi) {

    fun assignAssistantRole(userId: Int, eventId: Int) = apiRequestFlow {
        roleApi.assignAssistantRole(userId, eventId)
    }

    fun assignOrganizationalRole(userId: Int, eventId: Int, roleId: Int) = apiRequestFlow {
        roleApi.assignOrganizationalRole(userId, eventId, roleId)
    }

    fun assignOrganizerRole(userId: Int, eventId: Int) = apiRequestFlow {
        roleApi.assignOrganizerRole(userId, eventId)
    }

    fun assignSystemRole(userId: Int, roleId: Int) = apiRequestFlow {
        roleApi.assignSystemRole(userId, roleId)
    }

    fun createRole(roleRequest: RoleRequest) = apiRequestFlow {
        roleApi.createRole(roleRequest)
    }

    fun deleteRole(id: Int) = apiRequestFlow {
        roleApi.deleteRole(id)
    }

    fun editRole(id: Int, roleRequest: RoleRequest) = apiRequestFlow {
        roleApi.editRole(id, roleRequest)
    }

    fun getAllPrivileges(type: RoleControllerApi.TypeGetAllPrivileges? = null) = apiRequestFlow {
        roleApi.getAllPrivileges(type)
    }

    fun getAllRoles() = apiRequestFlow {
        roleApi.getAllRoles()
    }

    fun getEventsByRole(id: Int) = apiRequestFlow {
        roleApi.getEventsByRole(id)
    }

    fun getOrganizationalRoleById(roleId: Int, eventId: Int) = apiRequestFlow {
        roleApi.getOrganizationalRoleById(roleId, eventId)
    }

    fun getOrganizationalRoles() = apiRequestFlow {
        roleApi.getOrganizationalRoles()
    }

    fun getRoleById(id: Int) = apiRequestFlow {
        roleApi.getRoleById(id)
    }

    fun revokeAssistantRole(userId: Int, eventId: Int) = apiRequestFlow {
        roleApi.revokeAssistantRole(userId, eventId)
    }

    fun revokeOrganizationalRole(userId: Int, eventId: Int, roleId: Int) = apiRequestFlow {
        roleApi.revokeOrganizationalRole(userId, eventId, roleId)
    }

    fun revokeOrganizerRole(userId: Int, eventId: Int) = apiRequestFlow {
        roleApi.revokeOrganizerRole(userId, eventId)
    }

    fun revokeSystemRole(userId: Int, roleId: Int) = apiRequestFlow {
        roleApi.revokeSystemRole(userId, roleId)
    }

    fun searchByName(name: String) = apiRequestFlow {
        roleApi.searchByName(name)
    }
}
