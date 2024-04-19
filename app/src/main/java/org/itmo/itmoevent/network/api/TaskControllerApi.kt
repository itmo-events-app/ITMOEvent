package org.itmo.itmoevent.network.api

import org.itmo.itmoevent.network.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import org.itmo.itmoevent.network.model.FileDataResponse
import org.itmo.itmoevent.network.model.TaskRequest
import org.itmo.itmoevent.network.model.TaskResponse
import org.itmo.itmoevent.network.model.UploadFilesRequest

interface TaskControllerApi {
    /**
     * Удаление файлов из задачи
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id ID задачи
     * @param requestBody 
     * @return [Unit]
     */
    @DELETE("api/tasks/{id}/files")
    suspend fun deleteFiles(@Path("id") id: kotlin.Int, @Body requestBody: kotlin.collections.List<kotlin.String>): Response<Unit>

    /**
     * Получение списка имен файлов задачи
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id ID задачи
     * @return [kotlin.collections.List<FileDataResponse>]
     */
    @GET("api/tasks/{id}/files")
    suspend fun getFileNames(@Path("id") id: kotlin.Int): Response<kotlin.collections.List<FileDataResponse>>

    /**
     * Создание задачи
     * 
     * Responses:
     *  - 200: OK
     *
     * @param taskRequest 
     * @return [kotlin.Int]
     */
    @POST("api/tasks")
    suspend fun taskAdd(@Body taskRequest: TaskRequest): Response<kotlin.Int>

    /**
     * Удаление задачи
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id ID задачи
     * @return [Unit]
     */
    @DELETE("api/tasks/{id}")
    suspend fun taskDelete(@Path("id") id: kotlin.Int): Response<Unit>

    /**
     * Удаление исполнителя задачи
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id ID задачи
     * @return [TaskResponse]
     */
    @DELETE("api/tasks/{id}/assignee")
    suspend fun taskDeleteAssignee(@Path("id") id: kotlin.Int): Response<TaskResponse>

    /**
     * Редактирование задачи
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id ID задачи
     * @param taskRequest 
     * @return [TaskResponse]
     */
    @PUT("api/tasks/{id}")
    suspend fun taskEdit(@Path("id") id: kotlin.Int, @Body taskRequest: TaskRequest): Response<TaskResponse>

    /**
     * Получение задачи по id
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id ID задачи
     * @return [TaskResponse]
     */
    @GET("api/tasks/{id}")
    suspend fun taskGet(@Path("id") id: kotlin.Int): Response<TaskResponse>

    /**
     * Копирование списка задач
     * 
     * Responses:
     *  - 200: OK
     *
     * @param dstEventId ID мероприятия, куда задача будет скопирована
     * @param requestBody 
     * @return [kotlin.collections.List<TaskResponse>]
     */
    @POST("api/tasks/event/{dstEventId}")
    suspend fun taskListCopy(@Path("dstEventId") dstEventId: kotlin.Int, @Body requestBody: kotlin.collections.List<kotlin.Int>): Response<kotlin.collections.List<TaskResponse>>

    /**
     * Перемещение списка задач
     * 
     * Responses:
     *  - 200: OK
     *
     * @param dstEventId ID мероприятия, куда задача будет перемещена
     * @param requestBody 
     * @return [kotlin.collections.List<TaskResponse>]
     */
    @PUT("api/tasks/event/{dstEventId}")
    suspend fun taskListMove(@Path("dstEventId") dstEventId: kotlin.Int, @Body requestBody: kotlin.collections.List<kotlin.Int>): Response<kotlin.collections.List<TaskResponse>>


    /**
    * enum for parameter taskStatus
    */
    enum class TaskStatusTaskListShowInEvent(val value: kotlin.String) {
        @SerialName(value = "NEW") NEW("NEW"),
        @SerialName(value = "IN_PROGRESS") IN_PROGRESS("IN_PROGRESS"),
        @SerialName(value = "EXPIRED") EXPIRED("EXPIRED"),
        @SerialName(value = "DONE") DONE("DONE")
    }

    /**
     * Получение списка задач мероприятия
     * 
     * Responses:
     *  - 200: OK
     *
     * @param eventId ID мероприятия
     * @param assigneeId ID Исполнителя задачи (optional)
     * @param assignerId ID Создателя задачи (optional)
     * @param taskStatus Статус задачи (optional)
     * @param deadlineLowerLimit Нижняя граница для фильтрации задач по дедлайну (optional)
     * @param deadlineUpperLimit Верхняя граница для фильтрации задач по дедлайну (optional)
     * @param subEventTasksGet Включить получение задач активностей мероприятия (optional, default to false)
     * @param personalTasksGet Получить свои задачи в мероприятии. Более приоритетный параметр, чем assigneeId (optional, default to false)
     * @param page Номер страницы (optional, default to 0)
     * @param pageSize Размер страницы (optional, default to 50)
     * @return [kotlin.collections.List<TaskResponse>]
     */
    @GET("api/tasks/event/{eventId}")
    suspend fun taskListShowInEvent(@Path("eventId") eventId: kotlin.Int, @Query("assigneeId") assigneeId: kotlin.Int? = null, @Query("assignerId") assignerId: kotlin.Int? = null, @Query("taskStatus") taskStatus: TaskStatusTaskListShowInEvent? = null, @Query("deadlineLowerLimit") deadlineLowerLimit: java.time.LocalDateTime? = null, @Query("deadlineUpperLimit") deadlineUpperLimit: java.time.LocalDateTime? = null, @Query("subEventTasksGet") subEventTasksGet: kotlin.Boolean? = false, @Query("personalTasksGet") personalTasksGet: kotlin.Boolean? = false, @Query("page") page: kotlin.Int? = 0, @Query("pageSize") pageSize: kotlin.Int? = 50): Response<kotlin.collections.List<TaskResponse>>


    /**
    * enum for parameter taskStatus
    */
    enum class TaskStatusTaskListShowWhereAssignee(val value: kotlin.String) {
        @SerialName(value = "NEW") NEW("NEW"),
        @SerialName(value = "IN_PROGRESS") IN_PROGRESS("IN_PROGRESS"),
        @SerialName(value = "EXPIRED") EXPIRED("EXPIRED"),
        @SerialName(value = "DONE") DONE("DONE")
    }

    /**
     * Получение списка задач где пользователь является исполнителем
     * 
     * Responses:
     *  - 200: OK
     *
     * @param eventId ID мероприятия (optional)
     * @param assignerId ID Создателя задачи (optional)
     * @param taskStatus Статус задачи (optional)
     * @param deadlineLowerLimit Нижняя граница для фильтрации задач по дедлайну (optional)
     * @param deadlineUpperLimit Верхняя граница для фильтрации задач по дедлайну (optional)
     * @param page Номер страницы (optional, default to 0)
     * @param pageSize Размер страницы (optional, default to 50)
     * @return [kotlin.collections.List<TaskResponse>]
     */
    @GET("api/tasks/where-assignee")
    suspend fun taskListShowWhereAssignee(@Query("eventId") eventId: kotlin.Int? = null, @Query("assignerId") assignerId: kotlin.Int? = null, @Query("taskStatus") taskStatus: TaskStatusTaskListShowWhereAssignee? = null, @Query("deadlineLowerLimit") deadlineLowerLimit: java.time.LocalDateTime? = null, @Query("deadlineUpperLimit") deadlineUpperLimit: java.time.LocalDateTime? = null, @Query("page") page: kotlin.Int? = 0, @Query("pageSize") pageSize: kotlin.Int? = 50): Response<kotlin.collections.List<TaskResponse>>

    /**
     * Назначение исполнителя задачи
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id ID задачи
     * @param userId ID пользователя
     * @return [TaskResponse]
     */
    @PUT("api/tasks/{id}/assignee/{userId}")
    suspend fun taskSetAssignee(@Path("id") id: kotlin.Int, @Path("userId") userId: kotlin.Int): Response<TaskResponse>

    /**
     * Установка статуса задачи
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id ID задачи
     * @param body 
     * @return [TaskResponse]
     */
    @PUT("api/tasks/{id}/status")
    suspend fun taskSetStatus(@Path("id") id: kotlin.Int, @Body body: kotlin.String): Response<TaskResponse>

    /**
     * Назначение себя исполнителем задачи
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id ID задачи
     * @return [TaskResponse]
     */
    @PUT("api/tasks/{id}/assignee")
    suspend fun taskTakeOn(@Path("id") id: kotlin.Int): Response<TaskResponse>

    /**
     * Добавление файлов к задаче
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id ID задачи
     * @param uploadFilesRequest  (optional)
     * @return [kotlin.collections.List<FileDataResponse>]
     */
    @PUT("api/tasks/{id}/files")
    suspend fun uploadFiles(@Path("id") id: kotlin.Int, @Body uploadFilesRequest: UploadFilesRequest? = null): Response<kotlin.collections.List<FileDataResponse>>

}
