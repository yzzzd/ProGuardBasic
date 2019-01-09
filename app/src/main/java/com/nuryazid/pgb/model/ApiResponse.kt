package com.nuryazid.pgb.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String
    )