package com.example.iciban.data.repository

import com.example.iciban.data.ResultState
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface UserRepository{
    suspend fun login(username: String, password: String): Flow<ResultState<Boolean>>
    suspend fun register(userName: String, email: String, password: String): Flow<ResultState<Boolean>>
}