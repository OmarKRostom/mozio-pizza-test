package com.mozio.internal.network

import com.google.gson.Gson
import com.mozio.internal.network.models.Error
import com.mozio.internal.network.models.NetworkErrorPayload
import java.lang.Exception

class NetworkError {
    var errorPayload: NetworkErrorPayload? = null

    companion object {
        fun create(errorResponse: String): NetworkError = NetworkError().apply {
            errorPayload = try {
                Gson().fromJson(errorResponse, NetworkErrorPayload::class.java)
            } catch (ex: Exception) {
                val errorObj = Error(arrayListOf(errorResponse), arrayListOf(999), "internal")
                NetworkErrorPayload(arrayListOf(errorObj))
            }
        }

        fun createNonApiError(errorResponse: String): NetworkError = NetworkError().apply {
            val errorObj = Error(arrayListOf(errorResponse), arrayListOf(999), "internal")
            errorPayload = NetworkErrorPayload(arrayListOf(errorObj))
        }
    }
}