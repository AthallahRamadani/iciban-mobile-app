package com.example.iciban.data.datasource.api.response

data class LoginResponse(
	val data: Data? = null,
	val message: String? = null,
	val status: String? = null
)

data class Data(
	val accessToken: String? = null,
	val refreshToken: String? = null,
	val user: User? = null
)

data class User(
	val photo: Any? = null,
	val id: Int? = null,
	val cloudinaryImageId: Any? = null,
	val email: String? = null,
	val baseEntity: BaseEntity? = null,
	val username: String? = null
)

data class BaseEntity(
	val createdAt: String? = null,
	val updatedAt: String? = null
)

