/**
 *
 * Please note:
 * This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 *
 */

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package org.itmo.itmoevent.network.model


import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

/**
 * 
 *
 * @param id 
 * @param email 
 * @param name 
 * @param surname 
 * @param status 
 * @param sentTime 
 */
@Serializable

data class RegistrationRequestForAdmin (

    @SerialName(value = "id")
    val id: kotlin.Int? = null,

    @SerialName(value = "email")
    val email: kotlin.String? = null,

    @SerialName(value = "name")
    val name: kotlin.String? = null,

    @SerialName(value = "surname")
    val surname: kotlin.String? = null,

    @SerialName(value = "status")
    val status: RegistrationRequestForAdmin.Status? = null,

    @Contextual @SerialName(value = "sentTime")
    val sentTime: java.time.LocalDateTime? = null

) {

    /**
     * 
     *
     * Values: NEW,APPROVED,DECLINED
     */
    @Serializable
    enum class Status(val value: kotlin.String) {
        @SerialName(value = "NEW") NEW("NEW"),
        @SerialName(value = "APPROVED") APPROVED("APPROVED"),
        @SerialName(value = "DECLINED") DECLINED("DECLINED");
    }
}

