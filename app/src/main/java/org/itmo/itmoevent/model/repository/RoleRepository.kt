package org.itmo.itmoevent.model.repository

import android.util.Log
import org.itmo.itmoevent.model.data.dto.PrivilegeDto
import org.itmo.itmoevent.model.data.entity.Privilege
import org.itmo.itmoevent.model.data.entity.Role
import org.itmo.itmoevent.model.network.RoleApi

class RoleRepository(private val roleApi: RoleApi) {

    private var systemPrivileges: List<Privilege>? = null

    suspend fun getSystemPrivileges(): List<Privilege>? {
        if (systemPrivileges == null) {
            try {
                val response = roleApi.getUserSystemPrivileges()
                if (response.isSuccessful) {
                    systemPrivileges = response.body()?.map {
                        mapPrivilegeDtoToEntity(it)
                    }
                } else {
                    return null
                }

            } catch (ex: Exception) {
                return null
            }
        }

        return systemPrivileges
    }

    suspend fun getEventPrivileges(eventId: Int): List<Privilege>? {
        return try {
            val response = roleApi.getUserEventPrivileges(eventId)
            if (response.isSuccessful) {
                response.body()?.map {
                    mapPrivilegeDtoToEntity(it)
                }
            } else {
                null
            }

        } catch (ex: Exception) {
            null
        }
    }

    suspend fun getOrgRoles(): List<Role>? {
        try {
            val response = roleApi.getOrganisationalRoles()
            return if (response.isSuccessful) {
                response.body()?.map {
                    Role(
                        it.id,
                        it.name
                    )
                }
            } else {
                null
            }
        } catch (ex: Exception) {
            Log.i("retrofit", ex.stackTraceToString())
            return null
        }
    }

    private fun mapPrivilegeDtoToEntity(privilegeDto: PrivilegeDto) = Privilege(
        privilegeDto.id,
        privilegeDto.name,
        privilegeDto.description
    )

}