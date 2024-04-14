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
 * @param name 
 * @param email 
 * @param visited 
 * @param eventId 
 * @param additionalInfo 
 */
@Serializable

data class ParticipantResponse (

    @SerialName(value = "id")
    val id: kotlin.Int,

    @SerialName(value = "name")
    val name: kotlin.String,

    @SerialName(value = "email")
    val email: kotlin.String,

    @SerialName(value = "visited")
    val visited: kotlin.Boolean,

    @SerialName(value = "eventId")
    val eventId: kotlin.Int,

    @SerialName(value = "additionalInfo")
    val additionalInfo: kotlin.String? = null

)

