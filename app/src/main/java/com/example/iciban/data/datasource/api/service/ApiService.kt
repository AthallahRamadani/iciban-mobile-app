package com.example.iciban.data.datasource.api.service

import com.example.iciban.data.datasource.api.request.AuthRequest
import com.example.iciban.data.datasource.api.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("authenticate")
    suspend fun login(
        @Body authRequest: AuthRequest,
    ): LoginResponse
}