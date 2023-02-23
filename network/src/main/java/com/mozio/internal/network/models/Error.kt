package com.mozio.internal.network.models

import com.google.gson.annotations.SerializedName

data class Error(
    @SerializedName("error")
    val error: List<String>,
    @SerializedName("errorCodes")
    val errorCodes: List<Int>,
    @SerializedName("property")
    val property: String
)