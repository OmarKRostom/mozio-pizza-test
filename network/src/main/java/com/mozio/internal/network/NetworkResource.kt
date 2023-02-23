package com.mozio.internal.network

open class NetworkResource<T>(val status: NetworkStatus? = NetworkStatus.Idle,
                              val payload: NetworkPayload<T>? = null,
                              val error: NetworkError? = null) {
    open class NetworkSuccess<T>(data: T) : NetworkResource<T>(
        NetworkStatus.Success,
        NetworkPayload.create(data)
    )
    open class NetworkFailure(error: NetworkError): NetworkResource<NetworkError>(
        NetworkStatus.Failure,
            error = error)
    open class NetworkLoading(): NetworkResource<Boolean>(NetworkStatus.Loading)
}