package org.itmo.itmoevent.network.api

import org.itmo.itmoevent.network.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import org.itmo.itmoevent.network.model.EventResponse
import org.itmo.itmoevent.network.model.PrivilegeResponse
import org.itmo.itmoevent.network.model.RoleRequest
import org.itmo.itmoevent.network.model.RoleResponse

interface RoleControllerApi {
    /**
     * Назначение пользователю роли Помощник
     * 
     * Responses:
     *  - 200: OK
     *
     * @param userId ID пользователя
     * @param eventId ID мероприятия
     * @return [Unit]
     */
    @POST("api/roles/assistant/{userId}/{eventId}")
    suspend fun assignAssistantRole(@Path("userId") userId: kotlin.Int, @Path("eventId") eventId: kotlin.Int): Response<Unit>

    /**
     * Назначение пользователю организационной роли
     * 
     * Responses:
     *  - 200: OK
     *
     * @param userId ID пользователя
     * @param eventId ID мероприятия
     * @param roleId ID роли
     * @return [Unit]
     */
    @POST("api/roles/organizational/{userId}/{eventId}/{roleId}")
    suspend fun assignOrganizationalRole(@Path("userId") userId: kotlin.Int, @Path("eventId") eventId: kotlin.Int, @Path("roleId") roleId: kotlin.Int): Response<Unit>

    /**
     * Назначение пользователю роли Организатор
     * 
     * Responses:
     *  - 200: OK
     *
     * @param userId ID пользователя
     * @param eventId ID мероприятия
     * @return [Unit]
     */
    @POST("api/roles/organizer/{userId}/{eventId}")
    suspend fun assignOrganizerRole(@Path("userId") userId: kotlin.Int, @Path("eventId") eventId: kotlin.Int): Response<Unit>

    /**
     * Назначение пользователю системной роли
     * 
     * Responses:
     *  - 200: OK
     *
     * @param userId ID пользователя
     * @param roleId ID роли
     * @return [Unit]
     */
    @POST("api/roles/system/{userId}/{roleId}")
    suspend fun assignSystemRole(@Path("userId") userId: kotlin.Int, @Path("roleId") roleId: kotlin.Int): Response<Unit>

    /**
     * Создание роли
     * 
     * Responses:
     *  - 200: OK
     *
     * @param roleRequest 
     * @return [RoleResponse]
     */
    @POST("api/roles/")
    suspend fun createRole(@Body roleRequest: RoleRequest): Response<RoleResponse>

    /**
     * Удаление роли
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id ID роли
     * @return [Unit]
     */
    @DELETE("api/roles/{id}")
    suspend fun deleteRole(@Path("id") id: kotlin.Int): Response<Unit>

    /**
     * Редактирование роли
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id ID роли
     * @param roleRequest 
     * @return [RoleResponse]
     */
    @PUT("api/roles/{id}")
    suspend fun editRole(@Path("id") id: kotlin.Int, @Body roleRequest: RoleRequest): Response<RoleResponse>


    /**
    * enum for parameter type
    */
    enum class TypeGetAllPrivileges(val value: kotlin.String) {
        @SerialName(value = "SYSTEM") SYSTEM("SYSTEM"),
        @SerialName(value = "EVENT") EVENT("EVENT")
    }

    /**
     * Получение списка привилегий
     * 
     * Responses:
     *  - 200: OK
     *
     * @param type Тип привилегии (optional)
     * @return [kotlin.collections.List<PrivilegeResponse>]
     */
    @GET("api/roles/privileges")
    suspend fun getAllPrivileges(@Query("type") type: TypeGetAllPrivileges? = null): Response<kotlin.collections.List<PrivilegeResponse>>

    /**
     * Получение списка всех ролей
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [kotlin.collections.List<RoleResponse>]
     */
    @GET("api/roles/")
    suspend fun getAllRoles(): Response<kotlin.collections.List<RoleResponse>>

    /**
     * Получение списка мероприятий пользователя по привилегии
     * 
     * Responses:
     *  - 200: OK
     *
     * @param privilegeId ID привилегии
     * @param userId ID пользователя
     * @return [kotlin.collections.List<EventResponse>]
     */
    @GET("api/roles/{privilegeId}/{userId}/events")
    suspend fun getEventsByPrivilige(@Path("privilegeId") privilegeId: kotlin.Int, @Path("userId") userId: kotlin.Int): Response<kotlin.collections.List<EventResponse>>

    /**
     * Получение списка мероприятий пользователя по роли
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id ID роли
     * @return [kotlin.collections.List<EventResponse>]
     */
    @GET("api/roles/{id}/events")
    suspend fun getEventsByRole(@Path("id") id: kotlin.Int): Response<kotlin.collections.List<EventResponse>>

    /**
     * Получение организационной роли по id
     * 
     * Responses:
     *  - 200: OK
     *
     * @param roleId ID роли
     * @param eventId ID меропрятия
     * @return [RoleResponse]
     */
    @GET("api/roles/organizational/{roleId}")
    suspend fun getOrganizationalRoleById(@Path("roleId") roleId: kotlin.Int, @Query("eventId") eventId: kotlin.Int): Response<RoleResponse>

    /**
     * Получение списка организационных ролей
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [kotlin.collections.List<RoleResponse>]
     */
    @GET("api/roles/organizational")
    suspend fun getOrganizationalRoles(): Response<kotlin.collections.List<RoleResponse>>

    /**
     * Получение роли по id
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id ID роли
     * @return [RoleResponse]
     */
    @GET("api/roles/{id}")
    suspend fun getRoleById(@Path("id") id: kotlin.Int): Response<RoleResponse>

    /**
     * Получение списка системных ролей
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [kotlin.collections.List<RoleResponse>]
     */
    @GET("api/roles/system")
    suspend fun getSystemRoles(): Response<kotlin.collections.List<RoleResponse>>

    /**
     * Получение списка организационных ролей в мероприятии, назначенных пользователю
     * 
     * Responses:
     *  - 200: OK
     *
     * @param userId ID пользователя
     * @param eventId ID мероприятия
     * @return [kotlin.collections.List<RoleResponse>]
     */
    @GET("api/roles/organizational/{userId}/{eventId}")
    suspend fun getUserEventRoles(@Path("userId") userId: kotlin.Int, @Path("eventId") eventId: kotlin.Int): Response<kotlin.collections.List<RoleResponse>>

    /**
     * Получение списка организационных ролей в мероприятии, назначенных пользователю
     * 
     * Responses:
     *  - 200: OK
     *
     * @param userId ID пользователя
     * @return [kotlin.collections.List<RoleResponse>]
     */
    @GET("api/roles/system/{userId}")
    suspend fun getUserSystemRoles(@Path("userId") userId: kotlin.Int): Response<kotlin.collections.List<RoleResponse>>

    /**
     * Лишение пользователя роли Помощник
     * 
     * Responses:
     *  - 200: OK
     *
     * @param userId 
     * @param eventId 
     * @return [Unit]
     */
    @DELETE("api/roles/assistant/{userId}/{eventId}")
    suspend fun revokeAssistantRole(@Path("userId") userId: kotlin.Int, @Path("eventId") eventId: kotlin.Int): Response<Unit>

    /**
     * Лишение пользователя организационной роли
     * 
     * Responses:
     *  - 200: OK
     *
     * @param userId ID пользователя
     * @param eventId ID мероприятия
     * @param roleId ID роли
     * @return [Unit]
     */
    @DELETE("api/roles/organizational/{userId}/{eventId}/{roleId}")
    suspend fun revokeOrganizationalRole(@Path("userId") userId: kotlin.Int, @Path("eventId") eventId: kotlin.Int, @Path("roleId") roleId: kotlin.Int): Response<Unit>

    /**
     * Лишение пользователя роли Организатор
     * 
     * Responses:
     *  - 200: OK
     *
     * @param userId 
     * @param eventId 
     * @return [Unit]
     */
    @DELETE("api/roles/organizer/{userId}/{eventId}")
    suspend fun revokeOrganizerRole(@Path("userId") userId: kotlin.Int, @Path("eventId") eventId: kotlin.Int): Response<Unit>

    /**
     * Лишение пользователя системной роли
     * 
     * Responses:
     *  - 200: OK
     *
     * @param userId ID пользователя
     * @param roleId ID роли
     * @return [Unit]
     */
    @DELETE("api/roles/system-revoke/{userId}/{roleId}")
    suspend fun revokeSystemRole(@Path("userId") userId: kotlin.Int, @Path("roleId") roleId: kotlin.Int): Response<Unit>

    /**
     * Поиск ролей по совпадению в названии
     * 
     * Responses:
     *  - 200: OK
     *
     * @param name Имя роли
     * @return [kotlin.collections.List<RoleResponse>]
     */
    @GET("api/roles/search")
    suspend fun searchByName(@Query("name") name: kotlin.String): Response<kotlin.collections.List<RoleResponse>>

}
