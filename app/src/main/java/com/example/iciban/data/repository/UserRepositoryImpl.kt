package com.example.iciban.data.repository

import com.example.iciban.data.ResultState
import com.example.iciban.data.datasource.api.request.AuthRequest
import com.example.iciban.data.datasource.api.request.RegisterRequest
import com.example.iciban.data.datasource.api.service.ApiService
import com.example.iciban.data.datasource.preference.UserDataStore
import com.example.iciban.data.util.toUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(
    private val apiService: ApiService,
    private val appPreferences: UserDataStore
) : UserRepository {


    override suspend fun login(
        username: String, password: String
    ): Flow<ResultState<Boolean>> = flow {
        emit(ResultState.Loading)
        try {
            val request = AuthRequest(
                username,
                password
            )
            val response = apiService.login(request)
            val user = response.data?.toUser()
            if (user != null) {
                appPreferences.setUserDataSession(user)
                appPreferences.setUserAuthorization(true)
                emit(ResultState.Succes(true))
            } else {
                throw Exception("Failed")
            }
        } catch (e: Exception) {
            emit(ResultState.Error(e))
        }


    }

    override suspend fun register(
        userName: String,
        email: String,
        password: String
    ): Flow<ResultState<Boolean>> =
        flow {
            emit(ResultState.Loading)
            try {
                val request = RegisterRequest(
                    userName,
                    email,
                    password
                )
                val response = apiService.register(request)
                val user = response.data?.toUser()
                if (user != null) {
                    appPreferences.setUserDataSession(user)
                    appPreferences.setUserAuthorization(true)
                    emit(ResultState.Succes(true))
                } else {
                    throw Exception("Failed")
                }
            } catch (e: Exception) {
                emit(ResultState.Error(e))
            }
        }

}