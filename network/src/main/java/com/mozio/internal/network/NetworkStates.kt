package com.mozio.internal.network

sealed class NetworkStatus {
    object Success: NetworkStatus()
    object Failure: NetworkStatus()
    object Loading: NetworkStatus()
    object Idle: NetworkStatus()
}