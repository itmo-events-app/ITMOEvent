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
 * @param placeId 
 * @param startDate 
 * @param endDate 
 * @param title 
 * @param shortDescription 
 * @param fullDescription 
 * @param format 
 * @param status 
 * @param registrationStart 
 * @param registrationEnd 
 * @param participantLimit 
 * @param participantAgeLowest 
 * @param participantAgeHighest 
 * @param preparingStart 
 * @param preparingEnd 
 * @param image 
 * @param parent 
 */
@Serializable

data class EventRequest (

    @SerialName(value = "placeId")
    val placeId: kotlin.Int,

    @Contextual @SerialName(value = "startDate")
    val startDate: java.time.LocalDateTime,

    @Contextual @SerialName(value = "endDate")
    val endDate: java.time.LocalDateTime,

    @SerialName(value = "title")
    val title: kotlin.String,

    @SerialName(value = "shortDescription")
    val shortDescription: kotlin.String,

    @SerialName(value = "fullDescription")
    val fullDescription: kotlin.String,

    @SerialName(value = "format")
    val format: EventRequest.Format,

    @SerialName(value = "status")
    val status: EventRequest.Status,

    @Contextual @SerialName(value = "registrationStart")
    val registrationStart: java.time.LocalDateTime,

    @Contextual @SerialName(value = "registrationEnd")
    val registrationEnd: java.time.LocalDateTime,

    @SerialName(value = "participantLimit")
    val participantLimit: kotlin.Int,

    @SerialName(value = "participantAgeLowest")
    val participantAgeLowest: kotlin.Int,

    @SerialName(value = "participantAgeHighest")
    val participantAgeHighest: kotlin.Int,

    @Contextual @SerialName(value = "preparingStart")
    val preparingStart: java.time.LocalDateTime,

    @Contextual @SerialName(value = "preparingEnd")
    val preparingEnd: java.time.LocalDateTime,

    @Contextual @SerialName(value = "image")
    val image: java.io.File,

    @SerialName(value = "parent")
    val parent: kotlin.Int? = null

) {

    /**
     * 
     *
     * Values: ONLINE,OFFLINE,HYBRID
     */
    @Serializable
    enum class Format(val value: kotlin.String) {
        @SerialName(value = "ONLINE") ONLINE("ONLINE"),
        @SerialName(value = "OFFLINE") OFFLINE("OFFLINE"),
        @SerialName(value = "HYBRID") HYBRID("HYBRID");
    }
    /**
     * 
     *
     * Values: DRAFT,PUBLISHED,COMPLETED,CANCELED
     */
    @Serializable
    enum class Status(val value: kotlin.String) {
        @SerialName(value = "DRAFT") DRAFT("DRAFT"),
        @SerialName(value = "PUBLISHED") PUBLISHED("PUBLISHED"),
        @SerialName(value = "COMPLETED") COMPLETED("COMPLETED"),
        @SerialName(value = "CANCELED") CANCELED("CANCELED");
    }
}

