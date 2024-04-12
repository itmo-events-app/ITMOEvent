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
 * @param type 
 * @param login 
 */
@Serializable

data class UserChangeLoginRequest (

    @SerialName(value = "type")
    val type: UserChangeLoginRequest.Type,

    @SerialName(value = "login")
    val login: kotlin.String? = null

) {

    /**
     * 
     *
     * Values: EMAIL
     */
    @Serializable
    enum class Type(val value: kotlin.String) {
        @SerialName(value = "EMAIL") EMAIL("EMAIL");
    }
}
