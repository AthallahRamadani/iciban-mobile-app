package com.example.iciban.data.datasource.api.service

import com.example.iciban.data.datasource.api.request.AuthRequest
import com.example.iciban.data.datasource.api.request.RegisterRequest
import com.example.iciban.data.datasource.api.response.LoginResponse
import com.example.iciban.data.datasource.api.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("authenticate")
    suspend fun login(
        @Body authRequest: AuthRequest,
    ): LoginResponse

    @POST("api/v1/auth/register")
    suspend fun register(
        @Body authRequest: RegisterRequest,
    ): RegisterResponse
}