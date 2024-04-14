package org.itmo.itmoevent.network.api

import org.itmo.itmoevent.network.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


import okhttp3.MultipartBody

interface TestControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param bucket 
     * @param `object` 
     * @return [Unit]
     */
    @DELETE("delete/{bucket}/{object}")
    suspend fun delete(@Path("bucket") bucket: kotlin.String, @Path("object") `object`: kotlin.String): Response<Unit>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param s 
     * @return [kotlin.String]
     */
    @GET("hello")
    suspend fun sayHello(@Query("s") s: kotlin.String): Response<kotlin.String>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param bucket 
     * @param multipartFile 
     * @return [Unit]
     */
    @Multipart
    @PUT("upload/{bucket}")
    suspend fun upload(@Path("bucket") bucket: kotlin.String, @Part multipartFile: MultipartBody.Part): Response<Unit>

}
