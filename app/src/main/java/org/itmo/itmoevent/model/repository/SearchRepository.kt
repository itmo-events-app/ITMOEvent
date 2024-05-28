package org.itmo.itmoevent.model.repository

import android.util.Log
import org.itmo.itmoevent.model.data.dto.EventShortDto
import org.itmo.itmoevent.model.data.dto.PlaceDto
import org.itmo.itmoevent.model.data.dto.UserRoleDto
import org.itmo.itmoevent.model.data.entity.search.ActivitySearch
import org.itmo.itmoevent.model.data.entity.search.PlaceSearch
import org.itmo.itmoevent.model.data.entity.search.UserSearch
import org.itmo.itmoevent.model.data.entity.task.Task
import org.itmo.itmoevent.model.network.EventActivityApi
import org.itmo.itmoevent.model.network.EventApi
import org.itmo.itmoevent.model.network.PlaceApi
import org.itmo.itmoevent.model.network.UserApi

class SearchRepository(
    private val placeApi: PlaceApi,
    private val eventApi: EventApi,
    private val activityApi: EventActivityApi
) {
    suspend fun getPlacesSearch(): List<PlaceSearch>? {
        return try {
            val response = placeApi.getAllPlaces(0, 50)
            if (response.isSuccessful) {
                response.body()?.map { mapPlaceDtoToEntity(it) }
            } else null
        } catch (ex: Exception) {
            Log.i("retrofit", ex.stackTraceToString())
            null
        }
    }

    suspend fun getEventOrgsSearch(eventId: Int): List<UserSearch>? {
        return try {
            val response = eventApi.getEventOrganizers(eventId)
            if (response.isSuccessful) {
                response.body()?.map { mapUserDtoToEntity(it) }
            } else null
        } catch (ex: Exception) {
            Log.i("retrofit", ex.stackTraceToString())
            null
        }
    }

    suspend fun getEventActivitiesSearch(eventId: Int): List<ActivitySearch>? {
        return try {
            val response = eventApi.getEventActivities(eventId)
            if (response.isSuccessful) {
                response.body()?.items?.map { mapActivitiesDtoToEntity(it) }
            } else null
        } catch (ex: Exception) {
            Log.i("retrofit", ex.stackTraceToString())
            null
        }
    }

    private fun mapPlaceDtoToEntity(dto: PlaceDto) =
        PlaceSearch(
            dto.id,
            dto.name,
            dto.address,
            dto.room
        )

    private fun mapActivitiesDtoToEntity(dto: EventShortDto) =
        ActivitySearch(
            dto.id,
            dto.title
        )

    private fun mapUserDtoToEntity(dto: UserRoleDto) =
        UserSearch(
            dto.id,
            dto.name,
            dto.surname
        )
}