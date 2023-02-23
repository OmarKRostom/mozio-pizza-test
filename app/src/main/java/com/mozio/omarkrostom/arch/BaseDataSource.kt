package com.mozio.omarkrostom.arch

import com.mozio.internal.network.NetworkError
import com.mozio.internal.network.NetworkPayload
import com.mozio.internal.network.NetworkResource
import com.mozio.internal.network.NetworkStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

open class BaseDataSource {
    fun <T> fireRequest(apiCall: suspend () -> Response<T>): Flow<NetworkResource<T>> {
        return flow {
            emit(NetworkResource(status = NetworkStatus.Loading))

            val response = apiCall()

            val networkResource = if (response.isSuccessful) {
                NetworkResource(
                    status = NetworkStatus.Success,
                    payload = NetworkPayload.create(response.body())
                )
            } else {
                try {
                    NetworkResource(
                        status = NetworkStatus.Failure, error =
                        NetworkError.create(response.errorBody()?.string() ?:
                        response.body()?.toString() ?: "")
                    )
                } catch (ex: Exception) {
                    NetworkResource(
                        status = NetworkStatus.Failure, error =
                        NetworkError.create(ex.message ?: "")
                    )
                }
            }

            emit(networkResource)
        }.flowOn(Dispatchers.IO)
    }
}