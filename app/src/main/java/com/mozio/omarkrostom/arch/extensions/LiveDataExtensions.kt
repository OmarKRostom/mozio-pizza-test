package com.mozio.omarkrostom.arch.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import com.mozio.omarkrostom.arch.Event

/**
 * Make sure you only get a value poked when it is non null
 */
fun <T> LiveData<T?>.nonNullObserve(viewLifecycleOwner: LifecycleOwner,
                                    block: (T) -> Unit) {
    this.observe(viewLifecycleOwner, Observer {
        it?.let(block)
    })
}

/**
 * Filter livedata with a predicate
 */
fun <T> LiveData<T>.filter(block: (T) -> Boolean): LiveData<T> {
    val filteredLiveData = MediatorLiveData<T>()
    filteredLiveData.addSource(this) {
        it?.let { data ->
            if (block.invoke(data))
                filteredLiveData.value = data
        }
    }

    return filteredLiveData
}

/**
 * Observe single events live data
 */
fun <T> LiveData<Event<T?>?>.observeSingleEvent(owner: LifecycleOwner, block: (T) -> Unit) {
    this.observe(owner, Observer {
        it?.getContentIfNotHandled()?.let(block)
    })
}