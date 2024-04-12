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
 * @param oldPassword 
 * @param newPassword 
 * @param confirmNewPassword 
 */
@Serializable

data class UserChangePasswordRequest (

    @SerialName(value = "oldPassword")
    val oldPassword: kotlin.String,

    @SerialName(value = "newPassword")
    val newPassword: kotlin.String,

    @SerialName(value = "confirmNewPassword")
    val confirmNewPassword: kotlin.String

)
