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
 * @param newPassword 
 * @param confirmNewPassword 
 * @param token 
 */
@Serializable

data class NewPasswordRequest (

    @SerialName(value = "newPassword")
    val newPassword: kotlin.String,

    @SerialName(value = "confirmNewPassword")
    val confirmNewPassword: kotlin.String,

    @SerialName(value = "token")
    val token: kotlin.String? = null

)

