package org.itmo.itmoevent.viewmodel.util

import org.itmo.itmoevent.model.data.entity.enums.PrivilegeName
import org.itmo.itmoevent.model.repository.RoleRepository

object PrivilegeUtil {
    fun hasSysPrivilege(roleRepository: RoleRepository, sysPrivilegeName: PrivilegeName) =
        roleRepository.systemPrivilegesNames!!.contains(sysPrivilegeName)

    fun hasEventPrivilege(eventPrivileges: List<PrivilegeName>?, eventPrivilegeName: PrivilegeName) =
        eventPrivileges?.contains(eventPrivilegeName) ?: false

    suspend fun getEventPrivileges(roleRepository: RoleRepository, eventId: Int) =
        roleRepository.loadEventPrivileges(eventId)?.map { it.name }
}