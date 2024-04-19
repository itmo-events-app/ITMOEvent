package org.itmo.itmoevent.network.repository

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.itmo.itmoevent.network.api.ParticipantsControllerApi
import org.itmo.itmoevent.network.model.ParticipantPresenceRequest
import org.itmo.itmoevent.network.model.ParticipantResponse
import org.itmo.itmoevent.network.util.apiRequestFlow
import retrofit2.Response
import java.io.File

class ParticipantsRepository(private val participantsApi: ParticipantsControllerApi) {

    suspend fun changePresence(id: Int, participantPresenceRequest: ParticipantPresenceRequest) =
        apiRequestFlow {
            participantsApi.changePresence(id, participantPresenceRequest)
        }

    suspend fun getParticipants(id: Int) =
        apiRequestFlow {
            participantsApi.getParticipants(id)
        }

    suspend fun getParticipantsXlsxFile(id: Int) =
        apiRequestFlow {
            participantsApi.getParticipantsXlsxFile(id)
        }

    suspend fun setParticipantsList(id: Int, file: File) =
        apiRequestFlow {
            val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("participantsFile", file.name, requestFile)
            participantsApi.setPartisipantsList(id, body)
        }
}
