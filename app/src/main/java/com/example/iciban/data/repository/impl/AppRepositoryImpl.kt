package com.example.iciban.data.repository.impl

import com.example.iciban.data.datasource.preference.UserDataStore
import com.example.iciban.data.repository.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class AppRepositoryImpl(
    private val sharedPref: UserDataStore,
    ) : AppRepository {
    override fun getUsername(): Flow<String> = sharedPref.getUsername()
    override suspend fun setUsername(value: String) {
        sharedPref.setUsername(value)
    }

    override suspend fun logout() {
        withContext(Dispatchers.IO) {
            sharedPref.clearAllDataSession()
        }
    }
}