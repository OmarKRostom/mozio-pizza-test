package com.mozio.omarkrostom.arch

class Event<T>(private val data: T?) {
    private var hasBeenHandled: Boolean = false

    fun peekContent(): T? = data

    fun getContentIfNotHandled(): T? = if (!hasBeenHandled) data.also { hasBeenHandled = true }
    else null
}