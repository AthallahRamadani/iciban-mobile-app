package com.example.iciban.data.repository

import kotlinx.coroutines.flow.Flow

interface AppRepository {
    fun getUsername(): Flow<String>
    suspend fun setUsername(value: String)
    suspend fun logout()

}