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
 * @param name 
 * @param surname 
 */
@Serializable

data class UserChangeNameRequest (

    @SerialName(value = "name")
    val name: kotlin.String,

    @SerialName(value = "surname")
    val surname: kotlin.String

)

