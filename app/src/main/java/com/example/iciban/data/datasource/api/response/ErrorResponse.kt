package com.example.iciban.data.datasource.api.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(

    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("message")
    val message: String? = null
)