package org.itmo.itmoevent.model.repository

import android.util.Log
import org.itmo.itmoevent.model.data.entity.Role
import org.itmo.itmoevent.model.network.RoleApi

class RoleRepository(private val roleApi: RoleApi) {

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

}