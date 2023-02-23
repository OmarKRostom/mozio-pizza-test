package com.mozio.internal.network.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class NetworkErrorPayload(
    @SerializedName("errors")
    val errors: List<Error>
) {
    fun getFormattedErrorMessage() =
        errors.joinToString("\n", "- ") {
            it.error
                .joinToString().capitalize(Locale.ROOT)
        }
}