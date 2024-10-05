package com.example.iciban.data.model

import com.example.iciban.data.datasource.api.response.BaseEntity

data class User (
    val username: String? = null,
    val accessToken: String? = null,
    val refreshToken: String? = null
)