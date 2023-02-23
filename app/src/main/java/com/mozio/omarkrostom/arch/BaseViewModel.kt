package com.mozio.omarkrostom.arch

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

open class BaseViewModel: ViewModel() {
    private val _notifyActivityMutableLiveData = MutableLiveData<Event<Bundle>>()
    val notifyActivityLiveData = Transformations.map(_notifyActivityMutableLiveData) { return@map it }

    fun notifyParentActivity() {
        _notifyActivityMutableLiveData.value = Event(Bundle())
    }
}