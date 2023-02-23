package com.mozio.omarkrostom.arch.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.mozio.internal.network.NetworkError
import com.mozio.internal.network.NetworkResource
import com.mozio.internal.network.NetworkStatus
import com.mozio.omarkrostom.arch.Event

/**
 * Get success event
 */
fun <T> LiveData<NetworkResource<T>>.getSuccessEvent(): LiveData<Event<T?>?> {
    return Transformations.switchMap(this.filter { it.status == NetworkStatus.Success }) { response ->
        val singleLiveEvent = MutableLiveData<Event<T?>>()
        if (response.payload != null) {
            singleLiveEvent.postValue(Event(response.payload?.data))
        } else {
            singleLiveEvent.postValue(Event(null))
        }
        return@switchMap singleLiveEvent
    }
}

/**
 * Get failure event
 */
fun <T> LiveData<NetworkResource<T>>.getFailureEvent(): LiveData<Event<NetworkError?>?> {
    return Transformations.switchMap(this)
    {
        val singleLiveEvent = MutableLiveData<Event<NetworkError?>?>()
        if (it.status == NetworkStatus.Failure && it.error != null) {
            singleLiveEvent.postValue(Event(it.error))
        }
        singleLiveEvent
    }
}

/**
 * Get loading event
 */
fun <T> LiveData<NetworkResource<T>>.getLoadingEvent(): LiveData<Event<Boolean>> =
    Transformations.map(this) {
    return@map Event(it.status is NetworkStatus.Loading)
}