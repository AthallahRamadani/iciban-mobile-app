package com.example.iciban.data.datasource.model

import com.example.iciban.data.datasource.api.response.BaseEntity

data class User (
    val photo: Any? = null,
    val id: Int? = null,
    val cloudinaryImageId: Any? = null,
    val email: String? = null,
    val baseEntity: BaseEntity? = null,
    val username: String? = null
)