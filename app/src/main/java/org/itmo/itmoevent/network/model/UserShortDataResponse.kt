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
 * @param surname 
 */
@Serializable

data class UserShortDataResponse (

    @SerialName(value = "id")
    val id: kotlin.Int? = null,

    @SerialName(value = "name")
    val name: kotlin.String? = null,

    @SerialName(value = "surname")
    val surname: kotlin.String? = null

)

